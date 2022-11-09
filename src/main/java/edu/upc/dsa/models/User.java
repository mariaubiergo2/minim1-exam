package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class User {

    String id;
    String title;
    String singer;

    public User() {
        //this.id = RandomUtils.getId();
    }



    @Override
    public String toString() {
        return "User [id="+id+", title=" + title + ", singer=" + singer +"]";
    }

}