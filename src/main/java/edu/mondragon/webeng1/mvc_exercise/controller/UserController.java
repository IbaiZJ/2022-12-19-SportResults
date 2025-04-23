package edu.mondragon.webeng1.mvc_exercise.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;
import edu.mondragon.webeng1.mvc_exercise.domain.user.service.UserService;
import edu.mondragon.webeng1.mvc_exercise.helper.ControllerHelper;

@WebServlet(name = "UserController", urlPatterns = { "/user/*" })
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private ControllerHelper controllerHelper;

    @Inject
    private UserService userService;

    // Maps actions name with actions methods
    private Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> getActionsMap = new HashMap<>() {{
        put("delete", UserController.this::deleteUser);
        put("create", UserController.this::showUserForm);
        put("edit", UserController.this::showUserForm);
        put("view", UserController.this::showUser);
        put("list", UserController.this::listUsers);
    }};
    private Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> postActionsMap = new HashMap<>() {{
        put("create", UserController.this::createUser);
        put("edit", UserController.this::editUser);
    }};
    
    public UserController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = controllerHelper.getAction();

        getActionsMap.getOrDefault(
            action,
            (req, res) -> listUsers(request, response)
        ).accept(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = controllerHelper.getAction();

        postActionsMap.getOrDefault(
            action,
            (req, res) -> listUsers(request, response)
        ).accept(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        int userId = controllerHelper.getId();

        if (userId != -1 && userService.deleteUser(userId)) {
            session.setAttribute("message", "message.deleteUser");
            session.removeAttribute("user");
        } else {
            session.setAttribute("error", "error.deleteUser");
        }

        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            logger.error("Error redirecting to root view.", e);
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        int userId = controllerHelper.getId();
        User user = userService.loadUser(userId);
        logger.debug("Show User Form: " + userId);

        if (user == null) {
            // Guard clause
            logger.error("User cannot be edited.");
            ControllerErrorHelper.respondNotFound(request, response, this.getServletContext());
            return;
        }
    
        user.setEmail(request.getParameter("email"));
        user.setFirstName(request.getParameter("firstName"));
        user.setPassword(request.getParameter("password"));
        user.setSecondName(request.getParameter("secondName"));
        user.setUsername(request.getParameter("username"));
        userService.saveUser(user);

        if (user.getUserId() == 0) {
            // Guard clause
            session.setAttribute("error", "error.editUser");
            try {
                response.sendRedirect("/user/" + userId + "/edit");
            } catch (IOException e) {
                logger.error("Error redirecting to edit user form.", e);
            }
            return;
        }

        session.setAttribute("message", "message.editUser");
        session.setAttribute("user", user);

        try {
            response.sendRedirect("/user/" + userId);
        } catch (IOException e) {
            logger.error("Error redirecting to user view.", e);
        }

    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setFirstName(request.getParameter("firstName"));
        user.setPassword(request.getParameter("password"));
        user.setSecondName(request.getParameter("secondName"));
        user.setUsername(request.getParameter("username"));

        userService.saveUser(user);

        User sessionUser = (User) session.getAttribute("user");
        String redirectUrl = "/";
        if (user.getUserId() != 0) {
            request.setAttribute("user", user);
            session.setAttribute("message", "message.createUser");
            if (sessionUser != null) // If a user creates another user, redirect to the view
                redirectUrl = "/user/" + user.getUserId();
            // else, redirect to the index so they can login.
        } else {
            session.setAttribute("error", "error.createUser");
            if (sessionUser != null)
                redirectUrl = "/user"; // If a user tried to creates another user, redirect to the list view.
            // else, redirect to the index so they can login.
        }

        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            logger.error("Error redirecting to " + redirectUrl, e);
        }
    }

    private void showUser(HttpServletRequest request, HttpServletResponse response) {
        int userId = controllerHelper.getId();
        logger.debug("Show User: " + userId);
        User user = userService.loadUser(userId);

        if (user == null || user.getUserId() == 0) {
            // Guard Clause
            logger.error("User cannot be found.");
            ControllerErrorHelper.respondNotFound(request, response, this.getServletContext());
            return;
        }

        request.setAttribute("user", user);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/user/user.jsp");
        
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to user view.", e);
        }

    }

    private void showUserForm(HttpServletRequest request, HttpServletResponse response) {
        int userId = controllerHelper.getId();
        logger.debug("Show User Form: " + userId);
        if (userId > 0) {
            // Edit user
            User user = userService.loadUser(userId);
            if (user == null) {
                // Guard Clause
                logger.error("User "+userId+" cannot be found.");
                ControllerErrorHelper.respondNotFound(request, response, this.getServletContext());
                return;
            }
            request.setAttribute("user", user);
        } // Else, Create user

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/user/user_form.jsp");

        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to user form.", e);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response){
        logger.debug("List Users");
        ArrayList<User> users = userService.loadUsers();
        request.setAttribute("userList", users);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/user/user_list.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to user list.", e);
        }
    }
}
