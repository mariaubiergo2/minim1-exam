package edu.upc.dsa;

import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Player;

import java.util.List;

public interface GameManager {

    // OP1:  Creación de un juego
    public Juego createJuego (String id, String description, int num);

    // OP2: Inicio de una partida de un juego por parte de un usuario
    public Partida iniciarPartida(String idjuego, String iduser);

    // OP3: Consulta del nivel actual
    public String getNivelActual(String iduser);

    // OP4: Consulta de la puntuación actual
    public String getPuntuacionActual(String iduser);

    // OP5: Pasar de nivel
    public Player nextLevel(String iduser, Integer newpuntos, String fecha);

    // OP6: Finalizar partida
    public Player endPartida(String iduser);

    // OP7: Consulta de usuarios que han participado en un juego ordenado
    //por puntuación (descendente)
    public List<Player> sortPlayers(Juego juego);

    // OP8: Consulta de las partidas en las que ha participado un usuario

    // OP9: Consulta de la actividad de un usuario sobre un juego


    public Juego getJuego(String namejuego);
    public Player getPlayer(String username);
    public Partida getPartida (String idjuego, String iduser);
    public Partida getPartidaActual (String username);






    public int sizeGames();
}
