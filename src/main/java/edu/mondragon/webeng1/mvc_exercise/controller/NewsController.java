package edu.mondragon.webeng1.mvc_exercise.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
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
import jakarta.servlet.jsp.jstl.core.Config;

import edu.mondragon.webeng1.mvc_exercise.domain.news.model.NewsItem;
import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;
import edu.mondragon.webeng1.mvc_exercise.domain.news.service.NewsService;
import edu.mondragon.webeng1.mvc_exercise.helper.ControllerHelper;

@WebServlet(name = "NewsController", urlPatterns = { "/news/*" })
public class NewsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private NewsService newsService;

    @Inject
    private ControllerHelper controllerHelper;

    public NewsController() {
        super();
    }
    // Maps actions name with actions methods
    private Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> getActionsMap = new HashMap<>() {{
        put("delete", NewsController.this::deleteNewsItem);
        put("create", NewsController.this::showNewsItemForm);
        put("edit", NewsController.this::showNewsItemForm);
        put("view", NewsController.this::showNewsItem);
        put("list", NewsController.this::listNews);
    }};
    private Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> postActionsMap = new HashMap<>() {{
        put("create", NewsController.this::createNewsItem);
        put("edit", NewsController.this::editNewsItem);
    }};

    /**
     * Executed if GET request.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        logger.debug("Trying hot code reload");
        String action = controllerHelper.getAction();

        getActionsMap.getOrDefault(
            action,
            (req, res) -> listNews(request, response)
        ).accept(request, response);
    }

    /**
     * Executed if POST request.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = controllerHelper.getAction();

        postActionsMap.getOrDefault(
            action,
            (req, res) -> listNews(request, response)
        ).accept(request, response);

    }

    /**
     * Default value if something is not correct or user wants to list NewsItems.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void listNews(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Locale locale = this.getLocale(request, session);

        ArrayList<NewsItem> news = newsService.loadAllNewsItems(locale);
        request.setAttribute("news", news);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/news_item/news_item_list.jsp");

        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("Error dispatching news list view.", e);
        }
    }

    /**
     * Load NewsItem and dispatch single NewsItem view.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void showNewsItem(HttpServletRequest request, HttpServletResponse response) {
        NewsItem newsItem = newsService.loadNewsItem(controllerHelper.getId());
        request.setAttribute("newsItem", newsItem);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/news_item/news_item.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("Error dispatching news item view.", e);
        }
    }


    /**
     * Dispatch NewsItem form.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void showNewsItemForm(HttpServletRequest request, HttpServletResponse response) {
        int newsItemId = controllerHelper.getId();
        logger.debug("Show News Item Form: " + newsItemId);

        if(newsItemId > 0) {
            NewsItem newsItem = newsService.loadNewsItem(newsItemId);
            request.setAttribute("newsItem", newsItem);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/news_item/news_item_form.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("Error dispatching news item form view.", e);
        }
    }


    /**
     * Delete NewsItem from Database and dispatch NewsItem list.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void deleteNewsItem(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        int id = controllerHelper.getId();
        if (id != -1 && newsService.deleteNewsItem(id)) {
            session.setAttribute("message", "message.deleteNewsItem");
        } else {
            session.setAttribute("error", "error.deleteNewsItem");
        }

        try {
            response.sendRedirect(request.getContextPath() + "/news/list");
        } catch (IOException e) {
            logger.error("Error redirecting to news list.", e);
        }
    }


    /**
     * Update NewsItem and redirect to its view.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void editNewsItem(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        int id = controllerHelper.getId();
        NewsItem newsItem = newsService.loadNewsItem(id);
        String retRedirectUrl;
        logger.debug("Edit News Item: " + id);


        newsItem.setTitle(request.getParameter("title"));
        newsItem.setBody(request.getParameter("body"));
        newsService.saveNewsItem(newsItem);


        if (newsItem.getNewsItemId() <= 0) {
            // Guard clause
            session.setAttribute("error", "error.editNewsItem");
            retRedirectUrl = request.getContextPath() + "/news/" + id + "/edit";
        } else {
            session.setAttribute("message", "message.editNewsItem");
            session.setAttribute("newsItem", newsItem);
            retRedirectUrl = request.getContextPath() + "/news/" + id + "/view";
        }

        try {
            response.sendRedirect(retRedirectUrl);
        } catch (IOException e) {
            logger.error("Error redirecting after NewsItem edition.", e);
        }
    }


    /**
     * Create NewsItem and redirect to its view.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void createNewsItem(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        NewsItem newsItem = new NewsItem();
        newsItem.setAuthor(user);
        newsItem.setTitle(request.getParameter("title"));
        newsItem.setBody(request.getParameter("body"));
        newsItem.setLang(getLocale(request, session));

        newsService.saveNewsItem(newsItem);

        String redirectUrl = "";
        if (newsItem.getNewsItemId() > 0) {
            // If news item has been created, redirect to its view.
            request.setAttribute("newsItem", newsItem);
            session.setAttribute("message", "message.createNewsItem");
            redirectUrl = "/news/" + newsItem.getNewsItemId() + "/view";
        } else {
            // If news item could not be created, redirect to list news items.
            session.setAttribute("error", "error.createNewsItem");
            redirectUrl = "/news/list";
        }
        try {
            response.sendRedirect(request.getContextPath() + redirectUrl);
        } catch (IOException e) {
            logger.error("Error redirecting after news item creation.", e);
        }
    }


    /**
     * Get locale from FMT or Browser (request).
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private Locale getLocale(HttpServletRequest request, HttpSession session) {
        Locale defaultLocale = Locale.forLanguageTag("en-UK"); // Default locale.
        Locale fmtLocale = (Locale) Config.get(session, Config.FMT_LOCALE); // Locale from FMT library
        Locale browserLocale = request.getLocale(); // Browser locale

        if (fmtLocale == null && browserLocale == null)
            return defaultLocale;
        
        if (fmtLocale == null)
            return browserLocale;

        return fmtLocale;
    }
}