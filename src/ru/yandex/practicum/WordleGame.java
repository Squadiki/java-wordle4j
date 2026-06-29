package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WordleGame {

    private String answer; // Загаданное слово

    private int steps; // Шаги

    private WordleDictionary dictionary; // Словарь

    private boolean finished; // Закончена ли игра

    private boolean victory; // Победил ли пользователь

    private List<MoveResult> previousMoves; // Успешные ходы

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.steps = 0;
        this.finished = false;
        this.victory = false;
        this.previousMoves = new ArrayList<>();
    }

    public String getAnswer() {
        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isVictory() {
        return victory;
    }

    // Анализ совпадения слова с ответом
    public String move(String input) throws RuntimeException, WordLengthException,
            WordNotFoundInDictionaryException, WordFormatException {

        if (finished) {
            throw new RuntimeException("Игра окончена.");
        }

        String normalizedInput = dictionary.validateWord(input);

        if (!dictionary.contains(normalizedInput)) {
            throw new WordNotFoundInDictionaryException("Данное слово неизвестно.");
        }

        String result = dictionary.checkResult(answer, input);

        steps++;

        previousMoves.add(new MoveResult(input, result));

        if (result.equals("+++++")) {
            finished = true;
            victory = true;
        } else if (steps >= 6) {
            finished = true;
        }

        return result;
    }

    // Слово-подсказка с учётом всего, что вводил пользователь ранее
    public String getHint() {
        if (finished) {
            throw new RuntimeException("Игра уже окончена.");
        }

        List<String> candidates = new ArrayList<>();

        for (String candidate : dictionary.getWords()) {
            boolean alreadyUsed = false;

            for (MoveResult move : previousMoves) {
                if (move.getWord().equals(candidate)) {
                    alreadyUsed = true;
                    break;
                }
            }

            if (alreadyUsed) {
                continue;
            }

            boolean suitable = true;

            for (MoveResult move : previousMoves) {
                String previousWord = move.getWord();
                String previousResult = move.getResult();

                try {
                    String candidateResult = dictionary.checkResult(candidate, previousWord);

                    if (!candidateResult.equals(previousResult)) {
                        suitable = false;
                        break;
                    }
                } catch (WordLengthException e) {
                    throw new RuntimeException("Ошибка при поиске подсказки.", e);
                }
            }

            if (suitable) {
                candidates.add(candidate);
            }
        }

        if (candidates.isEmpty()) {
            throw new RuntimeException("Не удалось найти подходящую подсказку.");
        }

        Random random = new Random();
        int index = random.nextInt(candidates.size());

        return candidates.get(index);
    }

}
