package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    @Test
    void checkResultShouldReturnAllPlusesForEqualWords() throws WordLengthException {
        WordleDictionary dictionary = new WordleDictionary(List.of("герой"));
        String result = dictionary.checkResult("герой", "герой");
        assertEquals("+++++", result);
    }

    @Test
    void checkResultShouldReturnCorrectResultForDifferentWords() throws WordLengthException {
        WordleDictionary dictionary = new WordleDictionary(List.of("герой"));
        String result = dictionary.checkResult("герой", "гонец");
        assertEquals("+^-^-", result);
    }

    @Test
    void checkResultShouldCorrectlyHandleRepeatedLetters() throws WordLengthException {
        WordleDictionary dictionary = new WordleDictionary(List.of("банан"));
        String result = dictionary.checkResult("банан", "ааааа");
        assertEquals("-+-+-", result);
    }

    @Test
    void validateWordShouldThrowWordFormatExceptionForEnglishLetters() {
        WordleDictionary dictionary = new WordleDictionary(List.of("герой"));
        assertThrows(WordFormatException.class, () -> {
            dictionary.validateWord("apple");
        });
    }

    @Test
    void validateWordShouldThrowWordLengthExceptionForWrongLength() {
        WordleDictionary dictionary = new WordleDictionary(List.of("герой"));
        assertThrows(WordLengthException.class, () -> {
            dictionary.validateWord("плебей");
        });
    }

    @Test
    void containsShouldNormalizeYoLetterAndUpperCase() {
        WordleDictionary dictionary = new WordleDictionary(List.of("ежики"));
        assertTrue(dictionary.contains("ЁЖИКИ"));
    }
}