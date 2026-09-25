package domain.validator;

import domain.model.Article;

public class ArticleValidator {
    public void validate(Article article) {
        if (article == null) {
            throw new IllegalArgumentException("Article is required");
        }
        if (article.getAuthorId() <= 0) {
            throw new IllegalArgumentException("Author ID must be a positive number");
        }
        if (isBlank(article.getTitle())) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (isBlank(article.getContent())) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
        if (article.getStatus() == null) {
            throw new IllegalArgumentException("Status is required");
        }
        if (article.getStatus() == Article.Status.PUBLISHED && isBlank(article.getPublishedAt())) {
            throw new IllegalArgumentException("Published article must have a publication date");
        }
    }

    public void validateNew(Article article) {
        validate(article);
        if (article.getStatus() != Article.Status.PENDING) {
            throw new IllegalStateException("New article must have a PENDING status");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
