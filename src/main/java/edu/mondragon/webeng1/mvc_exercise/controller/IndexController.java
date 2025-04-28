package edu.mondragon.webeng1.mvc_exercise.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mondragon.webeng1.mvc_exercise.domain.sportResult.service.SportResultSevice;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * This class will show the main page (as home.jsp is not directly accessible).
 * Be careful, becouse an index.jsp or index.html file will make this controller
 * unaccessible.
 * 
 * @author aperez
 *
 */
@WebServlet(name = "IndexController", urlPatterns = { "/index.html" })
public class IndexController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private SportResultSevice sportResultSevice;

    public IndexController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        logger.debug("Index Controller");

        HttpSession session = request.getSession();
        if(session.getAttribute("sportResults") == null) {
            session.setAttribute("sportResults", sportResultSevice.loadSportResults());
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/home.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
