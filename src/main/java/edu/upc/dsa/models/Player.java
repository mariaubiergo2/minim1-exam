package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Player {
    String username;
    Boolean currentlyPlaying;
    HashMap<String, Partida> partidasJugadas; //string = idPartida; Partida = partida.

    //HashMap<String, List<String>> juegosJugados; //string= namejuego; List = list idpartidas.

    public Player() {
        //this.id = RandomUtils.getId();
    }

    public Player(String username) {
        this.setUsername(username);
        this.setCurrentlyPlaying(false);
        this.setPartidasJugadas();
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public HashMap<String, Partida> getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas() {
        this.partidasJugadas = new HashMap<>();
    }

    public Boolean getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(Boolean currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public void addPartida(Partida partida){
        this.partidasJugadas.put(partida.getIdpartida(), partida);
        this.setCurrentlyPlaying(true);
    }


    @Override
    public String toString() {
        return "Player [username=" + username +"]";
    }


}