package com.javarush.kiryushkin.cryptoanalyzer;

public class TextField {

    public final String WELCOME = "Введите номер операции:";
    public final String ENCRYPT_FILE = "1. Зашифровать файл";
    public final String DECRYPT_FILE = "2. Расшифровать файл";
    public final String ENCRYPT_STRING = "3. Зашифровать строку";
    public final String DECRYPT_STRING = "4. Расшифровать строку";
    public final String DECRYPT_FILE_BRUTEFORCE = "5. Расшифровать файл с помощью Brute Force";
    public final String EXIT = "0. Выход из программы";
    public final String WRONG_OPERATION = "Неверный номер операции. Попробуйте ещё раз.";
    public final String INPUT_FILE_NAME_FOR_READING = "Введите имя файла для чтения или \"0\" для выхода в меню:";
    public final String WRONG_FILE_NAME = "Файла с таким именем не существует, или это директория.";
    public final String ACCESS_DENIED = "Директория или файл защищены от чтения и записи.";
    public final String INPUT_KEY = "Введите ключ - целое число или \"0\" для выхода в меню:";
    public final String WRONG_KEY = "Ключ должен быть числом.";
    public final String INPUT_FILE_NAME_FOR_WRITING = "Введите имя файла для записи или \"0\" для выхода в меню:";
    public final String CREATE_NEW_FILE = "Файла с именем %s не существует. Создать файл? y/n.\n";
    public final String CANT_CREATE_FILE = "Не удалось создать файл.";
    public final String FILE_ENCRYPTED = "Файл зашифрован.";
    public final String FILE_DECRYPTED = "Файл расшифрован";
    public final String INPUT_STRING = "Введите строку кириллицей:";
    public final String CANT_READ_FROM_FILE = "Не удалось прочитать файл.";
    public final String CANT_DECRYPT_FILE = "Файл расшифровать не удалось.";
    public final String ENCRYPTION_KEY = "Ключ шифрования \"%d\".\n";
    public final String CAN_RECOGNIZE_TEXT = "Вы можете распознать текст? y/n.";

}
