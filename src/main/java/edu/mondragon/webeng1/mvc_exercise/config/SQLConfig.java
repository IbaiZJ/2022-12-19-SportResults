package edu.mondragon.webeng1.mvc_exercise.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SQLConfig {
    private String connectionString;
    private String username;
    private String password;

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @PostConstruct
    private void init() {
        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            logger.debug("Loading db.properties file.");
            prop.load(classLoader.getResourceAsStream("db.properties"));
            
            connectionString = prop.getProperty("connectionString");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        } catch (FileNotFoundException e) {
            logger.error("db.properties file not found.", e);
        } catch (IOException e) {
            logger.error("could not load db.properties file.", e);
        }
    }

    /**
     * Get a new SQLite Connection.
     */
    public Connection connect() {
        Connection connection = null;
        try {
            // Get connection
            connection = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException e) {
            logger.error("Could not get a connection", e);
        }
        return connection;
    }

    /**
     * Close a SQLite Connection.
     */
    public void disconnect(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.clearWarnings();
                statement.close();
            }

            if (connection != null) {
                connection.clearWarnings();
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Error disconnecting:", e);
        }
    }
}