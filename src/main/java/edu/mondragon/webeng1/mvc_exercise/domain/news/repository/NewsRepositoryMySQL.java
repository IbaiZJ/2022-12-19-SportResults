package edu.mondragon.webeng1.mvc_exercise.domain.news.repository;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mondragon.webeng1.mvc_exercise.config.SQLConfig;
import edu.mondragon.webeng1.mvc_exercise.domain.news.model.NewsItem;
import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;
import edu.mondragon.webeng1.mvc_exercise.domain.user.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class NewsRepositoryMySQL implements NewsRepository {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private SQLConfig sqlConfig;

    @Inject
    private UserRepository userRepository;
    
    @Override
    public NewsItem insertNewsItem(NewsItem newsItem) {
        NewsItem retNewsItem = null;

        String sqlInsert = "INSERT INTO news_item (title,body,lang,authorId) VALUES(?,?,?,?)";

        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, newsItem.getTitle());
            stm.setString(2, newsItem.getBody());
            stm.setString(3, newsItem.getLang().getLanguage());
            stm.setInt(4, newsItem.getAuthor().getUserId());
            logger.debug(stm.toString());
            if (stm.executeUpdate() > 0) {
                // Get the ID
                try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        newsItem.setNewsItemId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating News Item failed, no ID obtained.");
                    }
                }
                retNewsItem = newsItem;
            } else {
                throw new SQLException("Creating News Item failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error(sqlInsert, e);
        }
        sqlConfig.disconnect(connection, stm);
        return retNewsItem;
    }

    @Override
    public NewsItem loadNewsItem(int newsItemId) {
        String sqlQuery = "SELECT * FROM news_item WHERE newsItemId=?";
        NewsItem newsItem = null;
        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlQuery);
            stm.setInt(1, newsItemId);
            logger.debug(stm.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int authorId;
                User author;
                newsItem = new NewsItem();

                newsItem.setNewsItemId(rs.getInt("newsItemId"));
                newsItem.setTitle(rs.getString("title"));
                newsItem.setBody(rs.getString("body"));

                Timestamp ts = rs.getTimestamp("date");
                Date date = new Date(ts.getTime());
                newsItem.setDate(date);

                String langStr = rs.getString("lang");
                Locale lang = Locale.forLanguageTag(langStr);
                newsItem.setLang(lang);

                authorId = rs.getInt("authorId");
                author = userRepository.loadUser(authorId);
                newsItem.setAuthor(author);
            }
        } catch (SQLException e) {
            logger.error("Error loading news item "+newsItemId, e);
        }
        sqlConfig.disconnect(connection, stm);
        return newsItem;
    }

    @Override
    public ArrayList<NewsItem> loadNews(Locale locale) {
        ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();
        Connection connection = sqlConfig.connect();
        String sqlQuery = "SELECT * FROM news_item WHERE lang=?";
        String languageTag = locale.getLanguage();
        logger.debug("Display Language:" + locale.getDisplayLanguage());
        logger.debug("Language:" + locale.getLanguage());
        logger.debug("LanguageTag:" + locale.toLanguageTag());

        ResultSet rs = null;
        PreparedStatement stm = null;
        NewsItem newsItem = null;
        try {
            stm = connection.prepareStatement(sqlQuery);
            stm.setString(1, languageTag);
            rs = stm.executeQuery();
            while (rs.next()) {

                newsItem = new NewsItem();

                newsItem.setNewsItemId(rs.getInt("newsItemId"));
                newsItem.setTitle(rs.getString("title"));
                newsItem.setBody(rs.getString("body"));

                Timestamp ts = rs.getTimestamp("date");
                Date date = new Date(ts.getTime());
                newsItem.setDate(date);

                String langStr = rs.getString("lang");
                Locale lang = Locale.forLanguageTag(langStr);
                newsItem.setLang(lang);

                int authorId = rs.getInt("authorId");
                User author = userRepository.loadUser(authorId);
                newsItem.setAuthor(author);

                newsItems.add(newsItem);
            }
        } catch (SQLException e) {
            logger.error("Error loading news items "+locale.toLanguageTag(), e);
        }
        sqlConfig.disconnect(connection, stm);
        return newsItems;
    }

    @Override
    public NewsItem updateNewsItem(NewsItem newsItem) {
        NewsItem retNewsItem = null;
        String sqlUpdate = "UPDATE news_item SET title=?, body=?, lang=? WHERE newsItemId=?";

        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlUpdate);
            stm.setString(1, newsItem.getTitle());
            stm.setString(2, newsItem.getBody());
            stm.setString(3, newsItem.getLang().toLanguageTag());
            stm.setInt(4, newsItem.getNewsItemId());

            if (stm.executeUpdate() < 1) {
                newsItem.setNewsItemId(0);
            }
            retNewsItem = newsItem;
        } catch (SQLException e) {
            logger.error("Error updating news item "+newsItem.getNewsItemId(), e);
        }
        sqlConfig.disconnect(connection, stm);
        return retNewsItem;
    }

    @Override
    public boolean deleteNewsItem(Integer newsItemId) {

        boolean ret = false;
        String sqlDelete = "DELETE FROM news_item WHERE newsItemId=?";
        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlDelete);
            stm.setInt(1, newsItemId);

            if (stm.executeUpdate() > 0) {
                ret = true;
            } else {
                throw new SQLException("Deleting News Item failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Error deleting news item "+newsItemId, e);

        }
        sqlConfig.disconnect(connection, stm);
        return ret;
    }
}
