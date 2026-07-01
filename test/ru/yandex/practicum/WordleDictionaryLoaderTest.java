package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordleDictionaryLoaderTest {

    @Test
    void readFileShouldReadLinesFromFile() throws IOException {
        Path filePath = Path.of("test_words.txt");

        try {
            Files.write(filePath, List.of("герой", "гонец", "банан"), StandardCharsets.UTF_8);

            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            List<String> words = loader.readFile(filePath.toString());
            assertEquals(List.of("герой", "гонец", "банан"), words);
        } finally {
            Files.deleteIfExists(filePath);
        }
    }
}