package com.planetbooks.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootDir = Paths.get("uploads/books");

    @Override
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            String filename = System.currentTimeMillis() + "_" +
                    StringUtils.cleanPath(file.getOriginalFilename());

            // Create directory if it does not exist
            if (!Files.exists(rootDir)) {
                Files.createDirectories(rootDir);
            }

            // save file
            Files.copy(file.getInputStream(), this.rootDir.resolve(filename));

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Error saving file: " + e.getMessage());
        }
    }
}
