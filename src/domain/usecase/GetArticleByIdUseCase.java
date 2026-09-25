package domain.usecase;

import domain.model.Article;
import domain.repository.ArticleRepository;

public class GetArticleByIdUseCase {
    private final ArticleRepository articleRepository;

    public GetArticleByIdUseCase(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article execute(int articleId) {
        return articleRepository.getArticleById(articleId);
    }
}
