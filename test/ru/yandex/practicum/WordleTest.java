package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class WordleTest {

    @Test
    void mainShouldStartGameAndFinishWithVictory() throws Exception {
        Path dictionaryPath = Path.of("words_ru.txt");
        Path logPath = Path.of("log.txt");

        byte[] oldDictionaryContent = null;
        boolean dictionaryExisted = Files.exists(dictionaryPath);

        if (dictionaryExisted) {
            oldDictionaryContent = Files.readAllBytes(dictionaryPath);
        }

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            Files.write(dictionaryPath, List.of("герой"), StandardCharsets.UTF_8);

            System.setIn(new ByteArrayInputStream("герой\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(outputStream, true, StandardCharsets.UTF_8));

            Wordle.main(new String[0]);
            String output = outputStream.toString(StandardCharsets.UTF_8);

            assertTrue(output.contains("Введите слово:"));
            assertTrue(output.contains("+++++"));
            assertTrue(output.contains("Победа!"));
            assertTrue(output.contains("Загаданное слово: герой"));

        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);

            if (dictionaryExisted) {
                Files.write(dictionaryPath, oldDictionaryContent);
            } else {
                Files.deleteIfExists(dictionaryPath);
            }

            Files.deleteIfExists(logPath);
        }
    }
}