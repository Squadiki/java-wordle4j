package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс содержит рутинные функции по сравнению слов и их нормализации
 */
public class WordleDictionary {
    private final List<String> words; // Список нормализованных слов (словарь)

    private final Random random = new Random();

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
            throw new EmptyDictionaryException("Словарь пуст.");
        }

        int index = random.nextInt(words.size());
        return words.get(index);
    }

    public List<String> getWords() {
        return new ArrayList<>(words);
    }


}