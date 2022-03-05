package cc.sfclub.telegram;

import cc.sfclub.core.Core;
import cc.sfclub.events.Event;
import cc.sfclub.events.message.direct.PrivateMessage;
import cc.sfclub.events.message.group.GroupMessage;
import cc.sfclub.telegram.bot.TelegramBot;
import cc.sfclub.telegram.bot.TelegramContact;
import cc.sfclub.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static cc.sfclub.telegram.bot.TelegramBot.PLATFORM_NAME;


public class TelegramAdapter extends TelegramLongPollingBot {
    private final String userName;
    private final String token;
    private final TelegramBot bot;
    private static final Logger LOGGER = LoggerFactory.getLogger("Telegram");

    public TelegramAdapter(String userName, String token, TelegramBot bot) {
        this.userName = userName;
        this.token = token;
        this.bot = bot;
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onRegister() {
        // fetch groups
        super.onRegister();

    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        if (message == null || !message.hasText()) {
            // current not planned
            return;
        }
        var text = message.getText();
        var chatId = message.getChatId();
        var senderId = message.getFrom().getId();
        if(!message.isSuperGroupMessage() && !message.isUserMessage()){
            // unplanned.
            return;
        }
        // check for polar users
        User u = Core.get().userManager().byPlatformID(PLATFORM_NAME, String.valueOf(senderId));
        if (u == null) {
            u = Core.get().userManager().register(Core.get().permCfg().getDefaultGroup(), PLATFORM_NAME, String.valueOf(senderId));
            u.setUserName(message.getFrom().getFirstName() + message.getFrom().getLastName());
        }
        if(message.isSuperGroupMessage()){
            var gm = new GroupMessage(
                    u.getUniqueID(),
                    text,
                    chatId,
                    PLATFORM_NAME,
                    message.getMessageId()
            );
            Event.postEvent(gm);
        }else if(message.isUserMessage()){
            var pm = new PrivateMessage(
                    u.getUniqueID(),
                    text,

                    PLATFORM_NAME,
                    message.getMessageId()
            );
            Event.postEvent(pm);
        }else{
            throw new IllegalStateException("Unexcepted " + message);
        }

        LOGGER.info("[{}] {}", u.getUserName(), text);

    }
}
