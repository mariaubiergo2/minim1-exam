package edu.upc.dsa.models;

public class Objecte {
    String name;
    String description;
    int dsaCoins;

    public Objecte() {    }

    public Objecte(String name, String description, int coins) {
        this();
        this.setDescription(description);
        this.setName(name);
        this.setDsaCoins(coins);
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getDsaCoins() {
        return dsaCoins;
    }
    public void setDsaCoins(int p) {
        this.dsaCoins = p;
    }

    @Override
    public String toString() {
        return "Objecte [name=" + name + ", description=" + description +", dsaCoins=" + dsaCoins +"]";
    }
}