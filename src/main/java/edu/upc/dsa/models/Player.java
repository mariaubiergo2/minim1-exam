package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Player {
    String username;
    String partidaActual;
    Integer puntos;
    Integer nivelActual;
    LinkedList<String> performance;


    public Player() {
        //this.id = RandomUtils.getId();
    }

    public Player(String username, String partidaActual, Integer puntos) {
        this.setUsername(username);
        this.setPartidaActual(partidaActual);
        this.setNivelActual(1);
        this.setPuntos(50);
        this.performance = (new LinkedList<String>());
    }

    public Integer getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(Integer nivelActual) {
        this.nivelActual = nivelActual;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPartidaActual() {
        return partidaActual;
    }

    public void setPartidaActual(String partidaActual) {
        this.partidaActual = partidaActual;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public LinkedList<String> getPerformance() {
        return performance;
    }

    public void setPerformance(String level) {
        this.performance.add(level);
    }

    @Override
    public String toString() {
        return "Player [username=" + username + ", partidaAct=" + partidaActual+ ", puntos=" + puntos+", nivelAct=" + nivelActual+ ", performance=" + performance+"]";
    }


}