package presentation;

import domain.model.Article;

import java.util.List;

public interface View {
    void showStartOptions();
    void showArticles();
    void showMessage(String message);
    void showError(String error);
    String getUserInput(String prompt);
    int getMenuChoice();
}
