package edu.mondragon.webeng1.mvc_exercise.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.jstl.core.*;

@WebServlet(name = "LocaleController", urlPatterns = { "/lang" })
public class LocaleController extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final long serialVersionUID = 1L;

	public LocaleController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Locale newLocale;
		String location = Optional.ofNullable(request.getHeader("referer")).orElse("/");
		String language = Optional.ofNullable(request.getParameter("language")).orElse("");
		String country = Optional.ofNullable(request.getParameter("country")).orElse("");
		logger.info("Language: " + language + " Country: " + country);

		if(language.isBlank()) {
			newLocale = request.getLocale();  // get locale from browser
		}else if(country.isBlank()) {
			newLocale = Locale.forLanguageTag(language);  // get locale just from language
		} else {
			newLocale = Locale.forLanguageTag(language + "-" + country);  // get locale from language and country
		}

		Config.set(session, jakarta.servlet.jsp.jstl.core.Config.FMT_LOCALE, newLocale);
		
		response.sendRedirect(location);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}