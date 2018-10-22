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
            String remoteAddr = request.getRemoteAddr();
            Token token = issueToken(user.getEmail(), remoteAddr);
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
    public Response challenge(@Context HttpServletRequest request, @QueryParam("access_token") String access_token) {
        String remoteAddress = request.getRemoteAddr();
        Key key = keyGenerator.generate(remoteAddress);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(access_token);
        Date expirationDate = claimsJws.getBody().getExpiration();
        Date plusThreshold = new Date(OffsetDateTime.now().plusMinutes(3).toInstant().toEpochMilli());
        if(expirationDate.before(plusThreshold)){
            User me = getUser();
            Token token = issueToken(me.getEmail(),remoteAddress);
            userService.storeToken(me.getId(), token);
            return ok(token);
        }
        return ok();
    }

    private Token issueToken(String username, String remoteAddress) {
        Key key = keyGenerator.generate(remoteAddress);
        OffsetDateTime issuedAt = OffsetDateTime.now();
        OffsetDateTime expirationDate = issuedAt.plusMinutes(20);
        String jwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(new Date(issuedAt.toInstant().toEpochMilli()))
                .setExpiration(new Date(expirationDate.toInstant().toEpochMilli()))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        Token token = new Token(jwtToken, remoteAddress, issuedAt, expirationDate);
        return token;
    }
}
