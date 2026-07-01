package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    @Test
    void successfulMoveShouldIncreaseSteps() throws WordLengthException, WordFormatException,
            WordNotFoundInDictionaryException {

        WordleDictionary dictionary = new WordleDictionary(List.of("герой"));
        WordleGame game = new WordleGame(dictionary);

        game.move(game.getAnswer());
        assertEquals(1, game.getSteps());
    }

    @Test
    void unknownWordShouldNotIncreaseSteps() {
        WordleDictionary dictionary = new WordleDictionary(List.of("герой"));
        WordleGame game = new WordleGame(dictionary);

        assertThrows(WordNotFoundInDictionaryException.class, () -> {
            game.move("вагон");
        });

        assertEquals(0, game.getSteps());
    }

    @Test
    void correctWordShouldSetVictoryTrue() throws WordLengthException, WordFormatException,
            WordNotFoundInDictionaryException {

        WordleDictionary dictionary = new WordleDictionary(List.of("герой"));
        WordleGame game = new WordleGame(dictionary);

        game.move(game.getAnswer());
        assertTrue(game.isVictory());
        assertTrue(game.isFinished());
    }

    @Test
    void gameShouldFinishAfterSixWrongMoves() throws WordLengthException, WordFormatException,
            WordNotFoundInDictionaryException {

        WordleDictionary dictionary = new WordleDictionary(List.of("герой", "гонец"));
        WordleGame game = new WordleGame(dictionary);
        String wrongWord;

        if (game.getAnswer().equals("герой")) {
            wrongWord = "гонец";
        } else {
            wrongWord = "герой";
        }

        for (int i = 0; i < 6; i++) {
            game.move(wrongWord);
        }

        assertTrue(game.isFinished());
        assertFalse(game.isVictory());
        assertEquals(6, game.getSteps());
    }

    @Test
    void getHintShouldReturnWordFromDictionary() throws WordLengthException {
        WordleDictionary dictionary = new WordleDictionary(List.of("герой", "гонец"));
        WordleGame game = new WordleGame(dictionary);
        String hint = game.getHint();
        assertTrue(dictionary.contains(hint));
    }

    @Test
    void checkResultShouldReturnAllPlusesForEqualWords() throws WordLengthException {

        WordleGame game = new WordleGame(new WordleDictionary(List.of("герой")));

        assertEquals("+++++", game.checkResult("герой", "герой"));
    }

    @Test
    void checkResultShouldReturnCorrectResultForDifferentWords() throws WordLengthException {

        WordleGame game = new WordleGame(new WordleDictionary(List.of("герой")));

        assertEquals("+^-^-", game.checkResult("герой", "гонец"));
    }

    @Test
    void checkResultShouldCorrectlyHandleRepeatedLetters() throws WordLengthException {

        WordleGame game = new WordleGame(new WordleDictionary(List.of("банан")));

        assertEquals("-+-+-", game.checkResult("банан", "ааааа"));
    }

    @Test
    void validateWordShouldThrowWordFormatExceptionForEnglishLetters() {
        WordleGame game = new WordleGame(new WordleDictionary(List.of("герой")));

        assertThrows(WordFormatException.class, () -> game.validateWord("apple"));
    }

    @Test
    void validateWordShouldThrowWordLengthExceptionForWrongLength() {
        WordleGame game = new WordleGame(new WordleDictionary(List.of("герой")));

        assertThrows(WordLengthException.class, () -> game.validateWord("плебей"));
    }
}