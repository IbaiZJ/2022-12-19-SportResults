package edu.mondragon.webeng1.mvc_exercise.resources;

import java.util.ListResourceBundle;

public class Labels_eu extends ListResourceBundle{
    private static final Object[][] contents = {
        {"hello", "Kaixo"},
        {"userId", "Erabiltzailearen IDa"},
        {"username", "Erabiltzaile Izena"},
        {"password", "Pasahitza"},
        {"firstName", "Izena"},
        {"secondName", "Abizena"},
        {"email", "e-posta"},
        {"edit", "Aldatu"},
        {"delete", "Ezabatu"},
        {"save", "Gorde"},
        {"home", "Hasiera"},
        {"userList", "Erabiltzaileen Zerrenda"},
        {"createUser", "Erabiltzailea Sortu"},
        {"editUser", "Erabiltzailea Aldatu"},
        /**/
        {"title", "Titulua"},
        {"body", "Edukia"},
        {"newsList", "Berriak"},
        {"newsItem", "Berria"},
        {"createNewsItem", "Berria sortu"},
        {"editNewsItem", "Berria aldatu"},
        {"language", "Hizkuntza"},
        {"creationDate", "Sormen Data"},
        /**/
        {"login", "Saioa Hasi"},
        {"logged", "Saioa hasi duzu!"},
        {"logout", "Saioa Itxi"},
        {"author", "Egilea"},
        {"language.currentLocale", "Momentuko Hizkuntza"},
        {"language.en", "Ingelesa"},
        {"language.es", "Gaztelera"},
        {"language.eu", "Euskara"},
        {"error.400.title", "Error 400 - Eskari ez baliogarria"},
        {"error.400.message","Egin nahi duzun akzioa ez da existitzen."},
        {"error.403.title", "Error 403 - Baimenik ez"},
        {"error.403.message","Akzio hau egiteko baimenik ez duzu edo baliobidera sarrerarik ez daukazu."},
        {"error.404.title", "Error 404 - Ez da Baliabidea aurkitu."},
        {"error.404.message","Bila zabiltzen baliabidea ez da existitzen edo ezin izan da kargatu."}
    };
    
    @Override
    protected Object[][] getContents() {
        return contents;
    }
}