package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    @Test
    void containsShouldNormalizeYoLetterAndUpperCase() {
        WordleDictionary dictionary = new WordleDictionary(List.of("ежики"));
        assertTrue(dictionary.contains("ЁЖИКИ"));
    }

    @Test
    void addWordShouldIgnoreWordsWithWrongLength() {
        WordleDictionary dictionary = new WordleDictionary(List.of());

        dictionary.addWord("кот");
        dictionary.addWord("герой");

        assertTrue(dictionary.contains("герой"));
        assertFalse(dictionary.contains("кот"));
    }
}