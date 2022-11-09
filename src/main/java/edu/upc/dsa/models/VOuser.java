package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class VOuser {
    String dni;
    String name;
    String surnames;
    String birthdate;
    String mail;
    String password;

    public VOuser() {    }

    public VOuser(String dni, String name, String surnames, String birthdate, String mail, String password) {
        this.setDni(dni);
        this.setName(name);
        this.setSurnames(surnames);
        this.setBirthdate(birthdate);
        this.setMail(mail);
        this.setPassword(password);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}