package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Partida {
    String id;
    String username;
    String juego;
    Boolean currentlyPlaying;

    public Partida() {
        //this.id = RandomUtils.getId();
    }

    public Partida(String username, String juego) {
        this.id = RandomUtils.getId();
        this.setUsername(username);
        this.setJuego(juego);
        this.setCurrentlyPlaying(true);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJuego() {
        return juego;
    }

    public Boolean getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(Boolean currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }

    @Override
    public String toString() {
        return "Partida [ idpartida= "+id+" iduser="+username+", title=" + username + ", idjuego=" + juego+ ", currentlyplaying=" + currentlyPlaying +"]";
    }

}