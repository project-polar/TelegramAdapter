package cc.sfclub.telegram;

import cc.sfclub.core.Core;
import cc.sfclub.events.server.ServerStartedEvent;
import cc.sfclub.plugin.Plugin;
import cc.sfclub.plugin.SimpleConfig;

import cc.sfclub.telegram.bot.TelegramBot;
import org.greenrobot.eventbus.Subscribe;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class AdapterMain extends Plugin {
    private SimpleConfig<Config> config;
    public static final long STARTUP_TIME = System.currentTimeMillis();

    public static Config getMainConfig() {
        return AdapterMain.get(AdapterMain.class).config.get();
    }

    @Subscribe
    @SuppressWarnings("all")
    public void onServerStart(ServerStartedEvent e) {
        long i = STARTUP_TIME - 1; // initialize
        getLogger().info("Initializing Telegram Bot");
        if ("TOKEN HERE".equals(getMainConfig().token)) {
            getLogger().warn("You have not set your token in the config");
            return;
        }
        // initialize telegram bot instance
        var tbot = new TelegramBot();
        Core.get().registerBot(tbot);
        getLogger().info("Done!");
        try {
            var tbotApi = new TelegramBotsApi(DefaultBotSession.class);
            var a = new TelegramAdapter(getMainConfig().botName, getMainConfig().token, tbot);
            tbotApi.registerBot(a);
            tbot.setApi(a);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public void onEnable() {
        getLogger().info("Loading Telegram Config...");
        getDataFolder().mkdir();
        config = new SimpleConfig<>(this,Config.class);
        config.saveDefault();
        config.reloadConfig();
        getLogger().info("Done!");
    }

    @Override
    public void onDisable() {

    }
}
