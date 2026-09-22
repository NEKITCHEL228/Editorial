package presentation;

import data.local.database.DatabaseConfig;
import data.local.database.DatabaseMigrator;
import domain.model.Article;

import java.util.List;
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

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();
        DatabaseMigrator migrator = new DatabaseMigrator(config);

        migrator.migrate();
        new ConsoleView().run();
    }

    public void run() {
        boolean isRunning = true;

        while (isRunning) {
            showStartOptions();
            int command = getMenuChoice();

            switch (command) {
                case SHOW_ARTICLES_COMMAND -> showArticles(List.of());
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
        getUserInput("Enter author ID: ");
        getUserInput("Enter title: ");
        getUserInput("Enter content: ");
        getUserInput("Enter status (PENDING, MODERATING, REJECTED, PUBLISHED): ");
        getUserInput("Enter published at: ");
        showMessage("Add article is not implemented yet.");
    }

    private void editArticle() {
        getUserInput("Enter article ID: ");
        getUserInput("Enter new title: ");
        getUserInput("Enter new content: ");
        getUserInput("Enter new status (PENDING, MODERATING, REJECTED, PUBLISHED): ");
        showMessage("Edit article is not implemented yet.");
    }

    private void deleteArticle() {
        getUserInput("Enter article ID: ");
        showMessage("Delete article is not implemented yet.");
    }

    private void getArticleById() {
        getUserInput("Enter article ID: ");
        showMessage("Get article by ID is not implemented yet.");
    }

    private void filterArticles() {
        getUserInput("Enter filter criteria: ");
        showMessage("Filter articles is not implemented yet.");
    }

    private void sortArticles() {
        showMessage("Sort articles is not implemented yet.");
    }

    private void searchArticles() {
        getUserInput("Enter search keyword: ");
        showMessage("Search articles is not implemented yet.");
    }
}
