package ma.ade.kwetter2.rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ma.ade.kwetter2.authentication.IKeyGenerator;
import ma.ade.kwetter2.authentication.RequireAuthentication;
import ma.ade.kwetter2.domain.Token;
import ma.ade.kwetter2.domain.User;
import ma.ade.kwetter2.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

@Stateless
@Path("/auth")
public class AuthenticationController extends BaseController {
    @Inject
    private UserService userService;
    @Inject
    private IKeyGenerator keyGenerator;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Context HttpServletRequest request, User inputUser){
        User user = userService.getUserByEmail(inputUser.getEmail());
        if(user == null){
            return notFound();
        }

        if(userService.authenticateUser(user.getId(), inputUser.getPassword())){
            String sessionId = request.getSession().getId();
            String tokenString = issueToken(user.getEmail(), sessionId);
            Token token = new Token(tokenString, sessionId);
            userService.storeToken(user.getId(), token);
            return ok(token);
        }
        else{
            return forbidden();
        }
    }

    @GET
    @Path("/challenge")
    @RequireAuthentication
    public Response challenge(@Context HttpServletRequest request, String access_token) {
        String sessionId = request.getSession().getId();
        Key key = keyGenerator.generate(sessionId);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(access_token);
        Date expirationDate = claimsJws.getBody().getExpiration();
        Date plusThreshold = new Date(OffsetDateTime.now().plusMinutes(1).toInstant().toEpochMilli());
        if(expirationDate.after(plusThreshold)){
            User me = getUser();
            String token = issueToken(me.getEmail(),sessionId);
            Set<Token> tokens = me.getTokens();
            for (Iterator<Token> it = tokens.iterator(); it.hasNext(); ) {
                Token t = it.next();
                if (t.getSessionId().equals(sessionId)) {
                    t.setAccessToken(token);
                    userService.updateToken(me.getId(), t);
                }

            }
            return ok(token);
        }
        return ok();
    }

    private String issueToken(String username, String sessionId) {
        Key key = keyGenerator.generate(sessionId);
        String jwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(OffsetDateTime.now().plusMinutes(20).toInstant().toEpochMilli()))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }
}
