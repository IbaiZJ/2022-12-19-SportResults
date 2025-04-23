package edu.mondragon.webeng1.mvc_exercise.helper;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * This class will split the URL and extract the element ID and the action
 * String. It can be reused in multiple controllers.
 * 
 * @author aperez
 *
 */
@RequestScoped
public class ControllerHelper {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private HttpServletRequest request;

    private String[] pathElements;
    private String action = "list";
    private int id = -1;

    public ControllerHelper() {
        // Constructor
    }

    @PostConstruct
    public void init() {
        // Constructor
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null && !pathInfo.contentEquals("/")) {
            logger.debug("Path info:" + pathInfo);
            pathElements = pathInfo.replaceFirst("/", "").split("/");
            getIdFromPathElements();
            getActionFromPathElements();
        }
        /*
        * else { // e.g. http://localhost:8080/FriendlyURL/friendly or
        * http://localhost:8080/FriendlyURL/friendly/ //For empty path, default values
        * => list & -1 }
        */
    }


    private void getIdFromPathElements() {
        try {
            id = Integer.parseInt(pathElements[0]);
        } catch (NumberFormatException e) {
            /*
             * If pathElements[0] is not an integer, request does not have an ID.
             * default value will be used (id = -1)
             */
        }
    }

    private void getActionFromPathElements() {
        if (pathElements.length > 1) {
            // Both id & action have been sent
            action = pathElements[1];
            return;
        }
        
        // Id or action has not been sent.
        if (id == -1) {
            /*
             * If id==-1, no id has been sent => first element must be an action.
             * e.g. http://localhost:8080/FriendlyURL/friendly/create
             */
            action = pathElements[0];
            return;
        }

        /* 
         * If the id is an integer and no action is sent, view the element.
         * e.g. http://localhost:8080/FriendlyURL/friendly/1
         */
        action = "view";
    }

    public String getAction() {
        return action;
    }

    public int getId() {
        return id;
    }
}
