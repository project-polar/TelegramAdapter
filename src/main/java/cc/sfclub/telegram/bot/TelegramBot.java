package cc.sfclub.telegram.bot;

import cc.sfclub.core.Core;
import cc.sfclub.telegram.TelegramAdapter;
import cc.sfclub.transform.Bot;
import cc.sfclub.transform.ChatGroup;
import cc.sfclub.transform.Contact;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TelegramBot extends Bot {
    public static Map<Long, Long> contactsAndGroup = new HashMap<>();
    public static final String PLATFORM_NAME = "Telegram";
    private TelegramAdapter api;
    @Override
    public String getName() {
        return PLATFORM_NAME;
    }

    public void setApi(TelegramAdapter api) {
        this.api = api;
    }

    @Override
    public Optional<ChatGroup> getGroup(long id) {
        return Optional.of(new TelegramGroup(id, Collections.emptySet(),api));
    }

    @Override
    public Optional<Contact> asContact(String userId) {
        return getContact(Long.parseLong(Core.get().userManager().byUUID(userId).getPlatformId()));
    }

    @Override
    public Optional<Contact> getContact(long id) {
        return Optional.of(new TelegramContact(id,"invalid","",api));
    }
}
