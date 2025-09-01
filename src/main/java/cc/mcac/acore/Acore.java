package cc.mcac.acore;

import cc.mcac.acore.bot.MessageListener;
import cc.mcac.acore.config.ConfigManager;
import cc.mcac.acore.database.DatabaseManager;
import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "acore",
        name = "Acore",
        version = BuildConstants.VERSION
        , authors = {"acsoto"}
)
public class Acore {

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private ConfigManager configManager;
    private DatabaseManager databaseManager;


    @Inject
    public Acore(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        logger.info("Acore plugin initialized with version: {}", BuildConstants.VERSION);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        loadConfig();
        loadDatabase();
        server.getEventManager().register(this, new MessageListener(server));
    }

    private void loadConfig() {
        this.configManager = new ConfigManager(logger, dataDirectory);
        logger.info("Acore config loaded on server: {}", configManager.get().server);
    }

    private void loadDatabase() {
        this.databaseManager = new DatabaseManager(logger, configManager);
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        databaseManager.shutdown();
        logger.info("Database closed.");
    }
}

