package edu.mondragon.webeng1.mvc_exercise.domain.news.service;

import java.util.ArrayList;
import java.util.Locale;

import edu.mondragon.webeng1.mvc_exercise.domain.news.model.NewsItem;
import edu.mondragon.webeng1.mvc_exercise.domain.news.repository.NewsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class NewsService {
	@Inject
	private NewsRepository repository;

	public NewsItem createNewsitem(NewsItem newsItem) {
        return repository.insertNewsItem(newsItem);
    }

    public NewsItem loadNewsItem(int newsItemId) {
        return repository.loadNewsItem(newsItemId);
    }

    public ArrayList<NewsItem> loadAllNewsItems(Locale locale) {
        return repository.loadNews(locale);
    }

    public boolean checkNewsItemAuthor(int newsItemId, int userId) {
        NewsItem newsItem = repository.loadNewsItem(newsItemId);
        if (newsItem == null) {
            // Guard clause
            return false;
        }

        return newsItem.getAuthor().getUserId() == userId;
    }

    public NewsItem saveNewsItem(NewsItem newsItem) {
        NewsItem retNewsItem;
        if (newsItem.getNewsItemId() == null || newsItem.getNewsItemId() == 0) {
            retNewsItem = repository.insertNewsItem(newsItem);
        } else {
            retNewsItem = repository.updateNewsItem(newsItem);
        }
        return retNewsItem;
    }

    public boolean deleteNewsItem(Integer newsItemId) {
        return repository.deleteNewsItem(newsItemId);
    }

}
