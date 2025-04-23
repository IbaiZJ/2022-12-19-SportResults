package edu.mondragon.webeng1.mvc_exercise.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mondragon.webeng1.mvc_exercise.domain.sportResult.model.SportResult;
import edu.mondragon.webeng1.mvc_exercise.domain.sportResult.service.SportResultSevice;
import edu.mondragon.webeng1.mvc_exercise.helper.ControllerHelper;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UserController", urlPatterns = { "/user/*" })
public class SportResultController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private ControllerHelper controllerHelper;

    @Inject
    private SportResultSevice sportResultSevice;

    private Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> getActionsMap = new HashMap<>() {
        {
            // put("delete", SportResultController.this::deleteUser);
            // put("create", SportResultController.this::showUserForm);
            // put("edit", SportResultController.this::showUserForm);
            // put("view", SportResultController.this::showUser);
            // put("list", SportResultController.this::listUsers);
        }
    };
    private Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> postActionsMap = new HashMap<>() {
        {
            // put("create", SportResultController.this::createUser);
            // put("edit", SportResultController.this::editUser);
        }
    };

    public SportResultController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = controllerHelper.getAction();

        getActionsMap.getOrDefault(
                action,
                (req, res) -> listSportResults(request, response)).accept(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = controllerHelper.getAction();

        postActionsMap.getOrDefault(
                action,
                (req, res) -> listSportResults(request, response)).accept(request, response);
    }

    private void listSportResults(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("List Sport Results");
        ArrayList<SportResult> sportResults = sportResultSevice.loadSportResults();
        request.setAttribute("sportResults", sportResults);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/user/user_list.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to user list.", e);
        }
    }
}
