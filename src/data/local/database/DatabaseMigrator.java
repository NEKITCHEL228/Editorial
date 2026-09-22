package data.local.database;
import org.flywaydb.core.Flyway;
public class DatabaseMigrator {
    private final DatabaseConfig databaseConfig;

    public DatabaseMigrator(DatabaseConfig config) {
        this.databaseConfig = config;
    }

    public void migrate() {
        Flyway flyway = Flyway.configure().dataSource(databaseConfig.getUrl(), databaseConfig.getUser(), databaseConfig.getPassword())
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
    }
}
