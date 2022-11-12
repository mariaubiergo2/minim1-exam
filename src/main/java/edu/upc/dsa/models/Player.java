package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Player {
    String username;
    Boolean currentlyPlaying;
    List<Partida> partidasJugadas; //string = namejuego; Partida = partida.

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

    public List<Partida> getPartidasJugadas() {
        return partidasJugadas;
    }
    public List<Partida> getPartidasJugadas(String namejuego) {
        List<Partida> result = new ArrayList<>();
        for(Partida p : partidasJugadas){
            if(p.getNamejuego().equals(namejuego))
                result.add(p);
        }
        return result;
    }

    public void setPartidasJugadas() {
        this.partidasJugadas = new LinkedList<>();
    }

    public Boolean getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(Boolean currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public void addPartida(Partida partida){
        this.partidasJugadas.add(partida);
        this.setCurrentlyPlaying(true);
    }


    @Override
    public String toString() {
        return "Player [username=" + username +"]";
    }


}