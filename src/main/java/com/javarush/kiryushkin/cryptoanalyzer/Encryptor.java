package com.javarush.kiryushkin.cryptoanalyzer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Encryptor {

    public void encryptFile(String inputFilename, String outputFilename, int key) throws IOException {
        Path pathToOutputFile = Paths.get(outputFilename);
        if (Files.notExists(pathToOutputFile)) {
            Files.createFile(pathToOutputFile);
        }
        Cipher cipher = new Cipher(key);
        Map<Character, Character> charMapping = cipher.getCharMapping();
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(inputFilename));
             BufferedWriter bufferedWriter = new BufferedWriter(
                     new FileWriter(outputFilename)))
        {
            int charAsInt;
            while ((charAsInt = bufferedReader.read()) != -1) {
                char symbol = (char) charAsInt;
                if (Character.toString(symbol).matches("\\s+")) {
                    symbol = ' ';
                }
                if (Character.isLetter(symbol) && Character.isUpperCase(symbol)) {
                    symbol = Character.toLowerCase(symbol);
                }
                if (charMapping.containsKey(symbol)) {
                    bufferedWriter.write(charMapping.get(symbol));
                }
            }
            bufferedWriter.flush();
        }
    }

    public String encryptString(String stringToEncrypt, int key) {
        StringBuilder encryptedString = new StringBuilder();
        Cipher cipher = new Cipher(key);
        Map<Character, Character> charMapping = cipher.getCharMapping();
        stringToEncrypt = stringToEncrypt.toLowerCase();
        for (int i = 0; i < stringToEncrypt.length(); i++) {
            char symbol = stringToEncrypt.charAt(i);
            if (charMapping.containsKey(symbol)) {
                encryptedString.append(charMapping.get(symbol));
            }
        }
        return encryptedString.toString();
    }

    public Map<Integer, String> decryptStringBruteForce(String shortString) {
        Map<Integer, String> decryptedVariants = new HashMap<>();
        Cipher cipher = new Cipher(0);
        int possibleVariants = cipher.getCharMapping().size();
        for (int i = 1; i <= possibleVariants; i++) {
            String variant = encryptString(shortString, -i);
            if (variant.contains(", ") && variant.contains(". ")) {
                decryptedVariants.put(i, variant);
            }
        }
        return decryptedVariants;
    }

}
