package edu.mondragon.webeng1.mvc_exercise.domain.news.repository;
import java.util.ArrayList;
import java.util.Locale;

import edu.mondragon.webeng1.mvc_exercise.domain.news.model.NewsItem;

public interface NewsRepository {
	public NewsItem insertNewsItem(NewsItem newsItem);
	public NewsItem loadNewsItem(int newsId);
	public ArrayList<NewsItem> loadNews(Locale locale);
	public NewsItem updateNewsItem(NewsItem newsItem);
	public boolean deleteNewsItem(Integer newsItemId);
}
