package cc.sfclub.telegram;

import cc.sfclub.events.server.ServerStartedEvent;
import cc.sfclub.plugin.Plugin;
import cc.sfclub.plugin.SimpleConfig;
import com.pengrad.telegrambot.TelegramBot;
import org.greenrobot.eventbus.Subscribe;

public class AdapterMain extends Plugin {
    private SimpleConfig<Config> config;
    private SimpleConfig<Cred> cred;
    public static Config getMainConfig(){
        return AdapterMain.get(AdapterMain.class).config.get();
    }
    @Subscribe
    @SuppressWarnings("all")
    public void onServerStart(ServerStartedEvent e) {
        getLogger().info("[TelegramAdapter] Login your telegram bot...");
        TelegramBot bot = new TelegramBot(getMainConfig().token);
    }

    @Override
    public void onEnable() {
        getLogger().info("[TelegramAdapter] Loading Config...");
        config.saveDefault();
        config.reloadConfig();
        getLogger().info("[TelegramAdapter] Finished");
    }

    @Override
    public void onDisable() {

    }
}
