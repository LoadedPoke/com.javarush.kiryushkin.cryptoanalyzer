package com.javarush.kiryushkin.cryptoanalyzer;

import java.nio.file.AccessDeniedException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Scanner;

public class UserInterface {

    private final String WELCOME = "Введите номер операции:";
    private final String ENCRYPT_FILE = "1. Зашифровать файл";
    private final String EXIT = "0. Выход из программы";
    private final String WRONG_OPERATION = "Неверный номер операции. Попробуйте ещё раз.";
    private final String INPUT_FILE_NAME = "Введите имя файла или \"0\" для выхода в меню:";
    private final String WRONG_FILE_NAME = "Файла с таким именем не существует, или это директория";
    private final String ACCESS_DENIED = "Директория или файл защищены от чтения и записи";

    public void begin() {
        System.out.println(WELCOME);
        System.out.println(ENCRYPT_FILE);
        System.out.println(EXIT);

        Scanner scanner = new Scanner(System.in);
        String operation = scanner.nextLine();
        switch (operation) {
            case "1":
                encryptFileDialog();
                break;
            case "0":
                return;
            default:
                System.out.println(WRONG_OPERATION);
                begin();
        }
    }

    private void encryptFileDialog() {
        System.out.println(INPUT_FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        if (filename.equals("0")) {
            begin();
        } else {
            validatePathToFile(filename);
        }
        //TODO: прописать запуск метода шифрования
    }

    private void validatePathToFile(String filename) {
        Validator validator = new Validator();
        try {
            validator.validateForWrite(filename);
        } catch (InvalidPathException exception) {
            System.out.println(WRONG_FILE_NAME);
            encryptFileDialog();
        } catch (AccessDeniedException exception) {
            System.out.println(ACCESS_DENIED);
            encryptFileDialog();
        }
    }
}
