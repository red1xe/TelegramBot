package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class MySimpleBot implements LongPollingSingleThreadUpdateConsumer {
    String BOT_TOKEN = System.getenv("TOKEN");
    private TelegramClient telegramClient = new OkHttpTelegramClient(BOT_TOKEN);

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            if (message_text.equals("/start")) {
                // User send /start
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text(message_text)
                        .build();
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/pic")) {
                // User sent /pic
                SendPhoto msg = SendPhoto
                        .builder()
                        .chatId(chat_id)
                        .photo(new InputFile("https://png.pngtree.com/background/20230519/original/pngtree-this-is-a-picture-of-a-tiger-cub-that-looks-straight-picture-image_2660243.jpg"))
                        .caption("This is a little cat :)")
                        .build();
                try {
                    telegramClient.execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/markup")) {
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text("Here is your keyboard")
                        .build();

                message.setReplyMarkup(ReplyKeyboardMarkup
                        .builder()
                        // Add first row of 3 buttons
                        .keyboardRow(new KeyboardRow("Refic", "Emre", "Umut", "Babi"))
                        // Add second row of 3 buttons
                        .keyboardRow(new KeyboardRow("Selim", "Akif", "Samet", "Barış"))
                        .build());
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("Refic")) {
                // Send a picture to the user
                SendPhoto msg = SendPhoto
                        .builder()
                        .chatId(chat_id)
                        // This time will send the picture using a URL
                        .photo(new InputFile("https://scontent.cdninstagram.com/v/t51.29350-15/437562101_393389206866319_5658685070126484352_n.jpg?stp=dst-jpegr_e35&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuaGRyLmYyOTM1MCJ9&_nc_ht=scontent.cdninstagram.com&_nc_cat=107&_nc_ohc=Ba99EQ6FFT8Q7kNvgGX9GF5&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzM0NDcyMzY4NDA1Njc2NzkxMw%3D%3D.2-ccb7-5&oh=00_AYCj4GGgH8WDhcMM97E7Rg0XIhWhUn3bxzmwXVENawWOYA&oe=66A7DADE&_nc_sid=10d13b"))
                        .caption(message_text)
                        .build();
                try {
                    telegramClient.execute(msg); // Call method to send the photo
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("Emre")) {
                // Send a picture to the user
                SendPhoto msg = SendPhoto
                        .builder()
                        .chatId(chat_id)
                        // This time will send the picture using a URL
                        .photo(new InputFile("https://scontent.cdninstagram.com/v/t51.29350-15/210115188_939385250239362_2695912988295978646_n.jpg?stp=dst-jpg_e35&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuc2RyLmYyOTM1MCJ9&_nc_ht=scontent.cdninstagram.com&_nc_cat=106&_nc_ohc=1AUAWxTLXTQQ7kNvgFbMOVx&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MjYxMTIxMzc0ODA5OTQ1ODM1MA%3D%3D.2-ccb7-5&oh=00_AYDzvhWh6eJfaRTxAhIDSF5vVavODmsx2GFIvCu1QmNFXg&oe=66A7D887&_nc_sid=10d13b"))
                        .caption(message_text)
                        .build();
                try {
                    telegramClient.execute(msg); // Call method to send the photo
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/hide")) {
                // Hide the keyboard
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text("Keyboard hidden")
                        .replyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build())
                        .build();

                try {
                    telegramClient.execute(message); // Call method to send the photo
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                // Unknown command
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text("Unknown command")
                        .build();
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}