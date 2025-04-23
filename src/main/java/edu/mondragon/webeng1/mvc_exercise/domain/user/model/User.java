package edu.mondragon.webeng1.mvc_exercise.domain.user.model;

/*
 * This is a Java Bean.
 * These are the unique characteristics they must have:
 *     -Default, no-argumented constructor.
 *     -It should be serializable and implement Serializable interface.
 *     -It may have a number of properties which can be read or written.
 *     -"getters" and "setters" for those properties.
 */
public class User implements java.io.Serializable /* 2nd characteristic */ {
    private static final long serialVersionUID = 3834633934831160740L;

    // 3rd characteristic: multiple properties.
    private int userId = 0;
    private String username = null;
    private String password = null;
    private String firstName = null;
    private String secondName = null;
    private String email = null;

    public User() {
    }// 1st characteristic

    // 4th characteristic: 'getters' and 'setters'.
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
