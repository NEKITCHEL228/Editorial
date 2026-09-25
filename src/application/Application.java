package application;

import data.local.database.DatabaseConfig;
import data.local.database.DatabaseConnectionFactory;
import data.local.database.DatabaseMigrator;
import data.local.repository.JdbcArticleRepository;
import data.local.repository.JdbcUserRepository;
import domain.repository.ArticleRepository;
import domain.repository.UserRepository;
import domain.usecase.AddArticleUseCase;
import domain.usecase.AddUserUseCase;
import domain.usecase.DeleteArticleUseCase;
import domain.usecase.DeleteUserUseCase;
import domain.usecase.EditArticleUseCase;
import domain.usecase.EditUserUseCase;
import domain.usecase.FilterArticlesUseCase;
import domain.usecase.GetArticleByIdUseCase;
import domain.usecase.GetArticlesUseCase;
import domain.usecase.GetUserByIdUseCase;
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
        UserRepository userRepository = new JdbcUserRepository(connectionFactory);

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
                new SearchArticleUseCase(),
                new AddUserUseCase(userRepository),
                new EditUserUseCase(userRepository),
                new DeleteUserUseCase(userRepository),
                new GetUserByIdUseCase(userRepository)
        );
        view.setPresenter(presenter);

        migrator.migrate();
        view.run();
    }
}
