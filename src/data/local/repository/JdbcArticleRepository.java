package data.local.repository;

import data.local.database.DatabaseConnectionFactory;
import domain.model.Article;
import domain.repository.ArticleRepository;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;

public class JdbcArticleRepository implements ArticleRepository {
    private final DatabaseConnectionFactory connectionFactory;

    public JdbcArticleRepository(DatabaseConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void addArticle(Article article) {
    }

    @Override
    public void deleteArticle(int articleId) {

    }

    @Override
    public void editArticle(Article article) {

    }

    @Override
    public void filterArticles() {

    }

    @Override
    public Article getArticleById(int articleId) {
        return null;
    }

    @Override
    public List<Article> getArticles() {
        return List.of();
    }

    @Override
    public List<Article> searchArticle(String keyword) {
        return List.of();
    }

    @Override
    public List<Article> sortArticles() {
        return List.of();
    }
}
