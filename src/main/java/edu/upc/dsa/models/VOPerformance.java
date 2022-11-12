package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class VOPerformance {
    Integer level;
    Integer puntos;
    String date;

    public VOPerformance() {
        //this.id = RandomUtils.getId();
    }

    public VOPerformance(Integer level, Integer puntos, String date) {
        this.setLevel(level);
        this.setPuntos(puntos);
        this.setDate(date);
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Performance [ level = "+ level +" puntos = "+puntos+", date = " +date+"]";
    }

}