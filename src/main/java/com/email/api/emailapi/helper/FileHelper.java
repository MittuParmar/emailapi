package com.email.api.emailapi.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FileHelper {

    @Autowired
    private ResourceLoader resourceLoader;

    private static final String STATIC_FOLDER_PATH = "src/main/resources/static/";

    public List<String> readFile(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/" + fileName);
        try {
            return Files.readAllLines(Paths.get(resource.getURI()));
        } catch (IOException e) {
            throw new IOException("Error reading file", e);
        }
    }

    public void writeFile(String fileName, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STATIC_FOLDER_PATH + fileName, true))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public boolean emailExists(String fileName, String email) {
        List<String> lines = null;
        try {
            lines = readFile(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Set<String> emailSet = new HashSet<>(lines);
        return emailSet.contains(email);
    }

    public synchronized void addEmail(String fileName, String email) {
        if (!emailExists(fileName, email)) {
            try {
                writeFile(fileName, List.of(email));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}