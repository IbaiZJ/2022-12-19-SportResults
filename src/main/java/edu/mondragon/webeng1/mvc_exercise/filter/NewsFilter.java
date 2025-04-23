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

import edu.mondragon.webeng1.mvc_exercise.domain.news.service.NewsService;
import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;
import edu.mondragon.webeng1.mvc_exercise.helper.ControllerHelper;

@WebFilter("/news/*")
public class NewsFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    ControllerHelper controllerHelper;

    @Inject
    NewsService newsService;


    private Map<String, Function<HttpSession, Integer>> actionsMap = new HashMap<>() {{
        put("create", NewsFilter.this::filterCreation);
        put("delete", NewsFilter.this::filterModification);
        put("edit", NewsFilter.this::filterModification);
        put("view", NewsFilter.this::filterShow);
        put("list", NewsFilter.this::filterShow);
    }};

    public NewsFilter() {}

    public void destroy() {}

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
        logger.debug("Init NewsItem filter.");
    }

    private int filterBadRequest(HttpSession session) {
        logger.error("Unknown News action.");
        session.setAttribute("error", "error.400.unknown_action");
        return HttpServletResponse.SC_BAD_REQUEST;
    }

    private int filterCreation(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // Guard clause
            logger.error("News Item creation needs a user in session.");
            session.setAttribute("error", "error.403.not_session_user");
            return HttpServletResponse.SC_FORBIDDEN;
        }

        logger.debug("News Item creation allowed for any user: " + user.getUserId());
        return HttpServletResponse.SC_OK;
    }

    private int filterModification(HttpSession session) {
        User user = (User) session.getAttribute("user");
        int newsItemId = controllerHelper.getId();

        if (user == null) {
            // Guard clause
            logger.error("News Item modification needs a user in session.");
            session.setAttribute("error", "error.403.not_session_user");
            return HttpServletResponse.SC_FORBIDDEN;
        }

        if (!newsService.checkNewsItemAuthor(newsItemId, user.getUserId())) {
            // Guard clause
            logger.error("Session user is not the author.");
            session.setAttribute("error", "error.403.user_not_author");
            return HttpServletResponse.SC_FORBIDDEN;
        }

        logger.debug("Session user is the author.");
        return HttpServletResponse.SC_OK;
    }

    private int filterShow(HttpSession session) {
        logger.debug("Anyone can read News.");
        return HttpServletResponse.SC_OK;
    }
}
