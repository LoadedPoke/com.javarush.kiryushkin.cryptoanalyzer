package com.javarush.kiryushkin.cryptoanalyzer;

import java.nio.file.*;

public class Validator {

    private static final String[] NOT_ALLOWED_NAMES = new String[]{
            "windows",
            "program files",
            "programdata",
            ".bash_history",
            ".bash_profile",
            "etc",
            "proc",
            "bin",
            "sbin",
            "boot",
            "dev",
            "usr",
            "var"
    };
    private static final String FILE_SEPARATOR = "[\\\\/]";

    private Path validatePath(String filename) throws InvalidPathException, AccessDeniedException {
        filename = filename.toLowerCase();
        for (String pathPart : filename.split(FILE_SEPARATOR)) {
            for (String notAllowedName : NOT_ALLOWED_NAMES) {
                if (notAllowedName.equals(pathPart)) {
                    throw new AccessDeniedException(filename);
                }
            }
        }
        return Paths.get(filename);
    }

    public Path validateForWrite(String filename) throws InvalidPathException, AccessDeniedException {
        Path path = validatePath(filename);
        if (Files.notExists(path) || Files.isDirectory(path)) {
            throw new InvalidPathException(filename, "");
        }
        if (!Files.isReadable(path)) {
            throw new AccessDeniedException(filename);
        }
        return path;
    }
}
