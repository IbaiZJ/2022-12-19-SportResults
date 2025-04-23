package edu.mondragon.webeng1.mvc_exercise.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;
import edu.mondragon.webeng1.mvc_exercise.domain.user.service.UserService;
import edu.mondragon.webeng1.mvc_exercise.helper.ControllerHelper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@WebServlet(name = "LoginController", urlPatterns = { "/login/*" })
public class LoginController extends HttpServlet {    
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private ControllerHelper controllerHelper;

    @Inject
    private UserService userService;

    private static final long serialVersionUID = 1L;

    // Maps actions name with actions methods
    private Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> actionsMap = new HashMap<>() {{
        put("login", LoginController.this::login);
        put("logout", LoginController.this::logout);
    }};
    

    public LoginController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get action parameters from the request
        String action = controllerHelper.getAction();
        logger.debug(action);

        actionsMap.getOrDefault(
            action,  // string to search in the map
            (req, res) -> logout(request, response) // method to call if the string is not found
        ).accept(request, response); // call the method found in the map

        response.sendRedirect("/");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);
        String username = Optional.ofNullable(request.getParameter("username")).orElse("");
        String password = Optional.ofNullable(request.getParameter("password")).orElse("");
        
        // Check if the user exists in the database
        User user = userService.login(username, password);

        // Save login result in session
        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("message", "message.login");
        } else {
            session.removeAttribute("user");
            session.setAttribute("wrongUsername", username);
            session.setAttribute("error", "error.login");
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);
        session.removeAttribute("user");
        session.setAttribute("message", "message.logout");
    }

}
