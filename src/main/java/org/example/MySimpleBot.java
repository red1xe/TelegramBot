package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.LocalDate;


public class MySimpleBot implements LongPollingSingleThreadUpdateConsumer {

    LocalDate today = LocalDate.now();

    // Get the token from the environment variables
    String BOT_TOKEN = System.getenv("TOKEN");
    private TelegramClient telegramClient = new OkHttpTelegramClient(BOT_TOKEN);


    @Override
    public void consume(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            if (message_text.equals("/about")) {
                sendPhoneNumberRequest(chat_id);
            }
        } else {
            System.out.println("#############");
            System.out.println(update.getMessage().getContact());
            System.out.println("#############");
        }

    }

    private void sendPhoneNumberRequest(long chatId) {
        KeyboardButton button = KeyboardButton.builder().text("Share Phone Number").requestContact(true).build();

        ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup.builder()
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .keyboardRow(new KeyboardRow(button))
                .build();
        SendMessage keyboardMarkupMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Please share your phone number with us.")
                .replyMarkup(keyboardMarkup)
                .allowSendingWithoutReply(false)
                .build();
        try {
            telegramClient.execute(keyboardMarkupMessage);
            System.out.println("Phone number request sent.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



