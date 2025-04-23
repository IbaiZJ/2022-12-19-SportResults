package edu.mondragon.webeng1.mvc_exercise.domain.user.repository;
import java.util.ArrayList;

import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;

public interface UserRepository {
    public User insertUser(User user);
    public User loadUser(String username,String password);
    public User loadUser(int userId);
    public ArrayList<User> loadUsers();
    public User updateUser(User user);
    public boolean deleteUser(int userId);
}
