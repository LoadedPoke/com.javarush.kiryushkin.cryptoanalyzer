package com.javarush.kiryushkin.cryptoanalyzer;

import java.util.HashMap;
import java.util.Map;

public class Cipher {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};

    private Map<Character, Character> encryptCharMapping;
    private Map<Character, Character> decryptCharMapping;

    public Cipher(int key) {
        this.encryptCharMapping = createCharMapping(ALPHABET, key);
        this.decryptCharMapping = createCharMapping(ALPHABET, -key);
    }

    private Map<Character, Character> createCharMapping(char[] charArray, int shift) {
        Map<Character, Character> charMapping = new HashMap<>();
        if (shift < 0) {
            shift = -shift;
            char temp;
            for (int i = 0; i < charArray.length / 2; i++) {
                temp = charArray[charArray.length - 1 - i];
                charArray[charArray.length - 1 - i] = charArray[i];
                charArray[i] = temp;
            }
        }
        for (int i = 0; i < charArray.length; i++) {
            Character key = charArray[i];
            Character value = charArray[(i + shift) % charArray.length];
            charMapping.put(key, value);
        }
        return charMapping;
    }

    public char encrypt(char symbol) {
        return encryptCharMapping.get(symbol);
    }

    public char decrypt(char symbol) {
        return decryptCharMapping.get(symbol);
    }
}
