package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WordleGame {

    private final String answer; // Загаданное слово

    private int steps; // Шаги

    private final int MAX_STEPS = 6; // Максимум шагов

    private final String TRUE_WORD = "+++++"; // Верное слово

    private final WordleDictionary dictionary; // Словарь

    private boolean finished; // Закончена ли игра

    private boolean victory; // Победил ли пользователь

    private final List<MoveResult> previousMoves; // Успешные ходы

    private final Random random = new Random();

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

    // Нормализация слова
    private String normalize(String word) {
        return word.trim().toLowerCase().replace('ё', 'е');
    }

    // Метод проверки того, содержит ли слово только русские буквы
    private boolean containsOnlyRussianLetters(String word) {
        return word.matches("[а-я]+");
    }

    // Метод для проверки введённого слова
    public String validateWord(String word) throws WordLengthException, WordFormatException {
        String normalizedWord = normalize(word);

        if (normalizedWord.length() != 5) {
            throw new WordLengthException("Неподходящее количество символов.");
        }

        if (!containsOnlyRussianLetters(normalizedWord)) {
            throw new WordFormatException("Слово должно состоять только из русских букв.");
        }

        return normalizedWord;
    }

    // Метод сравнения загаданного и ввёденного слова
    public String checkResult(String wordAnswer, String wordInput) throws WordLengthException {
        wordAnswer = normalize(wordAnswer);
        wordInput = normalize(wordInput);

        if (wordAnswer.length() != wordInput.length()) {
            throw new WordLengthException("Неподходящее количество символов.");
        }

        if (wordAnswer.equals(wordInput)) {
            return "+++++";
        }

        char[] result = {'-', '-', '-', '-', '-'};
        boolean[] usedAnswerLetters = new boolean[wordAnswer.length()];

        for (int i = 0; i < wordAnswer.length(); i++) {
            if (wordAnswer.charAt(i) == wordInput.charAt(i)) {
                result[i] = '+';
                usedAnswerLetters[i] = true;
            }
        }

        for (int i = 0; i < wordAnswer.length(); i++) {
            if (result[i] != '+') {
                for (int j = 0; j < wordAnswer.length(); j++) {
                    if (!usedAnswerLetters[j] && wordInput.charAt(i) == wordAnswer.charAt(j)) {
                        result[i] = '^';
                        usedAnswerLetters[j] = true;
                        break;
                    }
                }
            }
        }

        return new String(result);
    }

    // Анализ совпадения слова с ответом
    public String move(String input) throws WordLengthException,
            WordNotFoundInDictionaryException, WordFormatException {

        if (finished) {
            throw new GameIsFinishedException("Игра окончена.");
        }

        String normalizedInput = validateWord(input);

        if (!dictionary.contains(normalizedInput)) {
            throw new WordNotFoundInDictionaryException("Данное слово неизвестно.");
        }

        String result = checkResult(answer, input);

        steps++;

        previousMoves.add(new MoveResult(input, result));

        if (result.equals(TRUE_WORD)) {
            finished = true;
            victory = true;
        } else if (steps >= MAX_STEPS) {
            finished = true;
        }

        return result;
    }

    // Слово-подсказка с учётом всего, что вводил пользователь ранее
    public String getHint() throws WordLengthException {
        if (finished) {
            throw new GameIsFinishedException("Игра уже окончена.");
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

                String candidateResult = checkResult(candidate, previousWord);

                if (!candidateResult.equals(previousResult)) {
                    suitable = false;
                    break;
                }
            }

            if (suitable) {
                candidates.add(candidate);
            }
        }

        if (candidates.isEmpty()) {
            throw new HintNotFoundException("Не удалось найти подходящую подсказку.");
        }

        int index = random.nextInt(candidates.size());

        return candidates.get(index);
    }

}
