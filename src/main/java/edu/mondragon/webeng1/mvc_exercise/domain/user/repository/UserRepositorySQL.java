package edu.mondragon.webeng1.mvc_exercise.domain.user.repository;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mondragon.webeng1.mvc_exercise.config.SQLConfig;
import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@ApplicationScoped
public class UserRepositorySQL implements UserRepository {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private SQLConfig sqlConfig;

    @Override
    public User insertUser(User user) {
        String sqlInsert = "INSERT INTO user(username,password,first_name,second_name,email) VALUES(?,?,?,?,?)";
        User retUser;

        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getFirstName());
            stm.setString(4, user.getSecondName());
            stm.setString(5, user.getEmail());
            logger.debug(stm.toString());
            if (stm.executeUpdate() > 0) {
                // Get the ID
                try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            retUser = user;
        } catch (SQLException e) {
            logger.error("Error insertUser " + user.getUsername(), e);
            retUser = null;
        }
        sqlConfig.disconnect(connection, stm);
        return retUser;
    }

    @Override
    public User loadUser(String username, String password) {
        String sqlQuery = "SELECT * FROM user WHERE username=? AND password=?";
        User retUser = null;
        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlQuery);
            stm.setString(1, username);
            stm.setString(2, password);
            logger.info(stm.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                retUser = new User();
                retUser.setUserId(rs.getInt("userId"));
                retUser.setUsername(rs.getString("username"));
                retUser.setPassword(rs.getString("password"));
                retUser.setFirstName(rs.getString("first_name"));
                retUser.setSecondName(rs.getString("second_name"));
                retUser.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            logger.error("Error loadUser " + username);
        }
        sqlConfig.disconnect(connection, stm);
        return retUser;
    }

    @Override
    public User loadUser(int userId) {
        String sqlQuery = "SELECT * FROM user WHERE userId=?";
        User user = null;
        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlQuery);
            stm.setInt(1, userId);
            logger.debug(stm.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setSecondName(rs.getString("second_name"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            logger.debug("Error loadUser " + userId, e);
        }
        sqlConfig.disconnect(connection, stm);
        return user;
    }

    @Override
    public ArrayList<User> loadUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        Connection connection = sqlConfig.connect();
        String sqlQuery = "SELECT * FROM user";

        ResultSet rs = null;
        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sqlQuery);
            rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setSecondName(rs.getString("second_name"));
                user.setEmail(rs.getString("email"));

                userList.add(user);
            }
        } catch (SQLException e) {
            logger.error("Error DaoUserMysql loadUsers",e);
        }
        sqlConfig.disconnect(connection, stm);
        return userList;
    }

    @Override
    public User updateUser(User user) {
        String sqlUpdate = "UPDATE user SET username=?, password=?, first_name=?, second_name=?, email=? WHERE userId=?";
        User retUser = null;
        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlUpdate);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getFirstName());
            stm.setString(4, user.getSecondName());
            stm.setString(5, user.getEmail());
            stm.setInt(6, user.getUserId());
            logger.debug(stm.toString());

            if (stm.executeUpdate() < 1) {
                user.setUserId(0);
            }
            retUser = user;
        } catch (SQLException e) {
            logger.error("Error updateUser " + user.getUserId(), e);
        }
        sqlConfig.disconnect(connection, stm);
        return retUser;
    }

    @Override
    public boolean deleteUser(int userId) {
        boolean ret = false;
        String sqlDelete = "DELETE FROM user WHERE userId=?";
        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlDelete);
            stm.setInt(1, userId);
            if (stm.executeUpdate() > 0) {
                ret = true;
            } else {
                logger.error("Could not delete user " + userId);
            }
        } catch (SQLException e) {
            logger.error("Error deleteUser " + userId);
        }

        return ret;
    }

}
