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

    public void onAddArticleClicked() {}

    public void onGetArticles() {}

    public void onDeleteArticle() {}

    public void onGetArticleById() {}

    public void onFilterArticles() {}

    public void onSortArticles() {}

    public void onSearchArticle() {}

    public void onEditArticle() {}

}