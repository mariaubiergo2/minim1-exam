package edu.upc.dsa.models;

public class VOnextLevelCredencials {
    String iduser;
    Integer newpuntos;
    String fecha;

    public VOnextLevelCredencials() {
        //this.id = RandomUtils.getId();
    }

    public VOnextLevelCredencials(String iduser, Integer newpuntos, String fecha) {
        this.setIduser(iduser);
        this.setNewpuntos(newpuntos);
        this.setFecha(fecha);
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public Integer getNewpuntos() {
        return newpuntos;
    }

    public void setNewpuntos(Integer newpuntos) {
        this.newpuntos = newpuntos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}