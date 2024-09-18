package com.javarush.kiryushkin.cryptoanalyzer;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class UserInterface {

    private final String WELCOME = "Введите номер операции:";
    private final String ENCRYPT_FILE = "1. Зашифровать файл";
    private final String EXIT = "0. Выход из программы";
    private final String WRONG_OPERATION = "Неверный номер операции. Попробуйте ещё раз.";
    private final String INPUT_FILE_NAME_FOR_READING = "Введите имя файла для чтения или \"0\" для выхода в меню:";
    private final String WRONG_FILE_NAME = "Файла с таким именем не существует, или это директория.";
    private final String ACCESS_DENIED = "Директория или файл защищены от чтения и записи.";
    private final String INPUT_KEY = "Введите ключ - целое число или \"0\" для выхода в меню:";
    private final String WRONG_KEY = "Ключ должен быть числом.";
    private final String INPUT_FILE_NAME_FOR_WRITING = "Введите имя файла для записи или \"0\" для выхода в меню:";
    private final String CREATE_NEW_FILE = "Файла с именем %s не существует. Создать файл? y/n.\n";
    private final String CANT_CREATE_FILE = "Не удалось создать файл.";

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
        String fileNameForRead = inputFileForRead();
        int key = inputKey();
        String fileNameForWrite = inputFileForWrite();

    }

    private String inputFileForRead() {
        String filename = null;
        boolean isPathValid = false;
        while (!isPathValid) {
            isPathValid = true;
            System.out.println(INPUT_FILE_NAME_FOR_READING);
            filename = scanner.nextLine();
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
        return filename;
    }

    private String inputFileForWrite() {
        String filename = null;
        boolean isPathValid = false;
        while (!isPathValid) {
            isPathValid = true;
            System.out.println(INPUT_FILE_NAME_FOR_WRITING);
            filename = scanner.nextLine();
            if (filename.equals("0")) {
                begin();
            } else {
                try {
                    validator.validateForWrite(filename);
                } catch (InvalidPathException exception) {
                    System.out.println(WRONG_FILE_NAME);
                    isPathValid = false;
                } catch (AccessDeniedException exception) {
                    System.out.println(ACCESS_DENIED);
                    isPathValid = false;
                } catch (NoSuchFileException exception) {
                    Path path = Paths.get(filename);
                    System.out.printf(CREATE_NEW_FILE, path.toAbsolutePath());
                    boolean isAnswerValid = false;
                    while (!isAnswerValid) {
                        isAnswerValid = true;
                        String answer = scanner.nextLine();
                        answer = answer.toLowerCase();
                        switch (answer) {
                            case "n":
                            case "н":
                                isPathValid = false;
                                break;
                            case "y":
                            case "д":
                                break;
                            default:
                                isAnswerValid = false;
                        }
                    }
                }
            }
        }
        return filename;
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
