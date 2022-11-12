package edu.upc.dsa;

import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Player;

import java.util.*;

import org.apache.log4j.Logger;

import static java.lang.Integer.parseInt;

public class GameManagerImpl implements GameManager {
    private static GameManager instance;
    protected Map<String, Player> playersList; //string = username; Player = player
    protected  List<Juego> juegos; // catalogo de juegos
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public GameManagerImpl() {
        this.playersList = new HashMap<>();
        this.juegos= new ArrayList<>();
    }

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }

    @Override
    public Juego createJuego(String id, String description, int num) {
        Juego j = getJuego(id);
        if (j!=null){
            logger.info("There is already a game called: "+id);
            return null;
        }
        Juego g = new Juego(id, description, num);
        juegos.add(g);
        logger.info("New juego: "+g);
        return g;
    }

    @Override
    public Partida iniciarPartida(String idjuego, String iduser) {
        Juego j = getJuego(idjuego);
        if (j==null){
            return null;
        }
        Player player = this.playersList.get(iduser);
        if (player==null){
            Player newplayer = new Player(iduser);
            logger.info("New player: "+newplayer);
            this.playersList.put(iduser, newplayer);
        }else{
            if(player.getCurrentlyPlaying()){
                logger.info("This player is currently playing");
                return null;
            }
        }
        Partida newpartida = new Partida(iduser, idjuego);
        this.playersList.get(iduser).addPartida(newpartida);
        logger.info("New partida: "+newpartida);
        logger.info(this.playersList.get(iduser).getCurrentlyPlaying().toString());

        return newpartida;
    }

    @Override
    public String getNivelActual(String iduser) {
        Partida p = getPartidaActual(iduser);
        if(p!=null){
            logger.info("You are in level "+p.getNivelActual().toString());
            return p.getNivelActual().toString();
        }
        return null;
    }

    @Override
    public String getPuntuacionActual(String iduser) {
        Partida p = getPartidaActual(iduser);
        if(p!=null){
            logger.info("You have "+p.getPuntos().toString()+" points!");
            return p.getPuntos().toString();
        }
        return null;
    }

    @Override
    public Player nextLevel(String iduser, Integer newpuntos, String fecha) {
        Partida p = getPartidaActual(iduser);
        if(p!=null){
            if(p.getNivelActual()==getJuego(p.getNamejuego()).getNumniveles()){

            }
        }
        return null;
    }

    @Override
    public Player endPartida(String iduser) {
        return null;
    }

    @Override
    public List<Player> sortPlayers(Juego juego) {
        return null;
    }
/*
    @Override
    public Partida getPartida(String idjuego, String iduser) {
        for(Map.Entry<String, Player> entry : this.partidasPlayers.entrySet()){
            if(entry.getValue().getUsername().equals(iduser)){
                if(this.partidasId.get(entry.getKey()).getNamejuego().equals(idjuego)){
                    logger.info("Partida matching user and game found!");
                    return this.partidasId.get(entry.getValue());
                }

                }
            }

        return null;
        }

    @Override
    public Player getCurrentlyPlayingPlayer(String iduser) {
        logger.info("Looking for player: "+iduser);
        for (Juego j : juegos){
            Partida p = getPartida(j.getNamejuego(), iduser);
            if (p!=null){
                logger.info("There is a partida "+p);
                if(p.getCurrentlyPlaying()){
                    logger.info("Nice search! "+this.partidasPlayers.get(p.getIdpartida()));
                    return this.partidasPlayers.get(p.getIdpartida());
                }
            }
        }
        logger.info("There is no partida or it is not currently playing");
        return null;
    }


    @Override
    public String nivelActual(String iduser) {
        logger.info("Looking for actual level of: "+iduser);
        Player play = getCurrentlyPlayingPlayer(iduser);
        if (play!=null){
            return play.getNivelActual().toString();
        }
        return null;
    }

    @Override
    public String puntuacionActual(String iduser) {
        logger.info("Looking for actual puntuation of: "+iduser);
        Player play = getCurrentlyPlayingPlayer(iduser);
        if (play!=null){
            return play.getPuntos().toString();
        }
        return null;
    }

    @Override
    public Player nextLevel(String iduser, String fecha) {
        String nivelActual = nivelActual(iduser);
        if(nivelActual!=null){
            Player play = getCurrentlyPlayingPlayer(iduser);
            logger.info("Before passing the level: "+play);

            String idjuego = partidasId.get(play.getPartidaActual()).getNamejuego();
            for (Juego j : juegos){
                if(j.getNamejuego().equals(idjuego)){
                    logger.info(iduser+" is playing to "+j);
                    if(nivelActual.equals(j.getNumniveles().toString())){
                        logger.info("You are in the last level!");
                        this.partidasPlayers.get(play.getPartidaActual()).setNivelActual(100);
                        logger.info("After passing the level: "+getCurrentlyPlayingPlayer(iduser));
                        endPartida(iduser);
                    }
                }
            }
            this.partidasPlayers.get(play.getPartidaActual()).setNivelActual(parseInt(nivelActual)+1);
            String perf = "{Level "+nivelActual+ ", points: "+play.getPuntos()+", fecha: "+fecha+" }";
            this.partidasPlayers.get(play.getPartidaActual()).setPerformance(perf);
            logger.info("After passing the level: "+getCurrentlyPlayingPlayer(iduser));
        }
        return null;
    }

    @Override
    public Player endPartida(String iduser) {
        Player play = getCurrentlyPlayingPlayer(iduser);
        if (play!=null){
            this.partidasPlayers.get(play.getPartidaActual()).setPartidaActual(null);
            this.partidasId.get(play.getPartidaActual()).setCurrentlyPlaying(false);
            return play;
        }
        logger.info("Impossible to end the partida :(");
        return null;
    }

    @Override
    public List<Player> sortPlayers(Juego juego) {
        List<Player> list = new ArrayList<>(this.partidasPlayers.values());
        List<Player> sorted = new ArrayList<>();

        for(Player p: list){
            if(this.partidasId.get(p.getPartidaActual()).equals(juego)){
                sorted.add(p);
            }
        }

        sorted.sort((Player u1, Player u2)-> Integer.compare(u2.getPuntos(),u1.getPuntos()));

        return sorted;
    }

 */

    @Override
    public Juego getJuego(String namejuego) {
        logger.info("Looking for a game called: "+namejuego);
        for (Juego j : this.juegos){
            if(j.getNamejuego().equals(namejuego)){
                logger.info(j);
                return j;
            }
        }
        logger.info("There is no game called: "+namejuego);
        return null;
    }

    @Override
    public Player getPlayer(String username) {
        return this.playersList.get(username);
    }

    @Override
    public Partida getPartida(String idjuego, String iduser) {
        Juego j = getJuego(idjuego);
        if(j!=null){
            Player player = this.playersList.get(iduser);
            if (player!=null){
                List<Partida> partidas = player.getPartidasJugadas(idjuego);
                logger.info(partidas.get(partidas.size()-1));
                return partidas.get(partidas.size()-1);
            }
        }
        return null;
    }

    @Override
    public Partida getPartidaActual(String username) {
        Player player = getPlayer(username);
        if (player==null){
            logger.info("This player does not exist");
            return null;
        }
        if (player.getCurrentlyPlaying()){
            Partida p = player.getPartidasJugadas().get(player.getPartidasJugadas().size()-1);
            return p;
        }
        logger.info("You are not playing right now!");
        return null;
    }


    @Override
    public int sizeGames() {
        logger.info("There are "+juegos.size()+" juegos");
        return juegos.size();
    }


}