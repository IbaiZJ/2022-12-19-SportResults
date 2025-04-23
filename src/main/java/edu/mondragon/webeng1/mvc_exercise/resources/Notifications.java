package edu.mondragon.webeng1.mvc_exercise.resources;

import java.util.ListResourceBundle;

public class Notifications extends ListResourceBundle {
    private static final Object[][] contents = {
        {"error.forbidden","Forbidden"},
        //LocaleController
        {"message.languageUpdated", "Language has been updated."},
        //LoginController
        {"message.login", "Successfully loged!"},
        {"error.login","Wrong username or password"},
        {"message.logout", "You loged out"},
        //UserController
        {"message.deleteUser", "User deleted."},
        {"error.deleteUser", "Problems removing User."},
        {"message.editUser", "User correctly edited."},
        {"error.editUser", "Problems editing User."},
        {"message.createUser", "User correctly created."},
        {"error.createUser", "Problems creating User."},
        //NewsItemController
        {"message.createNewsItem", "News Item has been created."},
        {"error.createNewsItem", "Problems creating News Item."},
        {"message.editNewsItem", "News Item has been edited."},
        {"error.editNewsItem", "Problems editing News Item."},
        {"message.deleteNewsItem", "News Item has been deleted."},
        {"error.deleteNewsItem", "Problems deleting News Item."},
        //Errors
        {"error.createUser", "Problems editing User."},
        {"error.400.unknown_action","You are trying to perform an unknown action."},
        {"error.403.jsp","You cannot access JSP files directly."},
        {"error.403.not_own_user","You can only modify your own user."},
        {"error.403.not_session_user","Login to perform this action."},
        {"error.403.user_not_author","User has to be the author of the resource to modify it."},
        {"error.404.not_found","The resource does not exist or cannot be loaded."}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}