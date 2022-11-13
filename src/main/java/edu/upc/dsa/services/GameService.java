package edu.upc.dsa.services;


import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.models.*;
import edu.upc.dsa.models.Exceptions.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.lang.Integer.parseInt;

@Api(value = "/game")
@Path("/")
public class GameService {
    private GameManager manager;

    public GameService() throws JuegoDoesNotExistException, PlayerCurrentlyPlayingException {
        this.manager = GameManagerImpl.getInstance();

        if (manager.sizeGames()==0) {
            this.manager.createJuego("pichi","com el beisball", 5);
            this.manager.createJuego("so-pa-po","el matias sempre guanya", 2);

            this.manager.iniciarPartida("pichi", "alex");
        }

    }

    @POST
    @ApiOperation(value = "create a new Game", notes = "If it does not exist a game with that name, it creates a new game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response= Juego.class),
            @ApiResponse(code = 409, message = "Conflict, it already exists a game with that name")

    })
    @Path("/games/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createJuego(Juego juego) {
        Juego juegocreated = this.manager.createJuego(juego.getNamejuego(), juego.getDescripcio(), juego.getNumniveles());

        if (juegocreated==null)  return Response.status(409).build();
        return Response.status(201).entity(juegocreated).build();
    }

    @PUT
    @ApiOperation(value = "start a Partida", notes = "Inicio de una partida de un juego por parte de un usuario. Si el usuario no existe, se crea.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully started", response= Partida.class),
            @ApiResponse(code = 404, message = "This game does not exists"),
            @ApiResponse(code = 400, message = "This player is already playing!")
    })
    @Path("/player/startpartida")
    public Response iniciarPartida(VOPlayerGameCredencials credencials) {
        try{
            Partida partida = this.manager.iniciarPartida(credencials.getNamejuego(), credencials.getUsername());
            return Response.status(201).entity(partida).build();
        } catch (JuegoDoesNotExistException e) {
            return Response.status(404).build();
        } catch (PlayerCurrentlyPlayingException e) {
            return Response.status(400).build();
        }
    }

    @GET
    @ApiOperation(value = "get nivel actual", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "This player does not exists"),
            @ApiResponse(code = 409, message = "This player is not playing right now!")
    })
    @Path("/player/{id}/nivelactual")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNivelActual(@PathParam("id") String id) {
        try{
            String nivel = this.manager.getNivelActual(id);
            return Response.status(200).entity(nivel).build();
        } catch (PlayerDoesNotExistException e) {
            return Response.status(404).build();
        } catch (PlayerNotCurrentlyPlayingException e) {
            return Response.status(409).build();
        }
    }

    @GET
    @ApiOperation(value = "get puntuacion actual", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "This player does not exists"),
            @ApiResponse(code = 409, message = "This player is not playing right now!")
    })
    @Path("/player/{id}/puntuacionactual")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntuacionActual(@PathParam("id") String id) {
        try{
            String puntos = this.manager.getPuntuacionActual(id);
            return Response.status(200).entity(puntos).build();
        } catch (PlayerDoesNotExistException e) {
            return Response.status(404).build();
        } catch (PlayerNotCurrentlyPlayingException e) {
            return Response.status(409).build();
        }
    }

    @PUT
    @ApiOperation(value = "pasar de nivel", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Has pasado de nivel!"),
            @ApiResponse(code = 202, message = "Has terminado el juego!"),
            @ApiResponse(code = 404, message = "This player does not exists"),
            @ApiResponse(code = 409, message = "This player is not playing right now!")
    })
    @Path("/player/gotonextlevel")
    public Response nextLevel(VOnextLevelCredencials credencials) {
        try{
            Player player = this.manager.nextLevel(credencials.getIduser(), credencials.getNewpuntos(), credencials.getFecha());
            if(player.getCurrentlyPlaying())
                return Response.status(200).build();
            return Response.status(202).build();
        }
        catch (JuegoDoesNotExistException e) {
            return Response.status(404).build();
        } catch (PlayerDoesNotExistException e) {
            return Response.status(404).build();
        } catch (PlayerNotCurrentlyPlayingException e) {
            return Response.status(409).build();
        }
    }

    @PUT
    @ApiOperation(value = "finalizar partida", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Partida ended!"),
            @ApiResponse(code = 404, message = "This player does not exists"),
            @ApiResponse(code = 409, message = "This player is not playing right now!")
    })
    @Path("/player/{id}/endpartida")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endPartida(@PathParam("id") String id) {
        try{
            Player player = this.manager.endPartida(id);
            return Response.status(200).build();
        } catch (PlayerDoesNotExistException e) {
            return Response.status(404).build();
        } catch (PlayerNotCurrentlyPlayingException e) {
            return Response.status(409).build();
        }
    }

    @POST
    @ApiOperation(value = "get sorted players", notes = "Consulta de usuarios que han participado en un juego ordenado por puntuación (descendente)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Player.class, responseContainer="List"),
            @ApiResponse(code = 204, message = "No one played to this game"),
            @ApiResponse(code = 404, message = "This game does not exist"),
            @ApiResponse(code = 409, message = "This player does not exist")
    })
    @Path("/games/{id}/sort")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortPlayers(@PathParam("id") String id) {
        try{
            Juego j = this.manager.getJuego(id);
            List<Player> players = this.manager.sortPlayers(j);
            if(players==null) return Response.status(204).build();
            GenericEntity<List<Player>> entity = new GenericEntity<List<Player>>(players){};
            return Response.status(200).entity(entity).build();
        } catch (JuegoDoesNotExistException e) {
            return Response.status(404).build();
        }
    }


    @GET
    @ApiOperation(value = "get partidas player", notes = "Consulta de las partidas en las que ha participado un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Partida.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "This player does not exist")
    })
    @Path("/player/{id}/partidas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPartidasPlayer(@PathParam("id") String id) {
        try{
            List<Partida> partidas = this.manager.getPartidasPlayer(id);
            GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas){};
            return Response.status(200).entity(entity).build();
        } catch (PlayerDoesNotExistException e) {
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get partidas player", notes = "Consulta de las partidas en las que ha participado un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = VOPerformance.class, responseContainer="List"),
            @ApiResponse(code = 204, message = "There is no activity"),
            @ApiResponse(code = 404, message = "This player does not exist"),
            @ApiResponse(code = 409, message = "This game does not exist")
    })
    @Path("player/{playername}/{namejuego}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerformance(@PathParam("playername") String username, @PathParam("namejuego") String namejuego) {
        try{
            List<VOPerformance> performance = this.manager.getPerformance(namejuego, username);
            if(performance==null)
                return Response.status(204).build();
            GenericEntity<List<VOPerformance>> entity = new GenericEntity<List<VOPerformance>>(performance){};
            return Response.status(200).entity(entity).build();
        } catch (PlayerDoesNotExistException e) {
            return Response.status(404).build();
        } catch (JuegoDoesNotExistException e) {
            return Response.status(409).build();
        }
    }

}

