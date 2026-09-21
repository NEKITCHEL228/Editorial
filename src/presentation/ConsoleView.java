package presentation;

import domain.model.Article;

import java.util.List;
import java.util.Scanner;

public class ConsoleView implements View {
    private final Scanner scanner = new Scanner(System.in);

    static void main() {
        System.out.println("hello world!");
    }

    @Override
    public void showArticles(List<Article> articles) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public String getUserInput(String prompt) {
        return "";
    }

    @Override
    public int getMenuChoice() {
        return 0;
    }
}