package edu.upc.dsa.models;

public class VOcredencials {
    String mail;
    String password;

    public VOcredencials() {    }

    public VOcredencials(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public VOcredencials (User u){
        /*
        this();
        this.setMail(u.getMail());
        this.setPassword(u.getPassword());
         */
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

    @Override
    public String toString() {
        return "[mail =" + mail +" password=" + password +"]";
    }

}