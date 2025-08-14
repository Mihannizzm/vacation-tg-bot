package com.example.bot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChatIdStorage {
    private static final String FILE_NAME = "chatid.txt";

    public static void saveChatId(Long chatId) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILE_NAME))) {
            writer.write(chatId.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Long loadChatId() {
        try {
            Path path = Path.of(FILE_NAME);
            if (Files.exists(path)) {
                String content = Files.readString(path).trim();
                return Long.parseLong(content);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}