package edu.upc.dsa;

import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Player;

import java.util.*;

import org.apache.log4j.Logger;

import static java.lang.Integer.parseInt;

public class ShopManagerImpl implements ShopManager {
    private static ShopManager instance;
    protected List<Player> players;
    protected Map<String, Partida> partidasId;
    protected Map<String, Player> partidasPlayers;
    protected  List<Juego> juegos;
    final static Logger logger = Logger.getLogger(ShopManagerImpl.class);

    public ShopManagerImpl() {
        this.players = new LinkedList<>();
        this.partidasPlayers = new HashMap<>();
        this.partidasId = new HashMap<>();
        this.juegos= new ArrayList<>();
    }

    public static ShopManager getInstance() {
        if (instance==null) instance = new ShopManagerImpl();
        return instance;
    }

    public int size() {
        int ret = this.players.size();
        logger.info("size " + ret);

        return ret;
    }

    public Player addUser(Player t) {
        logger.info("new Track " + t);

        this.players.add (t);
        logger.info("new Track added");
        return t;
    }

    @Override
    public Juego createJuego(String id, String description, int num) {
        Juego g = new Juego(id, description, num);
        juegos.add(g);
        logger.info("New juego: "+g);
        return g;
    }

    @Override
    public Partida inicioPartida(String idjuego, String iduser) {
        Partida pp = this.getPartida(idjuego, iduser);
        if (pp!=null){
            if(pp.getCurrentlyPlaying()){
                logger.info("This user is currently playing");
                return null;
            }
        }
        for(Juego j : juegos){
            if (j.getNamejuego().equals(idjuego)){
                Partida createdPartida = new Partida(iduser, idjuego);
                this.partidasId.put(createdPartida.getId(), createdPartida);
                this.partidasPlayers.put(createdPartida.getId(), new Player(iduser, pp.getId(), 50));
                logger.info("New partida:"+createdPartida);
                return createdPartida;
            }
        }
        logger.info("This game does not exist");
        return null;
    }

    @Override
    public Partida getPartida(String idjuego, String iduser) {
        for(Map.Entry<String, Player> entry : this.partidasPlayers.entrySet()){
            if(entry.getValue().getUsername().equals(iduser)){
                if(this.partidasId.get(entry.getKey()).getJuego().equals(idjuego)){
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
                    logger.info("Nice search! "+this.partidasPlayers.get(p.getId()));
                    return this.partidasPlayers.get(p.getId());
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

            String idjuego = partidasId.get(play.getPartidaActual()).getJuego();
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
    public List<Player> sortPlayers() {
        List<User> list = new ArrayList<>(this.users.values());

        list.sort((User u1, User u2)-> {
            int res = u1.getSurnames().compareToIgnoreCase(u2.getSurnames());
            if (res == 0){
                res = u1.getName().compareToIgnoreCase(u2.getName());
            }
            return res;
        });

        return list;
        return null;
    }


    public List<Player> findAll() {
        return this.players;
    }



}