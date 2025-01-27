package com.javarush.kiryushkin.cryptoanalyzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {

    TextField textField = new TextField();
    Scanner scanner = new Scanner(System.in);
    Validator validator = new Validator();
    Encryptor encryptor = new Encryptor();

    public void begin() {
        boolean isOperationValid = false;
        while (!isOperationValid) {
            System.out.println(textField.WELCOME);
            System.out.println(textField.ENCRYPT_FILE);
            System.out.println(textField.DECRYPT_FILE);
            System.out.println(textField.ENCRYPT_STRING);
            System.out.println(textField.DECRYPT_STRING);
            System.out.println(textField.DECRYPT_FILE_BRUTEFORCE);
            System.out.println(textField.EXIT);
            String operation = scanner.nextLine();
            isOperationValid = true;
            switch (operation) {
                case "1":
                    encryptFileDialog(textField.FILE_ENCRYPTED);
                    break;
                case "2":
                    decryptFileDialog(textField.FILE_DECRYPTED);
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
                    System.out.println(textField.WRONG_OPERATION);
                    isOperationValid = false;
            }
        }
    }

    private void encryptFileDialog(String endMessage) {
        String fileNameForRead = inputFileForRead();
        int key = inputKey();
        if (endMessage.equals(textField.FILE_DECRYPTED)) {
            key = -key;
        }
        String fileNameForWrite = inputFileForWrite();
        try {
            encryptor.encryptFile(fileNameForRead, fileNameForWrite, key);
        } catch (IOException exception) {
            System.out.println(textField.CANT_CREATE_FILE);
            exception.printStackTrace();
        }
        System.out.println(endMessage);
        begin();
    }

    private void decryptFileDialog(String endMessage) {
        encryptFileDialog(endMessage);
    }

    private void encryptStringDialog() {
        System.out.println(textField.INPUT_STRING);
        String stringToEncrypt = scanner.nextLine();
        int key = inputKey();
        System.out.println(encryptor.encryptString(stringToEncrypt, key));
        begin();
    }

    private void decryptStringDialog() {
        System.out.println(textField.INPUT_STRING);
        String stringToEncrypt = scanner.nextLine();
        int key = inputKey();
        key = -key;
        System.out.println(encryptor.encryptString(stringToEncrypt, key));
        begin();
    }

    private void decryptFileBruteForceDialog() {
        String fileNameForRead = inputFileForRead();
        String fileNameForWrite = inputFileForWrite();
        String shortString = takeStringFromFile(fileNameForRead);
        Map<Integer, String> decryptedVariants = encryptor.decryptStringBruteForce(shortString);
        if (decryptedVariants.isEmpty()) {
            System.out.println(textField.CANT_DECRYPT_FILE);
        } else {
            for (Map.Entry<Integer, String> variant : decryptedVariants.entrySet()) {
                int key = variant.getKey();
                String value = variant.getValue().substring(0, 200);
                boolean isVariantFound = false;
                if (decryptedVariants.entrySet().size() == 1) {
                    System.out.printf(textField.ENCRYPTION_KEY, key);
                    try {
                        encryptor.encryptFile(fileNameForRead, fileNameForWrite, -key);
                    } catch (IOException exception) {
                        System.out.println(textField.CANT_CREATE_FILE);
                        exception.printStackTrace();
                    }
                    System.out.println(textField.FILE_DECRYPTED);
                } else {
                    System.out.println(textField.CAN_RECOGNIZE_TEXT);
                    System.out.println(value);
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
                                System.out.printf(textField.ENCRYPTION_KEY, key);
                                try {
                                    encryptor.encryptFile(fileNameForRead, fileNameForWrite, -key);
                                } catch (IOException exception) {
                                    System.out.println(textField.CANT_CREATE_FILE);
                                    exception.printStackTrace();
                                }
                                System.out.println(textField.FILE_DECRYPTED);
                                isVariantFound = true;
                                break;
                            default:
                                isAnswerValid = false;
                        }
                    }
                }
                if (isVariantFound) {
                break;
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
            System.out.println(textField.INPUT_FILE_NAME_FOR_READING);
            filename = scanner.nextLine();
            if (filename.equals("0")) {
                begin();
            } else {
                try {
                    validator.validateForRead(filename);
                } catch (InvalidPathException exception) {
                    System.out.println(textField.WRONG_FILE_NAME);
                    isPathValid = false;
                } catch (AccessDeniedException exception) {
                    System.out.println(textField.ACCESS_DENIED);
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
            System.out.println(textField.INPUT_FILE_NAME_FOR_WRITING);
            filename = scanner.nextLine();
            if (filename.equals("0")) {
                begin();
            } else {
                try {
                    validator.validateForWrite(filename);
                } catch (InvalidPathException exception) {
                    System.out.println(textField.WRONG_FILE_NAME);
                    isPathValid = false;
                } catch (AccessDeniedException exception) {
                    System.out.println(textField.ACCESS_DENIED);
                    isPathValid = false;
                } catch (NoSuchFileException exception) {
                    Path path = Paths.get(filename);
                    System.out.printf(textField.CREATE_NEW_FILE, path.toAbsolutePath());
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
            System.out.println(textField.INPUT_KEY);
            String keyString = scanner.nextLine();
            try {
                key = Integer.parseInt(keyString);
            } catch (NumberFormatException exception) {
                System.out.println(textField.WRONG_KEY);
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
            while ((charAsInt = bufferedReader.read()) != -1 && stringBuilder.length() < 1000) {
                stringBuilder.append((char) charAsInt);
            }
        } catch (FileNotFoundException exception) {
            System.out.println(textField.WRONG_FILE_NAME);
            exception.printStackTrace();
        } catch (IOException exception) {
            System.out.println(textField.CANT_READ_FROM_FILE);
            exception.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
