package domain.usecase;

import domain.model.Article;
import domain.repository.ArticleRepository;

public class AddArticleUseCase {
    private final ArticleRepository repository;

    public AddArticleUseCase(ArticleRepository repository) {
        this.repository = repository;
    }

    public void execute(Article article) {
        if (article == null) throw new IllegalArgumentException("No such article");
        if (article.getStatus() != Article.Status.PENDING) throw new IllegalStateException("New article must have a PENDING status");
        repository.addArticle(article);
    }
}