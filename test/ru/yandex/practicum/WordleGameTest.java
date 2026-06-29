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
    void getHintShouldReturnWordFromDictionary() {
        WordleDictionary dictionary = new WordleDictionary(List.of("герой", "гонец"));
        WordleGame game = new WordleGame(dictionary);
        String hint = game.getHint();
        assertTrue(dictionary.contains(hint));
    }
}