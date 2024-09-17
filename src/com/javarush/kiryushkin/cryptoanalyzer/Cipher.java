package com.javarush.kiryushkin.cryptoanalyzer;

import java.util.HashMap;
import java.util.Map;

public class Cipher {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            '.', ',', '«', '»', '"', '\'', '/', ':', '!', '?', ' ',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    private Map<Character, Character> сharMapping;

    public Cipher(int key) {
        this.сharMapping = createCharMapping(ALPHABET, key);
    }

    private Map<Character, Character> createCharMapping(char[] charArray, int shift) {
        if (shift < 0) {
            shift = charArray.length + shift % charArray.length;
        }
        Map<Character, Character> charMapping = new HashMap<>();
        for (int i = 0; i < charArray.length; i++) {
            Character key = charArray[i];
            Character value = charArray[(i + shift) % charArray.length];
            charMapping.put(key, value);
        }
        return charMapping;
    }

    public char encrypt(char symbol) {
        return сharMapping.get(symbol);
    }

}
