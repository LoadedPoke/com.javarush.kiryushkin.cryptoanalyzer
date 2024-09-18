package com.javarush.kiryushkin.cryptoanalyzer;

import java.io.*;
import java.util.Map;

public class Encryptor {

    public void EncryptFile(String inputFilename, String outputFilename, int key) throws IOException {
        Cipher cipher = new Cipher(key);
        Map<Character, Character> charMapping = cipher.getCharMapping();
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(inputFilename));
             BufferedWriter bufferedWriter = new BufferedWriter(
                     new FileWriter(outputFilename)))
        {
            int charAsInt;
            while ((charAsInt = bufferedReader.read()) != 0) {
                char symbol = (char) charAsInt;
                if (Character.toString(symbol).matches("\\s+")) {
                    symbol = ' ';
                }
                if (charMapping.containsKey(symbol)) {
                    if (Character.isLetter(symbol) && Character.isUpperCase(symbol)) {
                        symbol = Character.toLowerCase(symbol);
                    }
                    bufferedWriter.write(charMapping.get(symbol));
                }
            }
            bufferedWriter.flush();
        }
    }

}
