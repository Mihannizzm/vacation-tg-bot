package com.example.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class MyBot extends TelegramLongPollingBot {
    private final String botToken;
    private Long chatIdForNotifications;

    public MyBot(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return "MyVacationBot"; // имя бота
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            switch (text) {
                case "/start@VacationTimeBot", "/start" -> {
                    chatIdForNotifications = chatId;
                    ChatIdStorage.saveChatId(chatId); // Сохраняем в файл

                    sendText(chatId,
                            "👋 Привет! Я бот-отсчёт до нашей незабываемой поездки в Тайланд 🌴☀️\n\n" +
                                    "📌 Команды:\n" +
                                    "• /start — описание бота\n" +
                                    "• /timeleft — сколько осталось до вылета ✈️"
                    );
                }
                case "/timeleft@VacationTimeBot", "/timeleft" -> sendText(chatId, getTimeLeft());
            }
        }
    }

    public void sendText(Long chatId, String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(chatId.toString())
                    .text(text)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTimeLeft() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC+4"));
        ZonedDateTime flightTime = ZonedDateTime.of(
                2025, 10, 26, 23, 55, 0, 0,
                ZoneId.of("UTC+3")
        );

        Duration duration = Duration.between(now, flightTime);
        if (duration.isNegative()) {
            return "✈️🏖 Отпуск уже начался 🥳";
        }

        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();

        return String.format(
                "🌴☀️ До вылета в Тайланд осталось:\n\n" +
                        "📅 %d дн " +
                        "⏳ %d чac " +
                        "🕒 %d мин",
                days, hours, minutes
        ).replace("-", "\\-"); // Экранируем минус для MarkdownV2
    }

    public Long getChatIdForNotifications() {
        return chatIdForNotifications;
    }
}