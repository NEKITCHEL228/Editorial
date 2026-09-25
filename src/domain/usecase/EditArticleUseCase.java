package domain.usecase;

import domain.model.Article;
import domain.repository.ArticleRepository;

public class EditArticleUseCase {
    private final ArticleRepository articleRepository;

    public EditArticleUseCase(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void execute(int articleId, String title, String content, Article.Status status) {
        Article article = articleRepository.getArticleById(articleId);

        article.setTitle(title);
        article.setContent(content);
        article.setStatus(status);

        articleRepository.editArticle(article);
    }
}
