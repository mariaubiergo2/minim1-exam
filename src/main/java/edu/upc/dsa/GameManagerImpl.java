package edu.upc.dsa;

import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Player;

import java.util.*;

import edu.upc.dsa.models.VOPerformance;
import org.apache.log4j.Logger;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

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
                logger.info("You are in the last level!");
                this.playersList.get(iduser).getPartidasJugadas().get(p.getIdpartida()).addPuntos(100);
                endPartida(iduser);
                return this.playersList.get(iduser);
            }
            VOPerformance performance = new VOPerformance(p.getNivelActual(),p.getPuntos(), fecha);
            this.playersList.get(iduser).getPartidasJugadas().get(p.getIdpartida()).addPerformance(performance);
            this.playersList.get(iduser).getPartidasJugadas().get(p.getIdpartida()).addPuntos(newpuntos);
            this.playersList.get(iduser).getPartidasJugadas().get(p.getIdpartida()).sumNivelActual(1);

            logger.info("Performance actual: "+this.playersList.get(iduser).getPartidasJugadas().get(p.getIdpartida()).getPerformanceList());
        }
        return null;
    }

    @Override
    public Player endPartida(String iduser) {
        Partida p = getPartidaActual(iduser);
        if(p!=null){
            this.playersList.get(iduser).setCurrentlyPlaying(false);
            logger.info("You finished playing");
        }
        return null;
    }

    @Override
    public List<Player> sortPlayers(Juego juego) {
        List<Partida> partidaJuego = new ArrayList<>();
        for(Map.Entry<String , Player> entry : this.playersList.entrySet()){
            Partida p = getPartida(juego.getNamejuego(), entry.getKey());
            if(p!=null)
                partidaJuego.add(p);
        }
        if(partidaJuego.size()!=0){
            partidaJuego.sort((Partida p1, Partida p2) -> Integer.compare(p1.getPuntos(),p2.getPuntos()));
            List<Player> result = new ArrayList<>();
            for(Partida partida : partidaJuego){
                result.add(getPlayer(partida.getUsername()));
            }
            return result;
        }
        logger.info("No one played to this game");

        return null;
    }

    @Override
    public List<Partida> getPartidasPlayer(String username) {
        return getPlayer(username).getPartidasJugadas().values().stream().collect(toList());
    }

    @Override
    public List<VOPerformance> getPerformance(String idjuego, String iduser) {
        return getPartida(idjuego, iduser).getPerformanceList();
    }

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
        return getPartidas(idjuego, iduser).get(getPartidas(idjuego, iduser).size()-1);
    }

    @Override
    public List<Partida> getPartidas(String idjuego, String iduser) {
        Juego j = getJuego(idjuego);
        if(j==null){
            return null;
        }
        Player player = this.playersList.get(iduser);
        if (player==null){
            logger.info("This player does not exist, yet");
            return null;
        }
        HashMap<String, Partida> partidas = player.getPartidasJugadas();
        List<Partida> partidasplayed = new ArrayList<>();
        for(Map.Entry<String, Partida> entry : partidas.entrySet()){
            if(entry.getValue().getNamejuego().equals(idjuego)) {
                partidasplayed.add(entry.getValue());
            }
        }
        logger.info("There are "+partidasplayed.size()+" partidas played to this game");
        return partidasplayed;
    }


    @Override
    public Partida getPartidaActual(String username) {
        Player player = getPlayer(username);
        if (player==null){
            logger.info("This player does not exist");
            return null;
        }
        if (player.getCurrentlyPlaying()){
            List<Partida> partidasjugadas = (List<Partida>) player.getPartidasJugadas().values().stream().collect(toList());
            logger.info("Partida actual: "+partidasjugadas.get(partidasjugadas.size()-1));
            return partidasjugadas.get(partidasjugadas.size()-1);
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