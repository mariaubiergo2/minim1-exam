package edu.upc.dsa.services;

/*
import edu.upc.dsa.ShopManager;
import edu.upc.dsa.ShopManagerImpl;
import edu.upc.dsa.models.Player;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/shop")
@Path("/shop")
public class ShopService {
    private ShopManager manager;

    public ShopService() {
        this.manager = ShopManagerImpl.getInstance();

        if (manager.size()==0) {
            this.manager.addUser(new VOuser("111","Marti","Ubiergo Gomez", "02/11/2001", "martini@gmail.com", "gats"));
            this.manager.addUser(new VOuser("222","Laia", "Fonsi", "11/01/2001","lia@lalia.com", "gossos"));
            this.manager.addUser(new VOuser("333","Biel",  "Fonsi", "01/12/2001", "aeros@love.com",  "dofins"));

            this.manager.addObject(new Objecte("taula", "te potes", 50));
            this.manager.addObject(new Objecte("jerro", "trencat", 2));
            this.manager.addObject(new Objecte("Play", "aparell electronic", 149));
            this.manager.addObject(new Objecte("prestatge", "Aguanta molt", 9));

        }


    }

    @GET
    @ApiOperation(value = "get all Users", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Player.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks() {

        List<Player> players = this.manager.findAll();

        GenericEntity<List<Player>> entity = new GenericEntity<List<Player>>(players) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Player.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrack(@PathParam("id") String id) {
        Player t = this.manager.getTrack(id);
        if (t == null) return Response.status(404).build();
        else  return Response.status(201).entity(t).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Track", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/{id}")
    public Response deleteTrack(@PathParam("id") String id) {
        Player t = this.manager.getTrack(id);
        if (t == null) return Response.status(404).build();
        else this.manager.deleteTrack(id);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "update a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/")
    public Response updateTrack(Player player) {

        Player t = this.manager.updateTrack(player);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }



    @POST
    @ApiOperation(value = "create a new Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Player.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTrack(VOuser user) {

        if (user.getSinger()==null || user.getTitle()==null)  return Response.status(500).entity(user).build();
        this.manager.addUser(user);
        return Response.status(201).entity(user).build();
    }

}

 */