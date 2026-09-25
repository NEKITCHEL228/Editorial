package presentation;

import domain.model.Article;
import domain.usecase.*;

public class Presenter {
    private final View view;
    private final GetArticlesUseCase getArticlesUseCase;
    private final AddArticleUseCase addArticleUseCase;
    private final GetArticleByIdUseCase getArticleByIdUseCase;
    private final DeleteArticleUseCase deleteArticleUseCase;
    private final FilterArticlesUseCase filterArticlesUseCase;
    private final EditArticleUseCase editArticleUseCase;
    private final SortArticlesUseCase sortArticlesUseCase;
    private final SearchArticleUseCase searchArticleUseCase;


    public Presenter(
            View view,
            GetArticlesUseCase getArticlesUseCase,
            AddArticleUseCase addArticleUseCase,
            EditArticleUseCase editArticleUseCase,
            GetArticleByIdUseCase getArticleByIdUseCase,
            DeleteArticleUseCase deleteArticleUseCase,
            FilterArticlesUseCase filterArticlesUseCase,
            SortArticlesUseCase sortArticlesUseCase,
            SearchArticleUseCase searchArticleUseCase

    ) {
        this.view = view;
        this.getArticlesUseCase = getArticlesUseCase;
        this.addArticleUseCase = addArticleUseCase;
        this.deleteArticleUseCase = deleteArticleUseCase;
        this.getArticleByIdUseCase = getArticleByIdUseCase;
        this.filterArticlesUseCase = filterArticlesUseCase;
        this.sortArticlesUseCase = sortArticlesUseCase;
        this.searchArticleUseCase = searchArticleUseCase;
        this.editArticleUseCase = editArticleUseCase;
    }

    public void onAddArticle(Article article) {
        try {
            addArticleUseCase.execute(article);
            view.showMessage("Article added");
        } catch (IllegalArgumentException | IllegalStateException e) {
            view.showError(e.getMessage());
        }
    }

    public void onGetArticles() {}

    public void onDeleteArticle(int articleId) {
        try {
            deleteArticleUseCase.execute(articleId);
            view.showMessage("Article deleted");
        } catch (IllegalStateException | IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

    public Article onGetArticleById(int articleId) {
        Article returnArticle = null;
        try {
            returnArticle = getArticleByIdUseCase.execute(articleId);
        } catch (IllegalStateException | IllegalArgumentException e) {
            view.showError(e.getMessage());
        }

        return returnArticle;
    }

    public void onFilterArticles() {}

    public void onSortArticles() {}

    public void onSearchArticle() {}

    public void onEditArticle(int articleId, String title, String content, Article.Status status) {
        try {
            editArticleUseCase.execute(articleId, title, content, status);
        } catch (IllegalStateException | IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

}