package edu.mondragon.webeng1.mvc_exercise.resources;

import java.util.ListResourceBundle;

public class Notifications_eu extends ListResourceBundle{
    private static final Object[][] contents = {
        //LocaleController
        {"message.languageUpdated", "Hizkuntza eguneratu duzu."},
        //LoginController
        {"message.login", "Saioa hasi duzu!"},
        {"error.login","Erabiltzaile edo pasahitz okerrak."},
        {"message.logout", "Saioa itxi duzu."},
        //UserController
        {"message.deleteUser", "Erabiltzailea ezabatu da."},
        {"error.deleteUser", "Arazoak erabiltzailea ezabatzen."},
        {"message.editUser", "Erabiltzailea aldatu da."},
        {"error.editUser", "Arazoak erabiltzailea aldatzen."},
        {"message.createUser", "Erabiltzailea sortu da."},
        {"error.createUser", "Arazoak erabiltzailea sortzen."},
        //NewsItemController
        {"message.createNewsItem", "Berria sortu da."},
        {"error.createNewsItem", "Arazoak berria sortzean."},
        {"message.editNewsItem", "Berria aldatu da."},
        {"error.editNewsItem", "Arazoak berria aldatzean."},
        {"message.deleteNewsItem", "Berria ezabatu da."},
        {"error.deleteNewsItem", "Arazoak berria ezabatzean."},
        //Errors
        {"error.createUser", "Arazoak erabiltzailea sortzen."},
        {"error.400.unknown_action","Existitzen ez den akzio bat egiten saiatzen zabiltza."},
        {"error.403.jsp","Ez daukazu JSP fitxategiak zuzenean ikusteko baimenik."},
        {"error.403.not_own_user","Zure erabiltzailea bakarrik aldatu dezakezu."},
        {"error.403.not_session_user","Saioa hasi akzio hau egiteko."},
        {"error.403.user_not_author","Erabiltzailea baliabidearen autorea izan behar da hau aldatzeko."},
        {"error.404.not_found","Baliabidea ez da existitzen edo ezin da kargatu."},

        {"message.createSportResult", "Kirol emaitza sortu da"},
        {"error.createSportResult", "Huts egin du kirol emaitza sortzean"},
        {"message.editSportResult", "Kirol emaitza editatu da"},
        {"error.editSportResult", "Huts egin du kirol emaitza editatzean"},
        {"message.deleteSportResult", "Kirol emaitza ezabatu da"},
        {"error.deleteSportResult", "Huts egin du kirol emaitza ezabatzean"}

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}