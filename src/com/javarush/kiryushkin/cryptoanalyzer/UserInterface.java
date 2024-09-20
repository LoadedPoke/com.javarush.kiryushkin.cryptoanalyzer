package com.javarush.kiryushkin.cryptoanalyzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {

    private final String WELCOME = "Введите номер операции:";
    private final String ENCRYPT_FILE = "1. Зашифровать файл";
    private final String DECRYPT_FILE = "2. Расшифровать файл";
    private final String ENCRYPT_STRING = "3. Зашифровать строку";
    private final String DECRYPT_STRING = "4. Расшифровать строку";
    private final String DECRYPT_FILE_BRUTEFORCE = "5. Расшифровать файл с помощью Brute Force";
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
    private final String FILE_ENCRYPTED = "Файл зашифрован.";
    private final String FILE_DECRYPTED = "Файл расшифрован";
    private final String INPUT_STRING = "Введите строку кириллицей:";
    private final String CANT_READ_FROM_FILE = "Не удалось прочитать файл.";
    private final String CANT_DECRYPT_FILE = "Файл расшифровать не удалось.";
    private final String ENCRYPTION_KEY = "Ключ шифрования \"%d\".\n";
    private final String CAN_RECOGNIZE_TEXT = "Вы можете распознать текст? y/n.";

    Scanner scanner = new Scanner(System.in);
    Validator validator = new Validator();
    Encryptor encryptor = new Encryptor();

    public void begin() {
        boolean isOperationValid = false;
        while (!isOperationValid) {
            System.out.println(WELCOME);
            System.out.println(ENCRYPT_FILE);
            System.out.println(DECRYPT_FILE);
            System.out.println(ENCRYPT_STRING);
            System.out.println(DECRYPT_STRING);
            System.out.println(DECRYPT_FILE_BRUTEFORCE);
            System.out.println(EXIT);
            String operation = scanner.nextLine();
            isOperationValid = true;
            switch (operation) {
                case "1":
                    encryptFileDialog(FILE_ENCRYPTED);
                    break;
                case "2":
                    decryptFileDialog(FILE_DECRYPTED);
                    break;
                case "3":
                    encryptStringDialog();
                    break;
                case "4":
                    decryptStringDialog();
                    break;
                case "5":
                    decryptFileBruteForceDialog();
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println(WRONG_OPERATION);
                    isOperationValid = false;
            }
        }
    }

    private void encryptFileDialog(String endMessage) {
        String fileNameForRead = inputFileForRead();
        int key = inputKey();
        if (endMessage.equals(FILE_DECRYPTED)) {
            key = -key;
        }
        String fileNameForWrite = inputFileForWrite();
        try {
            encryptor.encryptFile(fileNameForRead, fileNameForWrite, key);
        } catch (IOException exception) {
            System.out.println(CANT_CREATE_FILE);
            exception.printStackTrace();
        }
        System.out.println(endMessage);
        begin();
    }

    private void decryptFileDialog(String endMessage) {
        encryptFileDialog(endMessage);
    }

    private void encryptStringDialog() {
        System.out.println(INPUT_STRING);
        String stringToEncrypt = scanner.nextLine();
        int key = inputKey();
        System.out.println(encryptor.encryptString(stringToEncrypt, key));
        begin();
    }

    private void decryptStringDialog() {
        System.out.println(INPUT_STRING);
        String stringToEncrypt = scanner.nextLine();
        int key = inputKey();
        key = -key;
        encryptor.encryptString(stringToEncrypt, key);
        begin();
    }

    private void decryptFileBruteForceDialog() {
        String fileNameForRead = inputFileForRead();
        String fileNameForWrite = inputFileForWrite();
        String shortString = takeStringFromFile(fileNameForRead);
        Map<Integer, String> decryptedVariants = encryptor.decryptStringBruteForce(shortString);
        if (decryptedVariants.isEmpty()) {
            System.out.println(CANT_DECRYPT_FILE);
        } else {
            for (Map.Entry<Integer, String> variant : decryptedVariants.entrySet()) {
                int key = variant.getKey();
                if (decryptedVariants.entrySet().size() == 1) {
                    System.out.printf(ENCRYPTION_KEY, key);
                    try {
                        encryptor.encryptFile(fileNameForRead, fileNameForWrite, -key);
                    } catch (IOException exception) {
                        System.out.println(CANT_CREATE_FILE);
                        exception.printStackTrace();
                    }
                    System.out.println(FILE_DECRYPTED);
                } else {
                    System.out.println(CAN_RECOGNIZE_TEXT);
                    System.out.println(variant.getValue().substring(0, 200));
                    boolean isAnswerValid = false;
                    while (!isAnswerValid) {
                        isAnswerValid = true;
                        String answer = scanner.nextLine();
                        answer = answer.toLowerCase();
                        switch (answer) {
                            case "n":
                            case "н":
                                break;
                            case "y":
                            case "д":
                                System.out.printf(ENCRYPTION_KEY, key);
                                try {
                                    encryptor.encryptFile(fileNameForRead, fileNameForWrite, -key);
                                } catch (IOException exception) {
                                    System.out.println(CANT_CREATE_FILE);
                                    exception.printStackTrace();
                                }
                                System.out.println(FILE_DECRYPTED);
                                break;
                            default:
                                isAnswerValid = false;
                        }
                    }
                }
            }
        }
        begin();
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

    private String takeStringFromFile(String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(filename))) {
            int charAsInt;
            while ((charAsInt = bufferedReader.read()) != -1 || stringBuilder.length() < 1000) {
                stringBuilder.append((char) charAsInt);
            }
        } catch (FileNotFoundException exception) {
            System.out.println(WRONG_FILE_NAME);
            exception.printStackTrace();
        } catch (IOException exception) {
            System.out.println(CANT_READ_FROM_FILE);
            exception.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
