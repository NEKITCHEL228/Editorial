package domain.usecase;

import domain.model.Article;
import domain.repository.ArticleRepository;

public class DeleteArticleUseCase {
    private final ArticleRepository articleRepository;

    public DeleteArticleUseCase(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void execute(int articleId) {
        articleRepository.deleteArticle(articleId);
    }
}
