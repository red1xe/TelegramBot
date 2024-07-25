package org.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        String BOT_TOKEN = System.getenv("TOKEN");
        try {
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(BOT_TOKEN, new MySimpleBot());
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}