package domain.usecase;

import domain.model.Article;
import domain.repository.ArticleRepository;

public class AddArticleUseCase {
    private final ArticleRepository repository;

    public AddArticleUseCase(ArticleRepository repository) {
        this.repository = repository;
    }

    public void execute(Article article) {
        repository.addArticle(article);
    }
}