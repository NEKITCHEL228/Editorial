package presentation;

import domain.model.Article;

import java.util.List;
import java.util.Scanner;

import data.local.database.DatabaseConfig;
import data.local.database.DatabaseMigrator;
import org.flywaydb.core.internal.database.base.Database;

public class ConsoleView implements View {
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();
        DatabaseMigrator migrator = new DatabaseMigrator(config);

        migrator.migrate();
    }


    @Override
    public void showStartOptions() {

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