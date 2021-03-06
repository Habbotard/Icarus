package org.alexdev.icarus.http.mysql.dao;

import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.icarus.http.game.news.NewsArticle;
import org.alexdev.icarus.http.mysql.Storage;
import org.alexdev.icarus.http.util.Util;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDao {

    public static List<NewsArticle> getTop(int limit) {

        List<NewsArticle> articles = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT * FROM `site_articles` ORDER by `id` DESC LIMIT ?", sqlConnection);
            preparedStatement.setInt(1, limit);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                articles.add(fill(resultSet));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return articles;
    }

    public static List<NewsArticle> getArticles() {
        return getTop(25);
    }


    public static void create(String title, String shortstory, String fullstory, String topstory, String author) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("INSERT INTO `site_articles` (article_title, article_author, article_shortstory, article_fullstory, article_date, article_topstory) VALUES (?, ?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, shortstory);
            preparedStatement.setString(4, fullstory);
            preparedStatement.setString(5, Util.getDateAsString());
            preparedStatement.setString(6, topstory);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static boolean exists(int id) {

        boolean exists = false;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT id FROM site_articles WHERE id = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exists = true;
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return exists;
    }

    public static NewsArticle get(int id) {

        NewsArticle article = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT * FROM site_articles WHERE id = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                article = fill(resultSet);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return article;
    }

    public static void delete(int id) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("DELETE FROM site_articles WHERE id = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void save(NewsArticle article) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("UPDATE site_articles SET article_title = ?, article_shortstory = ?, article_fullstory = ?, article_topstory = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getShortStory());
            preparedStatement.setString(3, article.getFullStory());
            preparedStatement.setString(4, article.getTopStory());
            preparedStatement.setInt(5, article.getId());
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static List<String> getTopStoryImages() {

        List<String> images = new ArrayList<String>();

        for (File file : Paths.get(Settings.getInstance().getSiteDirectory(), "c_images", "Top_Story_Images").toFile().listFiles()) {

            if (!file.getName().contains(".png") && !file.getName().contains(".gif")) {
                continue;
            }

            images.add(file.getName());
        }

        return images;
    }

    private static NewsArticle fill(ResultSet resultSet) throws SQLException {
        return new NewsArticle(
                resultSet.getInt("id"), resultSet.getString("article_title"), resultSet.getString("article_author"),
                resultSet.getString("article_shortstory"), resultSet.getString("article_fullstory"), resultSet.getString("article_date"),
                resultSet.getString("article_topstory"), resultSet.getInt("views"));
    }
}
