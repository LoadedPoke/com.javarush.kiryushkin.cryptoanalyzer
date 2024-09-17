package com.javarush.kiryushkin.cryptoanalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

    public void readTextFromFile(String filename) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(filename))
        ) {

        }
    }

}
