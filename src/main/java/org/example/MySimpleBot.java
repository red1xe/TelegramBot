package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MySimpleBot implements LongPollingSingleThreadUpdateConsumer {
    String BOT_TOKEN = System.getenv("TOKEN");
    private TelegramClient telegramClient = new OkHttpTelegramClient(BOT_TOKEN);

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            String user_username = update.getMessage().getChat().getUserName();
            long user_id = update.getMessage().getChat().getId();
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chat_id)
                    .text(update.getMessage().getText() + " " + user_first_name + " " + user_last_name)
                    .build();
            log(user_first_name, user_last_name, user_username, user_id, message_text, message.getText());
            try {
                telegramClient.execute(message);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void log(String first_name, String last_name, String username, long id, String text, String bot_answer) {
        System.out.println("\n ----------------------------");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Date: " + dateFormat.format(date));
        System.out.println("Message from: " + first_name + " " + last_name + " with id: " + id);
        System.out.println("Message: " + text);
        System.out.println("Bot answer: " + bot_answer);
    }


}
