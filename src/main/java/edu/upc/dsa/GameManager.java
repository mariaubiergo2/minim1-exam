package edu.upc.dsa;

import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Player;

import java.util.List;

public interface GameManager {
    public Juego createJuego (String id, String description, int num);
    public Partida inicioPartida(String idjuego, String iduser);
    public Partida getPartida(String idjuego, String iduser);
    public Player getCurrentlyPlayingPlayer(String iduser);
    public String nivelActual(String iduser);
    public String puntuacionActual(String iduser);
    public Player nextLevel(String iduser, String fecha);
    public Player endPartida(String iduser);
    public List<Player> sortPlayers(Juego juego);

    public int sizeGames();
}
