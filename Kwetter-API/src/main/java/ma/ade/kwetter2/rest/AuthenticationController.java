package ma.ade.kwetter2.rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ma.ade.kwetter2.authentication.IKeyGenerator;
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
    public Response login(@Context HttpServletRequest request, String json){
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();

        String email = jsonObj.get("email").getAsString();
        String password = jsonObj.get("password").getAsString();

        User user = userService.getUserByEmail(email);
        if(user == null){
            return notFound();
        }

        if(userService.authenticateUser(user.getId(), password)){
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
