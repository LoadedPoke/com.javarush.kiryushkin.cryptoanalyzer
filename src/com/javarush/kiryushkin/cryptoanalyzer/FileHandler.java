package com.javarush.kiryushkin.cryptoanalyzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileHandler {

    public void readTextFromFile(String filename) throws FileNotFoundException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

    }

}
