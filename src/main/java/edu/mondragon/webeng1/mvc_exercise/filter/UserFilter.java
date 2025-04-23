package edu.mondragon.webeng1.mvc_exercise.filter;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;
import edu.mondragon.webeng1.mvc_exercise.helper.ControllerHelper;

/**
 * Servlet Filter implementation class UserFilter
 */
@WebFilter("/user/*")
public class UserFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    ControllerHelper controllerHelper;

    public UserFilter() {
    }

    public void destroy() {
    }

    private Map<String, Function<HttpSession, Integer>> actionsMap = new HashMap<>() {{
        put("create", UserFilter.this::filterCreation);
        put("delete", UserFilter.this::filterModification);
        put("edit", UserFilter.this::filterModification);
        put("view", UserFilter.this::filterShow);
        put("list", UserFilter.this::filterShow);
    }};


    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        logger.debug("User Filter");
        HttpServletRequest request = (HttpServletRequest) req;
        request.setCharacterEncoding("UTF-8");
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(true);

        String action = controllerHelper.getAction();

        int code;

        code = actionsMap.getOrDefault(
            action,
            this::filterBadRequest
        ).apply(session);

        if (code == HttpServletResponse.SC_OK) {
            chain.doFilter(req, res);
        } else {
            session.setAttribute("errorCode", code);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/error.jsp");
            response.setStatus(code);
            dispatcher.forward(request, response);
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

    private int filterBadRequest(HttpSession session) {
        logger.error("Unknown User action.");
        session.setAttribute("error", "error.400.unknown_action");
        return HttpServletResponse.SC_BAD_REQUEST;
    }

    private int filterCreation(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null){
            logger.debug("User " + user.getUsername() + " is creating another user.");
        } else {
            logger.debug("Anonymous user is trying to create a user.");
        }
        logger.debug("User creation allowed for anybody.");
        return HttpServletResponse.SC_OK;
    }

    private int filterModification(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null || user.getUserId() != controllerHelper.getId()){
            // Guard clause
            logger.error("User trying to modify other user.");
            session.setAttribute("error", "error.403.not_own_user");
            return HttpServletResponse.SC_FORBIDDEN;
        }

        logger.debug("User modifies it's own user.");
        return HttpServletResponse.SC_OK;
    }

    private int filterShow(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null){
            // Guard clause
            logger.error("View/List actions need the user to be in session.");
            session.setAttribute("error", "error.403.not_session_user");
            return HttpServletResponse.SC_FORBIDDEN;
        }
        
        logger.debug("User in session. They can view/list user(s).");
        return HttpServletResponse.SC_OK;
    }
}
