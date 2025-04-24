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
import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;
import edu.mondragon.webeng1.mvc_exercise.helper.ControllerHelper;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "SportResultController", urlPatterns = { "/sportResult/*" })
public class SportResultController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private ControllerHelper controllerHelper;

    @Inject
    private SportResultSevice sportResultSevice;

    private Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> getActionsMap = new HashMap<>() {
        {
            put("delete", SportResultController.this::deleteSportResult);
            put("create", SportResultController.this::showSportResultForm);
            put("edit", SportResultController.this::showSportResultForm);
            // put("view", SportResultController.this::showUser);
            put("list", SportResultController.this::listSportResults);
        }
    };
    private Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> postActionsMap = new HashMap<>() {
        {
            put("create", SportResultController.this::createSportResult);
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

        // RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/user/user_list.jsp");
        // try {
        //     dispatcher.forward(request, response);
        // } catch (ServletException | IOException e) {
        //     logger.error("Error forwarding to user list.", e);
        // }
    }

    private void showSportResultForm(HttpServletRequest request, HttpServletResponse response) {
        int sportResultId = controllerHelper.getId();
        logger.debug("Show sportResul Form: " + sportResultId);

        if (sportResultId > 0) {
            // Edit sportResult
            SportResult sportResult = sportResultSevice.loadSportResult(sportResultId);
            if (sportResult == null) {
                // Guard Clause
                logger.error("Sport Result " + sportResultId + " cannot be found.");
                ControllerErrorHelper.respondNotFound(request, response, this.getServletContext());
                return;
            }
            request.setAttribute("sportResult", sportResult);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/sport_results/sport_results_form.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to user form.", e);
        }
    }

    private void createSportResult(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Creating Sport Result");
        
        HttpSession session = request.getSession(true);
        SportResult sportResult = new SportResult();
        sportResult.setTeam1Name(request.getParameter("team1Name"));
        sportResult.setTeam1Result(Integer.valueOf(request.getParameter("team1Result")));
        sportResult.setTeam2Name(request.getParameter("team2Name"));
        sportResult.setTeam2Result(Integer.valueOf(request.getParameter("team2Result")));

        sportResultSevice.saveSportResult(sportResult);


        User sessionUser = (User) session.getAttribute("user");
        String redirectUrl = "/";
        // if (sportResult.getSportResultId() != 0) {
        //     request.setAttribute("user", user);
        //     session.setAttribute("message", "message.createUser");
        //     if (sessionUser != null) // If a user creates another user, redirect to the view
        //         redirectUrl = "/user/" + user.getUserId();
        //     // else, redirect to the index so they can login.
        // } else {
        //     session.setAttribute("error", "error.createUser");
        //     if (sessionUser != null)
        //         redirectUrl = "/user"; // If a user tried to creates another user, redirect to the list view.
        //     // else, redirect to the index so they can login.
        // }

        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            logger.error("Error redirecting to " + redirectUrl, e);
        }
    }

    private void deleteSportResult(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        int sportResultId = controllerHelper.getId();

        if (sportResultId != -1 && sportResultSevice.deleteSportResult(sportResultId)) {
            // session.setAttribute("message", "message.deleteUser");
            // session.removeAttribute("user");
        } else {
            // session.setAttribute("error", "error.deleteUser");
        }

        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            logger.error("Error redirecting to root view.", e);
        }
    }
}
