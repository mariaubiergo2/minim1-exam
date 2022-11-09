package edu.upc.dsa;

import edu.upc.dsa.models.User;

import java.util.List;

public interface ShopManager {
    public User addUser(String title, String singer);
    public User getTrack(String id);
    public List<User> findAll();
    public void deleteTrack(String id);
    public User updateTrack(User t);

    public int size();
}
