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
import javax.ws.rs.*;
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
    public Response login(String json){
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();

        String email = jsonObj.get("email").getAsString();
        String password = jsonObj.get("password").getAsString();

        User user = userService.getUserByEmail(email);
        if(user == null){
            return notFound();
        }

        if(userService.authenticateUser(user.getId(), password)){
            Token token = new Token(issueToken(user.getEmail()));
            return ok(token);
        }
        else{
            return forbidden();
        }
    }

    private String issueToken(String username) {
        Key key = keyGenerator.generate();
        String jwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(OffsetDateTime.now().plusHours(1).toInstant().toEpochMilli()))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }
}
