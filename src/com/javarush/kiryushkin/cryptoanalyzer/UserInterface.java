package com.javarush.kiryushkin.cryptoanalyzer;

import java.nio.file.AccessDeniedException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class UserInterface {

    private final String WELCOME = "Введите номер операции:";
    private final String ENCRYPT_FILE = "1. Зашифровать файл";
    private final String EXIT = "0. Выход из программы";
    private final String WRONG_OPERATION = "Неверный номер операции. Попробуйте ещё раз.";
    private final String INPUT_FILE_NAME = "Введите имя файла или \"0\" для выхода в меню:";
    private final String WRONG_FILE_NAME = "Файла с таким именем не существует, или это директория";
    private final String ACCESS_DENIED = "Директория или файл защищены от чтения и записи";
    private final String INPUT_KEY = "Введите ключ - целое число или \"0\" для выхода в меню:";
    private final String WRONG_KEY = "Ключ должен быть числом.";

    Scanner scanner = new Scanner(System.in);
    Validator validator = new Validator();


    public void begin() {
        boolean isOperationValid = false;
        while (!isOperationValid) {
            System.out.println(WELCOME);
            System.out.println(ENCRYPT_FILE);
            System.out.println(EXIT);
            String operation = scanner.nextLine();
            isOperationValid = true;
            switch (operation) {
                case "1":
                    encryptFileDialog();
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println(WRONG_OPERATION);
                    isOperationValid = false;
            }
        }
    }

    private void encryptFileDialog() {
        boolean isPathValid = false;
        while (!isPathValid) {
            isPathValid = true;
            System.out.println(INPUT_FILE_NAME);
            String filename = scanner.nextLine();
            if (filename.equals("0")) {
                begin();
            } else {
                try {
                    validator.validateForRead(filename);
                } catch (InvalidPathException exception) {
                    System.out.println(WRONG_FILE_NAME);
                    isPathValid = false;
                } catch (AccessDeniedException exception) {
                    System.out.println(ACCESS_DENIED);
                    isPathValid = false;
                }
            }
        }
        int key = inputKey();
        //TODO: прописать запуск метода шифрования
    }

    private int inputKey() {
        int key = 0;
        boolean isKeyValid = false;
        while (!isKeyValid) {
            isKeyValid = true;
            System.out.println(INPUT_KEY);
            String keyString = scanner.nextLine();
            try {
                key = Integer.parseInt(keyString);
            } catch (NumberFormatException exception) {
                System.out.println(WRONG_KEY);
                isKeyValid = false;
            }
        }
        if (key == 0) {
            begin();
        }
        return key;
    }
}
