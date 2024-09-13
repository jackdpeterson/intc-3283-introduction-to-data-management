package edu.northwestu.intc3283.datasourcestarter.config;
import org.flywaydb.core.Flyway;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import java.time.Duration;

@Component
public class DatabaseTestContextConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    @ServiceConnection
    public static MySQLContainer<?> sqlContainer = new MySQLContainer<>("mysql:8.0")
            .withReuse(false)
            .withUsername("root")
            .withPassword("test")
            .withDatabaseName("testdb") // Add database name if not present
            .withInitScript("db/init.sql");

    @Override
    public void initialize(final ConfigurableApplicationContext configurableApplicationContext) {

        sqlContainer
                .withStartupTimeout(Duration.ofMinutes(10))
                .withNetworkMode("bridge")
                .withReuse(false)
                .start();

        TestPropertyValues.of(
                        "spring.profiles.active=mysql",
                        "spring.flyway.enabled=false",
                        "spring.datasource.url=" + sqlContainer.getJdbcUrl(), // Correct the property key
                        "spring.datasource.username=" + sqlContainer.getUsername(), // Add username property
                        "spring.datasource.password=" + sqlContainer.getPassword(), // Add password property
                        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
                        "spring.datasource.initialization-mode=always"
                )
                .applyTo(configurableApplicationContext.getEnvironment());

        final Flyway fly = Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(sqlContainer.getJdbcUrl(), sqlContainer.getUsername(), sqlContainer.getPassword())
                .schemas(sqlContainer.getDatabaseName())
                .load();
        fly.migrate();
    }
}
