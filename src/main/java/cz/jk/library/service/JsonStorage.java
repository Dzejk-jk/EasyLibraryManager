package cz.jk.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cz.jk.library.model.LibraryData;

import java.io.File;
import java.io.IOException;

public class JsonStorage {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    private static final File FILE = new File(System.getProperty("user.home") + "/library.json");

    public static void save(LibraryData data) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(FILE, data);
        } catch (IOException e) {
            System.out.println("Chyba při ukládání: " + e.getMessage());
        }
    }

    public static LibraryData load() {
        if (!FILE.exists())
            return new LibraryData();
        try {
            return mapper.readValue(FILE, LibraryData.class);
        } catch (IOException e) {
            System.out.println("Chyba při načítání: " + e.getMessage());
            return new LibraryData();
        }
    }
}
