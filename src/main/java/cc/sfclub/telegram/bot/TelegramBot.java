package cc.sfclub.telegram.bot;

import cc.sfclub.core.Core;
import cc.sfclub.transform.Bot;
import cc.sfclub.transform.Contact;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TelegramBot extends Bot {
    public static Map<Long, Long> contactsAndGroup = new HashMap<>();
    public static final String PLATFORM_NAME = "Telegram";
    @Override
    public String getName() {
        return PLATFORM_NAME;
    }

    @Override
    public Optional<Contact> asContact(String userId) {
        return getContact(Long.parseLong(Core.get().userManager().byUUID(userId).getPlatformId()));
    }
}
