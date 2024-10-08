package com.javarush.kiryushkin.cryptoanalyzer;

import java.util.HashMap;
import java.util.Map;

public class Cipher {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            '.', ',', '«', '»', '"', '\'', '/', ':', '!', '?', ' ',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    private final Map<Character, Character> charMapping;

    public Cipher(int key) {
        this.charMapping = createCharMapping(ALPHABET, key);
    }

    public Map<Character, Character> getCharMapping() {
        return charMapping;
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
        return charMapping.get(symbol);
    }
}
