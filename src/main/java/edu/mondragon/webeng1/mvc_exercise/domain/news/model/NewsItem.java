package edu.mondragon.webeng1.mvc_exercise.domain.news.model;

import java.util.Date;
import java.util.Locale;

import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;

/*
 * This is a Java Bean.
 * These are the unique characteristics they must have:
 *     -Default, no-argumented constructor.
 *     -It should be serializable and implement Serializable interface.
 *     -It may have a number of properties which can be read or written.
 *     -"getters" and "setters" for those properties.
 */
public class NewsItem implements java.io.Serializable /* 2nd characteristic */ {
    private static final long serialVersionUID = 3834633934831160740L;

    // 3rd characteristic: multiple properties.
    private Integer newsItemId;
    private String title;
    private String body;
    private User author;
    private Date date;
    private Locale lang;

    public NewsItem() {
    }// 1st characteristic

    // 4th characteristic: 'getters' and 'setters'.
    public Integer getNewsItemId() {
        return newsItemId;
    }

    public void setNewsItemId(Integer newsItemId) {
        this.newsItemId = newsItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Locale getLang() {
        return lang;
    }

    public void setLang(Locale lang) {
        this.lang = lang;
    }
}
