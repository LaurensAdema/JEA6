package boundary.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/user")
public class UserResource {

    @GET
    public String User() {
        return "Enjoy Java EE 8!";
    }

}
