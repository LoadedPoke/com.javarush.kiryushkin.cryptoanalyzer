package com.javarush.kiryushkin.cryptoanalyzer;

import java.nio.file.*;

public class Validator {

    private static final String[] NOT_ALLOWED_NAMES = new String[]{
            "Windows",
            "Program Files",
            "ProgramData",
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
    private static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();

    private Path validatePath(String filename) throws InvalidPathException, AccessDeniedException {

        for (String pathPart : filename.split(FILE_SEPARATOR)) {
            for (String notAllowedName : NOT_ALLOWED_NAMES) {
                if (notAllowedName.equals(pathPart)) {
                    throw new AccessDeniedException(filename);
                }
            }
        }
        Path path = Paths.get(filename);
        return path;
    }

    public void validateForWrite(String filename) throws InvalidPathException, AccessDeniedException {
        Path path = validatePath(filename);
        if (Files.notExists(path)) {
            throw new InvalidPathException(filename, "");
        }
        if (Files.isDirectory(path)) {
            throw new InvalidPathException(filename, "");
        }
        if (!Files.isReadable(path)) {
            throw new AccessDeniedException(filename);
        }
    }
}
