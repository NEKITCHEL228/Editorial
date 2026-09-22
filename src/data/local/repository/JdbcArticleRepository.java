package data.local.repository;

import domain.model.Article;
import domain.repository.ArticleRepository;

import java.util.List;

public class JdbcArticleRepository implements ArticleRepository {
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
