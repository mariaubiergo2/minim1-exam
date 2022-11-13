package edu.upc.dsa;

import edu.upc.dsa.models.Exceptions.JuegoDoesNotExistException;
import edu.upc.dsa.models.Exceptions.PlayerCurrentlyPlayingException;
import edu.upc.dsa.models.Exceptions.PlayerDoesNotExistException;
import edu.upc.dsa.models.Exceptions.PlayerNotCurrentlyPlayingException;
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
    public Juego createJuego(String id, String description, int num)  {
        try {
            Juego j = getJuego(id);
        } catch (JuegoDoesNotExistException e){
            Juego g = new Juego(id, description, num);
            juegos.add(g);
            logger.info("New juego: "+g);
            return g;
        }
        logger.info("There is already a game called: "+id);
        return null;

    }

    @Override
    public Partida iniciarPartida(String idjuego, String iduser) throws JuegoDoesNotExistException, PlayerCurrentlyPlayingException {
        Juego j = getJuego(idjuego);
        if (j==null){
            throw new JuegoDoesNotExistException();
        }
        Player player = this.playersList.get(iduser);
        if (player==null){
            Player newplayer = new Player(iduser);
            logger.info("New player: "+newplayer);
            this.playersList.put(iduser, newplayer);
        }else{
            if(player.getCurrentlyPlaying()){
                logger.info("This player is currently playing");
                throw new PlayerCurrentlyPlayingException();
            }
        }
        Partida newpartida = new Partida(iduser, idjuego);
        this.playersList.get(iduser).addPartida(newpartida);
        logger.info("New partida: "+newpartida);

        return newpartida;
    }

    @Override
    public String getNivelActual(String iduser) throws PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException {
        Partida p = getPartidaActual(iduser);
        if(p!=null){
            logger.info("You are in level "+p.getNivelActual().toString());
            return p.getNivelActual().toString();
        }
        return null;
    }

    @Override
    public String getPuntuacionActual(String iduser) throws PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException {
        Partida p = getPartidaActual(iduser);
        if(p!=null){
            logger.info("You have "+p.getPuntos().toString()+" points!");
            return p.getPuntos().toString();
        }
        return null;
    }

    @Override
    public Player nextLevel(String iduser, Integer newpuntos, String fecha) throws PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException, JuegoDoesNotExistException {
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
    public Player endPartida(String iduser) throws PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException {
        Partida p = getPartidaActual(iduser);
        if(p!=null){
            this.playersList.get(iduser).setCurrentlyPlaying(false);
            logger.info("You ended the partida actual");
            return this.playersList.get(iduser);
        }
        return null;
    }

    @Override
    public List<Player> sortPlayers(Juego juego) throws JuegoDoesNotExistException, PlayerDoesNotExistException {
        Juego jj = getJuego(juego.getNamejuego());
        List<Partida> partidaJuego = new ArrayList<>();
        for(Map.Entry<String , Player> entry : this.playersList.entrySet()){
            Partida p = getPartida(juego.getNamejuego(), entry.getKey());
            if(p!=null)
                partidaJuego.add(p);
        }
        if(partidaJuego.size()!=0){
            partidaJuego.sort((Partida p1, Partida p2) -> Integer.compare(p2.getPuntos(),p1.getPuntos()));
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
    public List<Partida> getPartidasPlayer(String username) throws PlayerDoesNotExistException {
        List<Partida> list = getPlayer(username).getPartidasJugadas().values().stream().collect(toList());
        if (list.size()!=0){
            logger.info("There are "+list.size()+ " partidas of this player");
            return list;
        }
        logger.info("This player has not played");
        return null;
    }

    @Override
    public List<VOPerformance> getPerformance(String idjuego, String iduser) throws JuegoDoesNotExistException {
        List<VOPerformance> list = getPartida(idjuego, iduser).getPerformanceList();
        if(list.size()!=0){
            for (VOPerformance perf : list)
                logger.info(perf);
            return list;
        }
        return null;
    }

    @Override
    public Juego getJuego(String namejuego) throws JuegoDoesNotExistException{
        logger.info("Looking for a game called: "+namejuego);
        for (Juego j : this.juegos){
            if(j.getNamejuego().equals(namejuego)){
                logger.info(j);
                return j;
            }
        }
        logger.info("There is no game called: "+namejuego);
        throw new JuegoDoesNotExistException();
    }

    @Override
    public Player getPlayer(String username) throws PlayerDoesNotExistException {
        if(this.playersList.get(username)==null){
            logger.info("There is no player called: "+username);
            throw new PlayerDoesNotExistException();
        }
        return this.playersList.get(username);
    }

    @Override
    public Partida getPartida(String idjuego, String iduser) throws JuegoDoesNotExistException {
        List<Partida> list = getPartidas(idjuego, iduser);
        if (list!=null) {
            return list.get(list.size()-1);
        }
        return null;
    }

    @Override
    public List<Partida> getPartidas(String idjuego, String iduser) throws JuegoDoesNotExistException {
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
    public Partida getPartidaActual(String username)throws PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException {
        Player player = getPlayer(username);
        if (player.getCurrentlyPlaying()){
            List<Partida> partidasjugadas = (List<Partida>) player.getPartidasJugadas().values().stream().collect(toList());
            logger.info("Partida actual: "+partidasjugadas.get(partidasjugadas.size()-1));
            return partidasjugadas.get(partidasjugadas.size()-1);
        }
        logger.info("You are not playing right now!");
        throw new PlayerNotCurrentlyPlayingException();
    }


    @Override
    public int sizeGames() {
        logger.info("There are "+juegos.size()+" juegos");
        return juegos.size();
    }


}