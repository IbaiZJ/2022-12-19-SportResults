package edu.mondragon.webeng1.mvc_exercise.resources;

import java.util.ListResourceBundle;

public class Labels_es extends ListResourceBundle{
    private static final Object[][] contents = {
        {"hello", "Hola"},
        {"userId", "ID de Usuario"},
        {"username", "Nombre de Usuario"},
        {"password", "Contraseña"},
        {"firstName", "Nombre"},
        {"secondName", "Apellido"},
        {"email", "Correo electrónico"},
        {"edit", "Editar"},
        {"delete", "Borrar"},
        {"save", "Guardar"},
        {"home", "Inicio"},
        {"userList", "Lista de Usuarios"},
        {"createUser", "Crear Usuario"},
        {"editUser", "Editar Usuario"},
        /**/
        {"title", "Título"},
        {"body", "Cuerpo"},
        {"newsList", "Noticias"},
        {"newsItem", "Noticia"},
        {"createNewsItem", "Crear noticia"},
        {"editNewsItem", "Editar noticia"},
        {"language", "Idioma"},
        {"creationDate", "Fecha creación"},
        /**/
        {"login", "Iniciar Sessión"},
        {"logged", "¡Has iniciado la sessión!"},
        {"logout", "Cerrar Session"},
        {"author", "Autor"},
        {"language.currentLocale", "Idioma Actual"},
        {"language.en", "Inglés"},
        {"language.es", "Español"},
        {"language.eu", "Euskera"},
        {"error.400.title", "Error 400 - Petición erronea"},
        {"error.400.message","La acción a realizar no es correcta."},
        {"error.403.title", "Error 403 - Prohibido"},
        {"error.403.message","No tienes permisos para hacer esta acción o para acceder a este recurso."},
        {"error.404.title", "Error 404 - Recurso no encontrado."},
        {"error.404.message","El recurso no existe o no ha podido cargarse."},

        {"create", "Crear"},
        {"1st_team's_name", "Nombre del 1º grupo"},
        {"1st_team's_result", "Resultado del 1º grupo"},
        {"2nd_team's_name", "Nombre del 2º grupo"},
        {"2nd_team's_result", "Resultado del 2º grupo"},
        {"createSportResult", "Crear resultado deportivo"},
        {"editSportResult", "Editar resultado deportivo"},
    };
    
    @Override
    protected Object[][] getContents() {
        return contents;
    }
}