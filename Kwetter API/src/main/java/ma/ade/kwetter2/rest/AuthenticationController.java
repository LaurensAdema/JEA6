package ma.ade.kwetter2.rest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ma.ade.kwetter2.authentication.KeyGen;
import ma.ade.kwetter2.domain.User;
import ma.ade.kwetter2.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

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
public class AuthenticationController {
    @Inject
    private UserService userService;
    @Inject
    private KeyGen keyGenerator;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String email, String password){
        User user = userService.getUserByEmail(email);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(BCrypt.checkpw(password, user.getPassword())){
            String token = issueToken(user.getEmail());
            return Response.ok(token).build();
        }
        else{
            return Response.status(Response.Status.FORBIDDEN).build();
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
