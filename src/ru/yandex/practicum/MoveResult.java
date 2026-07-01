package ru.yandex.practicum;

// Вспомогательный класс
public class MoveResult {
    private final String word; // Слово
    private final String result; // Результат сравнения с загаданным словом

    public MoveResult(String word, String result) {
        this.word = word;
        this.result = result;
    }

    public String getWord() {
        return word;
    }

    public String getResult() {
        return result;
    }
}
