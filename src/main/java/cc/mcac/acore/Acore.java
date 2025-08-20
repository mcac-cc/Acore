package cc.mcac.acore;

import cc.mcac.acore.bot.MessageListener;
import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(
        id = "acore",
        name = "Acore",
        version = BuildConstants.VERSION
        , authors = {"acsoto"}
)
public class Acore {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public Acore(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
        logger.info("Acore plugin initialized with version: {}", BuildConstants.VERSION);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getEventManager().register(this,new MessageListener(server));
    }
}
