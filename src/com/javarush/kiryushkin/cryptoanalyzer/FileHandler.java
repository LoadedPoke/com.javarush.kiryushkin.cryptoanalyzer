package com.javarush.kiryushkin.cryptoanalyzer;

import java.nio.file.AccessDeniedException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class FileHandler {

    public void readFileName() throws InvalidPathException, AccessDeniedException {
        Scanner scanner = new Scanner(System.in);
        Validator validator = new Validator();
        String fileName = scanner.nextLine();
        validator.validateForWrite(fileName);
    }

}
