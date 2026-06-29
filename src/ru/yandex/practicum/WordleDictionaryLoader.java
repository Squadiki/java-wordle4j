package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;

//этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками

public class WordleDictionaryLoader {

    // метод по загрузке списка слов из файла по имени файла
    public List<String> readFile(String fileName) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        }
        return words;
    }
}
