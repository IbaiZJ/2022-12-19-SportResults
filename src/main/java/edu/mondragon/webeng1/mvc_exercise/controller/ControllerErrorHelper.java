package edu.mondragon.webeng1.mvc_exercise.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ControllerErrorHelper {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void respondNotFound(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
        HttpSession session = request.getSession(true);
        int errorCode = HttpServletResponse.SC_NOT_FOUND;
        logger.error("Element Not found.");
        session.setAttribute("error", "error.404.not_found");
        session.setAttribute("errorCode", errorCode);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/view/error.jsp");
        response.setStatus(errorCode);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to error page.", e);
        }
    }
}
