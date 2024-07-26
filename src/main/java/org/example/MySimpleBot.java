package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;

public class MySimpleBot implements LongPollingSingleThreadUpdateConsumer {
    private static final HashMap<String, String> userPhotoMap = new HashMap<>();
    private static final HashMap<String, String> userInfo = new HashMap<>();

    static {
        userInfo.put("Refik", "Refik is a farmer at Sokun.");
        userInfo.put("Emre", "Emre is a nurse at Etlik Hospital.");
        userInfo.put("Babi", "Babi is a marketing specialist at Migros");
        userInfo.put("Umut", "Umut is a driver at Koy Koop.");
        userInfo.put("Selim", "Selim is a software engineer at Bataryasan.");
        userInfo.put("Akif", "Akif is a lawyer at Akif Law Firm.");
        userInfo.put("Samet", "Samet is a waiter at Malibu Cafe.");
        userInfo.put("Barış", "Barış is a electrician at Barış Elektrik.");
    }

    static {
        userPhotoMap.put("Refik", "https://scontent.cdninstagram.com/v/t51.29350-15/437562101_393389206866319_5658685070126484352_n.jpg?stp=dst-jpegr_e35&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuaGRyLmYyOTM1MCJ9&_nc_ht=scontent.cdninstagram.com&_nc_cat=107&_nc_ohc=Ba99EQ6FFT8Q7kNvgGX9GF5&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzM0NDcyMzY4NDA1Njc2NzkxMw%3D%3D.2-ccb7-5&oh=00_AYCj4GGgH8WDhcMM97E7Rg0XIhWhUn3bxzmwXVENawWOYA&oe=66A7DADE&_nc_sid=10d13b");
        userPhotoMap.put("Emre", "https://scontent.cdninstagram.com/v/t51.29350-15/210115188_939385250239362_2695912988295978646_n.jpg?stp=dst-jpg_e35&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuc2RyLmYyOTM1MCJ9&_nc_ht=scontent.cdninstagram.com&_nc_cat=106&_nc_ohc=1AUAWxTLXTQQ7kNvgFbMOVx&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MjYxMTIxMzc0ODA5OTQ1ODM1MA%3D%3D.2-ccb7-5&oh=00_AYDzvhWh6eJfaRTxAhIDSF5vVavODmsx2GFIvCu1QmNFXg&oe=66A7D887&_nc_sid=10d13b");
        userPhotoMap.put("Babi", "https://scontent.cdninstagram.com/v/t51.29350-15/431389221_777154370950509_7470145621126166983_n.jpg?stp=dst-jpg_e35&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuc2RyLmYyOTM1MCJ9&_nc_ht=scontent.cdninstagram.com&_nc_cat=109&_nc_ohc=9rO1yswQjAUQ7kNvgGhccIq&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzMyMDExOTE1ODc0ODUwMDkwOA%3D%3D.2-ccb7-5&oh=00_AYAI2sTq4Bd5-fdMlvX8Majqlo5BXCKsAaXp3y5hWqCu0Q&oe=66A800C1&_nc_sid=10d13b");
        userPhotoMap.put("Umut", "https://scontent.cdninstagram.com/v/t51.29350-15/441367306_957080322411949_6542012933078847695_n.jpg?stp=dst-jpg_e35&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE3OTkuc2RyLmYyOTM1MCJ9&_nc_ht=scontent.cdninstagram.com&_nc_cat=101&_nc_ohc=AQj5XuPeyGsQ7kNvgGbannP&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzM1OTk2MTAxNzAyMTE4MjI3OA%3D%3D.2-ccb7-5&oh=00_AYARVl-SX89svNaApJ9fbBLK3B-HBKk9keyk_jA-UNibrw&oe=66A800BC&_nc_sid=10d13b");
        userPhotoMap.put("Selim", "https://scontent.cdninstagram.com/v/t51.29350-15/448875971_1119584665786602_1228537622047818872_n.webp?stp=dst-jpg_e35&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xMDgweDEzNTAuc2RyLmYyOTM1MCJ9&_nc_ht=scontent.cdninstagram.com&_nc_cat=106&_nc_ohc=7dq3QL2FsKwQ7kNvgEHEO7h&gid=a024213194e34865962aac36e76e8006&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzM5ODQwMTU5NzU1OTE5NTY4Nw%3D%3D.2-ccb7-5&oh=00_AYAsc1VxOJaZyxxLCJSRldb3V92O2bVULvCblPz6eDe2pQ&oe=66A81BD8&_nc_sid=10d13b");
        userPhotoMap.put("Akif", "https://scontent.cdninstagram.com/v/t51.29350-15/432032148_1179815842982494_4815268670369334228_n.webp?stp=dst-jpg_e35&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xMDgweDEzNTAuc2RyLmYyOTM1MCJ9&_nc_ht=scontent.cdninstagram.com&_nc_cat=110&_nc_ohc=E0t9S66hlaEQ7kNvgG0EKdH&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzMyMDc5NjkxMjk3NTY4NDA1NQ%3D%3D.2-ccb7-5&oh=00_AYCQTQTyuO-OhvfBWfXRXgCQoh2uoWr7PvRlLs3a9vfkzg&oe=66A80232&_nc_sid=10d13b");
        userPhotoMap.put("Samet", "https://instagram.fesb4-2.fna.fbcdn.net/v/t51.2885-19/439146549_827124799448783_2136636264709107352_n.jpg?_nc_ht=instagram.fesb4-2.fna.fbcdn.net&_nc_cat=111&_nc_ohc=TxM8xUUykOwQ7kNvgGZF1JA&edm=APHcPcMBAAAA&ccb=7-5&oh=00_AYD06wNGs19r3LPZUZaCBJwstnsg8brVrsI-1GCkLlLaSQ&oe=66A81803&_nc_sid=bef7bc");
        userPhotoMap.put("Barış", "https://instagram.fesb9-1.fna.fbcdn.net/v/t51.2885-19/448872902_1215992866429928_9144336828724939811_n.jpg?_nc_ht=instagram.fesb9-1.fna.fbcdn.net&_nc_cat=108&_nc_ohc=W7r-J0wT3CoQ7kNvgF9YaCb&gid=c12bb3f474914fbb87af0ae06343446a&edm=APHcPcMBAAAA&ccb=7-5&oh=00_AYCTxbGZgaoWtIjeZUefDYa1_muy5gB34i_O5Yc9VzqUfg&oe=66A81022&_nc_sid=bef7bc");

    }

