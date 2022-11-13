package edu.upc.dsa;

import edu.upc.dsa.models.Exceptions.PlayerCurrentlyPlayingException;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Player;
import edu.upc.dsa.models.VOPerformance;
import edu.upc.dsa.models.Exceptions.JuegoDoesNotExistException;
import edu.upc.dsa.models.Exceptions.PlayerNotCurrentlyPlayingException;
import edu.upc.dsa.models.Exceptions.PlayerCurrentlyPlayingException;
import edu.upc.dsa.models.Exceptions.PlayerDoesNotExistException;

import java.util.List;

public interface GameManager {

    // OP1:  Creación de un juego
    public Juego createJuego (String id, String description, int num);

    // OP2: Inicio de una partida de un juego por parte de un usuario. Si el usuario no existe, se crea.
    public Partida iniciarPartida(String idjuego, String iduser) throws JuegoDoesNotExistException, PlayerCurrentlyPlayingException;

    // OP3: Consulta del nivel actual
    public String getNivelActual(String iduser) throws PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException;

    // OP4: Consulta de la puntuación actual
    public String getPuntuacionActual(String iduser) throws PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException;

    // OP5: Pasar de nivel
    public Player nextLevel(String iduser, Integer newpuntos, String fecha) throws PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException, JuegoDoesNotExistException;

    // OP6: Finalizar partida
    public Player endPartida(String iduser) throws PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException;

    // OP7: Consulta de usuarios que han participado en un juego ordenado
    //por puntuación (descendente)
    public List<Player> sortPlayers(Juego juego) throws JuegoDoesNotExistException, PlayerDoesNotExistException;

    // OP8: Consulta de las partidas en las que ha participado un usuario
    public List<Partida> getPartidasPlayer(String username) throws PlayerDoesNotExistException;

    // OP9: Consulta de la actividad de un usuario sobre un juego
    public List<VOPerformance> getPerformance(String idjuego, String iduser) throws JuegoDoesNotExistException;

    public Juego getJuego(String namejuego) throws JuegoDoesNotExistException;
    public Player getPlayer(String username) throws PlayerDoesNotExistException;
    public Partida getPartida (String idjuego, String iduser) throws JuegoDoesNotExistException;
    public List<Partida> getPartidas (String idjuego, String iduser) throws JuegoDoesNotExistException;
    public Partida getPartidaActual (String username) throws PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException;

    public int sizeGames();
}
