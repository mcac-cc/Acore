package cc.mcac.acore.database;

import cc.mcac.acore.config.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private final HikariDataSource dataSource;

    public DatabaseManager(Logger logger, ConfigManager configManager) {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(configManager.get().database.jdbcUrl);
        config.setUsername(configManager.get().database.username);
        config.setPassword(configManager.get().database.password);

        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        this.dataSource = new HikariDataSource(config);
        logger.info("Database connection established");
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

}
