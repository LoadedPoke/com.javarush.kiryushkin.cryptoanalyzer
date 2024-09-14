package com.javarush.kiryushkin.cryptoanalyzer;

import java.nio.file.AccessDeniedException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class UserInterface {

    private final String WELCOME = "Введите номер операции:";
    private final String ENCRYPT_FILE = "1. Зашифровать файл";
    private final String EXIT = "0. Выход из программы";
    private final String WRONG_OPERATION = "Неверный номер операции. Попробуйте ещё раз.";
    private final String INPUT_FILE_NAME = "Введите имя файла:";
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

    public void encryptFileDialog() {
        System.out.println(INPUT_FILE_NAME);
        FileHandler fileHandler = new FileHandler();
        try {
            fileHandler.readFileName();
        } catch (InvalidPathException exception) {
            System.out.println(WRONG_FILE_NAME);
        } catch (AccessDeniedException exception) {
            System.out.println(ACCESS_DENIED);
        } finally {
            encryptFileDialog();
        }
        //TODO: прописать запуск метода шифрования
    }

}
