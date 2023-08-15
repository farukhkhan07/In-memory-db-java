package com.memory.kvstore.kvPair;

import java.io.*;
import java.util.Optional;

public class KVPersistentStorage {

    private static final String STORAGE_DIRECTORY = "evictedKeys";

    public KVPersistentStorage() {
        File directory = new File(STORAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Get file by key.
     * Read value from file.
     * return as KVPair
     */
    public Optional<KVPair> retrieve(String key) throws FileNotFoundException {
        String file = getFilePath(key);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String value = reader.readLine();
            if (value != null) {
                System.out.println("Key:" + key + " found in persistent storage. Path:" + file);
                return Optional.of(new KVPair(key, value));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /**
     * Write value of kvPair to file as txt. [Example: evictedKeys/{key}.txt]
     */
    public String store(KVPair kvPair) throws IOException {
        String key = kvPair.getKey();
        String value = kvPair.getValue();
        String file = getFilePath(key);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("KEY,VALUE");
            writer.newLine();
            writer.write(key + "," + value);
        } catch (IOException ioException) {
            throw new IOException("Exception:", ioException);
        }
        return key + "has been stored in disk";
    }

    private String getFilePath(String key) {
        return STORAGE_DIRECTORY + File.separator + key + ".csv";
    }

    void delete(String key) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
