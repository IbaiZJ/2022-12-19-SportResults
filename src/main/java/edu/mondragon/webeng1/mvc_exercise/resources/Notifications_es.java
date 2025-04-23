package edu.mondragon.webeng1.mvc_exercise.resources;

import java.util.ListResourceBundle;

public class Notifications_es extends ListResourceBundle{
    private static final Object[][] contents = {
        //LocaleController
        {"message.languageUpdated", "Se ha actualizado el idioma."},
        //LoginController
        {"message.login", "¡Has iniciado sessión!"},
        {"error.login","Usuario y contraseña incorrectos."},
        {"message.logout", "Has cerrado sessión."},
        //UserController
        {"message.deleteUser", "Usuario borrado."},
        {"error.deleteUser", "Problemas al borrar el usuario."},
        {"message.editUser", "Usuario editado."},
        {"error.editUser", "Problemas al editar el usuario."},
        {"message.createUser", "Usuario creado."},
        {"error.createUser", "Problemas al crear el Usuario."},
        //NewsItemController
        {"message.createNewsItem", "Se ha creado la noticia."},
        {"error.createNewsItem", "Problemas al crear la noticia."},
        {"message.editNewsItem", "Noticia editada."},
        {"error.editNewsItem", "Problemas al editar la noticia."},
        {"message.deleteNewsItem", "Noticia eliminada."},
        {"error.deleteNewsItem", "Problemas al eliminar la noticia."},
        //Errors
        {"error.createUser", "Problemas al editar el usuario."},
        {"error.400.unknown_action","Estas intentando hacer una acción que no existe."},
        {"error.403.jsp","No tienes permiso para acceder a ficheros JSP directamente."},
        {"error.403.not_own_user","Únicamente puedes modificar tu propio usuario."},
        {"error.403.not_session_user","Inicia sesión para hacer esta acción."},
        {"error.403.user_not_author","Solo el autor puede modificar el recurso."},
        {"error.404.not_found","El recurso no existe o no ha podido cargarse."}
    };
    
    @Override
    protected Object[][] getContents() {
        return contents;
    }
}