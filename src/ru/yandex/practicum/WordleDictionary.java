package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов и их нормализации
 */
public class WordleDictionary {
    private List<String> words; // Список нормализованных слов (словарь)

    public WordleDictionary(List<String> words) {
        this.words = new ArrayList<>();

        for (String word : words) {
            addWord(word);
        }
    }

    // Нормализация слова
    private String normalize(String word) {
        return word.trim().toLowerCase().replace('ё', 'е');
    }

    // Добавление слова в список
    public void addWord(String word) {
        String normalizedWord = normalize(word);

        if (normalizedWord.length() == 5) {
            words.add(normalizedWord);
        }
    }

    // Метод проверки содержания слова в списке
    public boolean contains(String word) {
        String normalizedWord = normalize(word);
        return words.contains(normalizedWord);
    }

    // Получение слова, которе будет ответом в игре
    public String getRandomWord() {
        if (words.isEmpty()) {
            throw new RuntimeException("Словарь пуст.");
        }

        Random random = new Random();
        int index = random.nextInt(words.size());
        return words.get(index);
    }

    public int size() {
        return words.size();
    }

    public List<String> getWords() {
        return new ArrayList<>(words);
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

    // Метод сравнения результат и ввсёденного слова
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

}