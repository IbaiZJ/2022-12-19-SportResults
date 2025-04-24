package edu.mondragon.webeng1.mvc_exercise.resources;

import java.util.ListResourceBundle;

public class Labels extends ListResourceBundle{
    private static final Object[][] contents = {
        {"hello", "Hello"},
        {"userId", "User ID"},
        {"username", "Username"},
        {"password", "Password"},
        {"firstName", "First Name"},
        {"secondName", "Second Name"},
        {"email", "Email"},
        {"edit", "Edit"},
        {"delete", "Delete"},
        {"save", "Save"},
        {"home", "Home"},
        {"userList", "UserList"},
        {"createUser", "Create User"},
        {"editUser", "Edit User"},
        /**/
        {"title", "Title"},
        {"body", "Body"},
        {"newsList", "News"},
        {"newsItem", "News Item"},
        {"createNewsItem", "Create News Item"},
        {"editNewsItem", "Edit News Item"},
        {"language", "Language"},
        {"creationDate", "Creation Date"},
        /**/
        {"login", "Login"},
        {"logged", "You are logged!"},
        {"logout", "Logout"},
        {"author", "Author"},
        {"language.currentLocale", "Current Language"},
        {"language.en", "English"},
        {"language.es", "Spanish"},
        {"language.eu", "Basque"},
        {"error.400.title", "Error 400 - Bad Request"},
        {"error.400.message","Requested action is not valid or doesn not exist."},
        {"error.403.title", "Error 403 - Forbidden"},
        {"error.403.message","You are not allowed to perform this action or to access this resource."},
        {"error.404.title", "Error 404 - Not found."},
        {"error.404.message","The resource cannot be loaded or does not exist."},

        {"create", "Create"},
        {"1st_team's_name", "1st team's name"},
        {"1st_team's_result", "1st team's result"},
        {"2nd_team's_name", "2nd team's name"},
        {"2nd_team's_result", "2nd team's result"},
        {"createSportResult", "Create Sport Result"},
        {"editSportResult", "Edit Sport Result"},
    };
    
    @Override
    protected Object[][] getContents() {
        return contents;
    }
}