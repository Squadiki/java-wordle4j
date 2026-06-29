package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/*
главный класс:
    лог-файл
    создан загрузчик словарей WordleDictionaryLoader
    загружен словарь WordleDictionary с помощью класса WordleDictionaryLoader
    создана игра WordleGame и ей передан словарь
 */
public class Wordle {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (PrintWriter log = new PrintWriter("log.txt")) {

            try {
                WordleDictionaryLoader loader = new WordleDictionaryLoader();
                List<String> rawWords = loader.readFile("words_ru.txt");
                WordleDictionary dictionary = new WordleDictionary(rawWords);
                WordleGame game = new WordleGame(dictionary);

                while (!game.isFinished()) {
                    System.out.println("Введите слово:");
                    String input = scanner.nextLine();

                    if (input.isEmpty()) {
                        String hint = game.getHint();
                        System.out.println("Подсказка: " + hint);
                        input = hint;
                    }

                    try {
                        String stepResult = game.move(input);
                        System.out.println(stepResult);
                    } catch (WordLengthException | WordNotFoundInDictionaryException | WordFormatException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (game.isVictory()) {
                    System.out.println("Победа!");
                } else {
                    System.out.println("Попытки закончились.");
                }

                System.out.println("Загаданное слово: " + game.getAnswer());

            } catch (Exception e) {
                System.out.println("Произошла системная ошибка. Подробности записаны в log.txt.");
                e.printStackTrace(log);
            }

        } catch (Exception e) {
            System.out.println("Не удалось создать лог-файл.");
        }
    }
}