package presentation;

import domain.model.Article;
import domain.model.User;

import java.util.ArrayList;
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
    private static final int ADD_USER_COMMAND = 9;
    private static final int EDIT_USER_COMMAND = 10;
    private static final int DELETE_USER_COMMAND = 11;
    private static final int GET_USER_BY_ID_COMMAND = 12;
    private static final int EXIT_COMMAND = 0;

    private List<Article> articles = new ArrayList<>();

    private Presenter presenter;
    private final Scanner scanner = new Scanner(System.in);

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void run() {
        boolean isRunning = true;

        while (isRunning) {
            showStartOptions();

            try {
                int command = getMenuChoice();

                switch (command) {
                    case SHOW_ARTICLES_COMMAND -> showArticles();
                    case ADD_ARTICLE_COMMAND -> addArticle();
                    case EDIT_ARTICLE_COMMAND -> editArticle();
                    case DELETE_ARTICLE_COMMAND -> deleteArticle();
                    case GET_ARTICLE_BY_ID_COMMAND -> getArticleById();
                    case FILTER_ARTICLES_COMMAND -> presenter.onFilterArticles();
                    case SORT_ARTICLES_COMMAND -> presenter.onSortArticles();
                    case SEARCH_ARTICLES_COMMAND -> presenter.onSearchArticle();
                    case ADD_USER_COMMAND -> addUser();
                    case EDIT_USER_COMMAND -> editUser();
                    case DELETE_USER_COMMAND -> deleteUser();
                    case GET_USER_BY_ID_COMMAND -> getUserById();
                    case EXIT_COMMAND -> {
                        showMessage("Exiting the application");
                        isRunning = false;
                    }
                    default -> showError("Unknown command");
                }
            } catch (RuntimeException e) {
                showError(e.getMessage());
            }
        }
    }

    private void showArticle(Article article) {
        if (article == null) {
            return;
        }

        System.out.println();
        System.out.println("ID: " + article.getId());
        System.out.println("Author ID: " + article.getAuthorId());
        System.out.println("Title: " + article.getTitle());
        System.out.println("Content: " + article.getContent());
        System.out.println("Status: " + article.getStatus());
        System.out.println("Published at: " + article.getPublishedAt());
    }

    private void showUser(User user) {
        if (user == null) {
            return;
        }

        System.out.println();
        System.out.println("ID: " + user.getId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Role: " + user.getRole());
    }

    private void addArticle() {
        int authorId = getPositiveIntInput("Enter author ID:");
        String title = getRequiredInput("Enter title:");
        String content = getRequiredInput("Enter content:");
        String publishedAt = getUserInput("Enter published at (leave empty if unpublished):").trim();

        if (publishedAt.isEmpty()) {
            publishedAt = null;
        }

        Article article = new Article(0, authorId, title, content, Article.Status.PENDING, publishedAt);
        presenter.onAddArticle(article);

        articles.add(article);
    }

    private void deleteArticle() {
        int articleId = getPositiveIntInput("Enter article ID:");

        presenter.onDeleteArticle(articleId);
    }

    private void editArticle() {
        int articleId = getPositiveIntInput("Enter article ID:");
        String title = getRequiredInput("Enter new title:");
        String content = getRequiredInput("Enter new content:");
        Article.Status status = getStatusInput("Enter new status:");

        presenter.onEditArticle(articleId, title, content, status);
    }

    private void getArticleById() {
        int articleId = getPositiveIntInput("Enter article ID to find:");

        Article article = presenter.onGetArticleById(articleId);

        showArticle(article);
    }

    private void addUser() {
        String username = getRequiredInput("Enter username:");
        String email = getRequiredInput("Enter email:");
        String passwordHash = getRequiredInput("Enter password hash:");
        User.Role role = getRoleInput("Enter role:");

        User user = new User(0, username, email, passwordHash, role);
        presenter.onAddUser(user);
    }

    private void editUser() {
        int userId = getPositiveIntInput("Enter user ID:");
        String username = getRequiredInput("Enter new username:");
        String email = getRequiredInput("Enter new email:");
        String passwordHash = getRequiredInput("Enter new password hash:");
        User.Role role = getRoleInput("Enter new role:");

        User user = new User(userId, username, email, passwordHash, role);
        presenter.onEditUser(user);
    }

    private void deleteUser() {
        int userId = getPositiveIntInput("Enter user ID:");

        presenter.onDeleteUser(userId);
    }

    private void getUserById() {
        int userId = getPositiveIntInput("Enter user ID to find:");
        User user = presenter.onGetUserById(userId);

        showUser(user);
    }

    @Override
    public void showStartOptions() {
        System.out.println("-------------------------");
        System.out.println("1. Show all articles");
        System.out.println("2. Add article");
        System.out.println("3. Edit article");
        System.out.println("4. Delete article");
        System.out.println("5. Get article by ID");
        System.out.println("6. Filter articles");
        System.out.println("7. Sort articles");
        System.out.println("8. Search articles");
        System.out.println("9. Add user");
        System.out.println("10. Edit user");
        System.out.println("11. Delete user");
        System.out.println("12. Get user by ID");
        System.out.println("0. Exit");
        System.out.println("-------------------------");
    }

    @Override
    public void showArticles() {
        if (articles == null || articles.isEmpty()) {
            showMessage("No articles found");
            return;
        }

        for (Article article : articles) {
            showArticle(article);
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println();
        System.out.println(message);
    }

    @Override
    public void showError(String error) {
        System.out.println("Error: " + error);
    }

    @Override
    public String getUserInput(String prompt) {
        System.out.println(prompt);
        System.out.flush();

        return scanner.nextLine();
    }

    @Override
    public int getMenuChoice() {
        return getIntInput("Enter command:");
    }

    private int getPositiveIntInput(String prompt) {
        while (true) {
            int value = getIntInput(prompt);

            if (value > 0) {
                return value;
            }

            showError("Enter a positive number");
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            String input = getUserInput(prompt).trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                showError("Enter a valid number");
            }
        }
    }

    private String getRequiredInput(String prompt) {
        while (true) {
            String input = getUserInput(prompt).trim();

            if (!input.isEmpty()) {
                return input;
            }

            showError("Value cannot be empty");
        }
    }

    private Article.Status getStatusInput(String prompt) {
        while (true) {
            String input = getUserInput(prompt).trim().toUpperCase(Locale.ROOT);

            try {
                return Article.Status.valueOf(input);
            } catch (IllegalArgumentException e) {
                showError("Available statuses: PENDING, MODERATING, REJECTED, PUBLISHED");
            }
        }
    }

    private User.Role getRoleInput(String prompt) {
        while (true) {
            String input = getUserInput(prompt).trim().toUpperCase(Locale.ROOT);

            try {
                return User.Role.valueOf(input);
            } catch (IllegalArgumentException e) {
                showError("Available roles: ADMIN, EDITOR, AUTHOR");
            }
        }
    }

}
