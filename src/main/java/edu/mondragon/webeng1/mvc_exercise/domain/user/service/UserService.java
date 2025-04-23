package edu.mondragon.webeng1.mvc_exercise.domain.user.service;

import java.util.ArrayList;

import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;
import edu.mondragon.webeng1.mvc_exercise.domain.user.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {
	@Inject
	private UserRepository repository;

	public User login(String username, String password){
		return repository.loadUser(username, password);
	}

    public User loadUser(String username, String password) {
        return repository.loadUser(username, password);
    }

    public User loadUser(int userId) {
        return repository.loadUser(userId);
    }

    public ArrayList<User> loadUsers() {
        return repository.loadUsers();
    }

    public User saveUser(User user) {
        User retUser;
        if (user.getUserId() == 0) {
            retUser = repository.insertUser(user);
        } else {
            retUser = repository.updateUser(user);
        }
        return retUser;
    }

    public boolean deleteUser(int userId) {
        return repository.deleteUser(userId);
    }

}
