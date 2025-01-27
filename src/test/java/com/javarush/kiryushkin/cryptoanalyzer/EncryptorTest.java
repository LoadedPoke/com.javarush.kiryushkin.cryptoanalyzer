package com.javarush.kiryushkin.cryptoanalyzer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class EncryptorTest {

    static Encryptor encryptor;

    @BeforeAll
    static void setUpAll() {
        encryptor = new Encryptor();
    }

    @AfterAll
    static void tearDownAll() {
        encryptor = null;
    }

    @ParameterizedTest
    @CsvSource({
            "абв, 1, бвг",
            "123, 5, 678",
            "abc, 2, ''",
            "тест, -1, сдрс"
    })
    void parameterizedEncryptString(String stringToEncrypt, int key, String expected) {
        String result = encryptor.encryptString(stringToEncrypt, key);
        assertEquals(expected, result);
    }
}