    String BOT_TOKEN = System.getenv("TOKEN");
    private TelegramClient telegramClient = new OkHttpTelegramClient(BOT_TOKEN);

    public void sendPhotoBasedOnMessage(String messageText, String chatId) {
        String photoUrl = userPhotoMap.get(messageText);

        if (photoUrl != null) {
            SendPhoto msg = SendPhoto
                    .builder()
                    .chatId(chatId)
                    .photo(new InputFile(photoUrl))
                    .caption(messageText)
                    .build();
            try {
                telegramClient.execute(msg); // Call method to send the photo
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where the messageText does not match any user
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("User not found")
                    .build();
        }
    }

    public void sendInfoBasedOnMessage(String messageText, String chatId) {
        String info = userInfo.get(messageText);

        if (info != null) {
            SendMessage msg = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(info)
                    .build();
            try {
                telegramClient.execute(msg); // Call method to send the photo
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where the messageText does not match any user
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("User not found")
                    .build();
        }
    }

    @Override
    public void consume(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            if (message_text.equals("/pic")) {
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
            } else if (message_text.equals("/start")) {
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text("Here is your keyboard")
                        .build();

                message.setReplyMarkup(ReplyKeyboardMarkup
                        .builder()
                        // Add first row of 3 buttons
                        .keyboardRow(new KeyboardRow("Refik", "Emre", "Umut", "Babi"))
                        // Add second row of 3 buttons
                        .keyboardRow(new KeyboardRow("Selim", "Akif", "Samet", "Barış"))
                        .build());
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/help")) {
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text("You can use the following commands:\n" +
                                "/start - to get the keyboard\n" +
                                "/pic - to get a picture\n" +
                                "/help - to get help")
                        .build();
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (userPhotoMap.containsKey(message_text)) {
                sendPhotoBasedOnMessage(message_text, String.valueOf(chat_id));
                sendInfoBasedOnMessage(message_text, String.valueOf(chat_id));
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


