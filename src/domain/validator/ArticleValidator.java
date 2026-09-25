package domain.validator;

import domain.model.Article;

public class ArticleValidator {
    public void validate(Article article) {
        if (article == null) {
            throw new IllegalArgumentException("Article is required");
        }
        validateAuthorId(article.getAuthorId());
        validateTitle(article.getTitle());
        validateContent(article.getContent());
        if (article.getStatus() == null) {
            throw new IllegalArgumentException("Status is required");
        }
        if (article.getStatus() == Article.Status.PUBLISHED && isBlank(article.getPublishedAt())) {
            throw new IllegalArgumentException("Published article must have a publication date");
        }
    }

    public void validateAuthorId(int authorId) {
        if (authorId <= 0) {
            throw new IllegalArgumentException("Author ID must be a positive number");
        }
    }

    public void validateTitle(String title) {
        if (isBlank(title)) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }

    public void validateContent(String content) {
        if (isBlank(content)) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
