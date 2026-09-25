package application;

import data.local.database.DatabaseConfig;
import data.local.database.DatabaseConnectionFactory;
import data.local.database.DatabaseMigrator;
import data.local.repository.JdbcArticleRepository;
import domain.repository.ArticleRepository;
import domain.usecase.AddArticleUseCase;
import domain.usecase.DeleteArticleUseCase;
import domain.usecase.EditArticleUseCase;
import domain.usecase.FilterArticlesUseCase;
import domain.usecase.GetArticleByIdUseCase;
import domain.usecase.GetArticlesUseCase;
import domain.usecase.SearchArticleUseCase;
import domain.usecase.SortArticlesUseCase;
import presentation.ConsoleView;
import presentation.Presenter;

public class Application {
    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();
        DatabaseMigrator migrator = new DatabaseMigrator(config);
        DatabaseConnectionFactory connectionFactory = new DatabaseConnectionFactory(config);
        ArticleRepository articleRepository = new JdbcArticleRepository(connectionFactory);

        ConsoleView view = new ConsoleView();
        Presenter presenter = new Presenter(
                view,
                new GetArticlesUseCase(),
                new AddArticleUseCase(articleRepository),
                new EditArticleUseCase(articleRepository),
                new GetArticleByIdUseCase(articleRepository),
                new DeleteArticleUseCase(articleRepository),
                new FilterArticlesUseCase(),
                new SortArticlesUseCase(),
                new SearchArticleUseCase()
        );
        view.setPresenter(presenter);

        migrator.migrate();
        view.run();
    }
}
