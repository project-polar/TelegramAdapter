package cc.sfclub.telegram.bot;

import cc.sfclub.telegram.TelegramAdapter;
import cc.sfclub.transform.Contact;
import cc.sfclub.user.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramContact extends Contact {
    private final String nickName;
    private final String userName;
    private final TelegramAdapter adapter;
    public TelegramContact(long ID, String nickName, String userName, TelegramAdapter adapter) {
        super(ID);
        this.nickName = nickName;
        this.userName = userName;
        this.adapter = adapter;
    }

    @Override
    public String getNickname() {
        return nickName;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public User asPermObj() {
        return null;
    }

    @Override
    public void sendMessage(String message) {
        var msg = new SendMessage();
        msg.setText(message);
        msg.setChatId(String.valueOf(getID()));
        try {
            adapter.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reply(long messageId, String message) {
        var msg = new SendMessage();
        msg.setReplyToMessageId((int)messageId);

        msg.setText(message);
        msg.setChatId(String.valueOf(getID()));
        try {
            adapter.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
