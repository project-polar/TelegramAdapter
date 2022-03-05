package cc.sfclub.telegram.bot;

import cc.sfclub.telegram.TelegramAdapter;
import cc.sfclub.transform.ChatGroup;
import cc.sfclub.transform.Contact;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Set;

public class TelegramGroup extends ChatGroup {
    private final TelegramAdapter adapter;

    public TelegramGroup(long ID, Set<Contact> members, TelegramAdapter adapter) {
        super(ID, members);
        this.adapter = adapter;

    }

    @Override
    public String getName() {
        return "[Some Telegram Group]";
    }

    @Override
    public String honorOf(Contact contact) {
        return "";
    }

    @Override
    public String nickOf(Contact contact) {
        return "";
    }

    @Override
    public Role roleOf(Contact contact) {
        return Role.MEMBER;
    }

    @Override
    public void sendMessage(String message) {
        var msg = new SendMessage();
        msg.setText(message);
        msg.setChatId(String.valueOf(super.getID()));
        try {
            adapter.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reply(long messageId, String message) {
        var msg = new SendMessage();
        msg.setText(message);
       // msg.enableMarkdown(true);
        msg.setChatId(String.valueOf(getID()));
        
        msg.setReplyToMessageId((int) messageId);
        try {
            adapter.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
