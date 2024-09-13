package com.javarush.kiryushkin.cryptoanalyzer;

import java.util.Scanner;

public class UserInterface {

    private static final String WELCOME = "Введите номер операции";
    private static final String ENCRYPT_FILE = "1. Зашифровать файл";
    private static final String EXIT = "0. Выход из программы";

    public void begin() {
        System.out.println(WELCOME);
        System.out.println(ENCRYPT_FILE);
        System.out.println(EXIT);

        Scanner scanner = new Scanner(System.in);
        String operation = scanner.nextLine();
        switch (operation) {
            case "1":
                System.out.println("Шифруем файл");
                break;
            case "0":
                return;
            default:
                System.out.println("Неверный номер операции. Попробуйте ещё раз.");
                begin();
        }
    }

}
