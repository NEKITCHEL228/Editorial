package presentation;

import data.local.database.DatabaseConfig;
import data.local.database.DatabaseConnectionFactory;
import data.local.database.DatabaseMigrator;
import data.local.repository.JdbcArticleRepository;
import domain.model.Article;
import domain.repository.ArticleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleView implements View {
    private static final int SHOW_ARTICLES_COMMAND = 1;
    private static final int ADD_ARTICLE_COMMAND = 2;
    private static final int EDIT_ARTICLE_COMMAND = 3;
    private static final int DELETE_ARTICLE_COMMAND = 4;
    private static final int GET_ARTICLE_BY_ID_COMMAND = 5;
    private static final int FILTER_ARTICLES_COMMAND = 6;
    private static final int SORT_ARTICLES_COMMAND = 7;
    private static final int SEARCH_ARTICLES_COMMAND = 8;
    private static final int EXIT_COMMAND = 0;
    private static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter SHORT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private final ArticleRepository articleRepository;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleView() {
        this(new JdbcArticleRepository(new DatabaseConnectionFactory(new DatabaseConfig())));
    }

    public ConsoleView(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();
        DatabaseMigrator migrator = new DatabaseMigrator(config);

        migrator.migrate();
        ArticleRepository articleRepository = new JdbcArticleRepository(new DatabaseConnectionFactory(config));
        new ConsoleView(articleRepository).run();
    }

    public void run() {
        boolean isRunning = true;

        while (isRunning) {
            showStartOptions();
            int command = getMenuChoice();

            switch (command) {
                case SHOW_ARTICLES_COMMAND -> showArticlesFromRepository();
                case ADD_ARTICLE_COMMAND -> addArticle();
                case EDIT_ARTICLE_COMMAND -> editArticle();
                case DELETE_ARTICLE_COMMAND -> deleteArticle();
                case GET_ARTICLE_BY_ID_COMMAND -> getArticleById();
                case FILTER_ARTICLES_COMMAND -> filterArticles();
                case SORT_ARTICLES_COMMAND -> sortArticles();
                case SEARCH_ARTICLES_COMMAND -> searchArticles();
                case EXIT_COMMAND -> {
                    showMessage("Exiting the application.");
                    isRunning = false;
                }
                default -> showError("Unknown command. Please select a command from the menu.");
            }
        }
    }

    @Override
    public void showStartOptions() {
        System.out.println();
        System.out.println("=== Editorial Console ===");
        System.out.println("1. Show all articles");
        System.out.println("2. Add article");
        System.out.println("3. Edit article");
        System.out.println("4. Delete article");
        System.out.println("5. Get article by ID");
        System.out.println("6. Filter articles");
        System.out.println("7. Sort articles");
        System.out.println("8. Search articles");
        System.out.println("0. Exit");
        System.out.println();
    }

    @Override
    public void showArticles(List<Article> articles) {
        if (articles == null || articles.isEmpty()) {
            showMessage("No articles found.");
            return;
        }

        System.out.println("Articles:");
        for (Article article : articles) {
            System.out.printf(
                    "%d. %s (author ID: %d, status: %s, published at: %s)%n",
                    article.getId(),
                    article.getTitle(),
                    article.getAuthorId(),
                    article.getStatus(),
                    article.getPublishedAt()
            );
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showError(String error) {
        System.err.println("Error: " + error);
    }

    @Override
    public String getUserInput(String prompt) {
        System.out.print(prompt);
        System.out.flush();

        if (!scanner.hasNextLine()) {
            return null;
        }

        return scanner.nextLine();
    }

    @Override
    public int getMenuChoice() {
        String input = getUserInput("Enter command: ");
        if (input == null) {
            return EXIT_COMMAND;
        }

        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            showError("Command must be a number.");
            return -1;
        }
    }

    private void addArticle() {
        Integer authorId = readPositiveInt("Enter author ID: ");
        if (authorId == null) {
            return;
        }

        String title = readRequiredInput("Enter title: ");
        String content = readRequiredInput("Enter content: ");
        Article.Status status = readStatus("Enter status (PENDING, MODERATING, REJECTED, PUBLISHED): ");
        String publishedAt = readPublishedAt();

        if (title == null || content == null || status == null) {
            return;
        }

        Article article = new Article(0, authorId, title, content, status, publishedAt);
        try {
            articleRepository.addArticle(article);
            showMessage("Article added successfully. ID: " + article.getId());
        } catch (RuntimeException e) {
            showError(getErrorMessage(e));
        }
    }

    private void editArticle() {
        Integer articleId = readPositiveInt("Enter article ID: ");
        if (articleId == null) {
            return;
        }

        String title = readRequiredInput("Enter new title: ");
        String content = readRequiredInput("Enter new content: ");
        Article.Status status = readStatus("Enter new status (PENDING, MODERATING, REJECTED, PUBLISHED): ");

        if (title == null || content == null || status == null) {
            return;
        }

        Article article = new Article(articleId, articleId, title, content, status, null);
        try {
            articleRepository.editArticle(article);
            showMessage("Article updated successfully.");
        } catch (RuntimeException e) {
            showError(getErrorMessage(e));
        }
    }

    private void deleteArticle() {
        Integer articleId = readPositiveInt("Enter article ID: ");
        if (articleId == null) {
            return;
        }

        try {
            articleRepository.deleteArticle(articleId);
            showMessage("Article deleted successfully.");
        } catch (RuntimeException e) {
            showError(getErrorMessage(e));
        }
    }

    private void getArticleById() {
        Integer articleId = readPositiveInt("Enter article ID: ");
        if (articleId == null) {
            return;
        }

        try {
            Article article = articleRepository.getArticleById(articleId);
            showArticle(article);
        } catch (RuntimeException e) {
            showError(getErrorMessage(e));
        }
    }

    private void showArticlesFromRepository() {
        showMessage("Showing all articles is not implemented in the repository yet.");
    }

    private void filterArticles() {
        showMessage("Filter articles is not implemented in the repository yet.");
    }

    private void sortArticles() {
        showMessage("Sort articles is not implemented in the repository yet.");
    }

    private void searchArticles() {
        showMessage("Search articles is not implemented in the repository yet.");
    }

    private void showArticle(Article article) {
        System.out.println("Article:");
        System.out.printf("ID: %d%n", article.getId());
        System.out.printf("Author ID: %d%n", article.getAuthorId());
        System.out.printf("Title: %s%n", article.getTitle());
        System.out.printf("Content: %s%n", article.getContent());
        System.out.printf("Status: %s%n", article.getStatus());
        System.out.printf("Published at: %s%n", article.getPublishedAt());
    }

    private Integer readPositiveInt(String prompt) {
        while (true) {
            Integer value = readInt(prompt);
            if (value == null) {
                return null;
            }
            if (value > 0) {
                return value;
            }
            showError("ID must be a positive number.");
        }
    }

    private Integer readInt(String prompt) {
        while (true) {
            String input = getUserInput(prompt);
            if (input == null) {
                return null;
            }

            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                showError("Please enter a valid integer.");
            }
        }
    }

    private String readRequiredInput(String prompt) {
        while (true) {
            String input = getUserInput(prompt);
            if (input == null) {
                return null;
            }

            String value = input.trim();
            if (!value.isBlank()) {
                return value;
            }
            showError("This field cannot be empty.");
        }
    }

    private String readPublishedAt() {
        while (true) {
            String input = getUserInput("Enter published at (DD.MM.YYYY or ISO date/time): ");
            if (input == null) {
                return null;
            }

            String value = input.trim();
            if (value.isBlank()) {
                return null;
            }

            String normalized = normalizePublishedAt(value);
            if (normalized != null) {
                return normalized;
            }

            showError("Invalid date format. Use DD.MM.YYYY or ISO date/time.");
        }
    }

    private String normalizePublishedAt(String value) {
        try {
            return LocalDate.parse(value, SHORT_DATE_FORMAT).format(DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ignored) {
            // Try the remaining supported formats.
        }

        try {
            return LocalDateTime.parse(value, SHORT_DATE_TIME_FORMAT)
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException ignored) {
            // Try the remaining supported formats.
        }

        try {
            return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)
                    .format(DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ignored) {
            // Try the remaining supported format.
        }

        try {
            return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException ignored) {
            return null;
        }
    }

    private Article.Status readStatus(String prompt) {
        while (true) {
            String input = getUserInput(prompt);
            if (input == null) {
                return null;
            }

            try {
                return Article.Status.valueOf(input.trim().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                showError("Invalid status. Available values: PENDING, MODERATING, REJECTED, PUBLISHED.");
            }
        }
    }

    private String getErrorMessage(RuntimeException exception) {
        return exception.getMessage() == null || exception.getMessage().isBlank()
                ? exception.getClass().getSimpleName()
                : exception.getMessage();
    }
}






