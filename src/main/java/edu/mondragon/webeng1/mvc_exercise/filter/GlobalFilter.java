package edu.mondragon.webeng1.mvc_exercise.filter;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mondragon.webeng1.mvc_exercise.domain.sportResult.service.SportResultSevice;
import edu.mondragon.webeng1.mvc_exercise.helper.ControllerHelper;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class GlobalFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    ControllerHelper controllerHelper;

    @Inject
    private SportResultSevice sportResultSevice;

    public GlobalFilter() {

    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        logger.debug("Global Filter");
        HttpServletRequest request = (HttpServletRequest) req;
        request.setCharacterEncoding("UTF-8");

        ServletContext context = request.getSession().getServletContext();
        if (context.getAttribute("sportResults") == null) {
            context.setAttribute("sportResults", sportResultSevice.loadSportResults());
        }

        chain.doFilter(req, res);
    }
}
