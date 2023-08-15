package com.memory.kvstore.kvPair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class KVStore {

    private final long maxSize;
    private final ConcurrentHashMap<String, KVPair> cache;
    private final LinkedBlockingDeque<String> queue;
    private final KVPersistentStorage kvPersistentStorage = new KVPersistentStorage();

    public KVStore(long maxSize) {
        this.maxSize = maxSize;
        this.cache = new ConcurrentHashMap<>((int) maxSize);
        this.queue = new LinkedBlockingDeque<>((int) maxSize);
    }

    public void put(KVPair kvPair) {
        try {
            cache.put(kvPair.getKey(), kvPair);
            addToQueue(kvPair.getKey());
            evictIfNeeded();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void delete(String key) {
        try {
            cache.remove(key);
            removeFromQueue(key);
        } catch (UnsupportedOperationException exception) {
            throw new UnsupportedOperationException("Not implemented yet");
        }
    }

    public Optional<KVPair> get(String key) {
        try {
            KVPair kvPair = cache.get(key);
            if (kvPair == null) {
                System.out.println("Key: " + key + " not available in cache. Checking storage...");
                kvPersistentStorage.retrieve(key);
            }
            addToQueue(key);
            return Optional.ofNullable(kvPair);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes if the key already existed.
     * Add the recently used key at the end of deque.
     */
    private void addToQueue(String key) {
        queue.remove(key);
        queue.offer(key);
    }

    /**
     * Use deque.poll to get the first element i.e. Least Recently Used.
     * Remove the LRU key from cache.
     * Store it in file system.
     */
    private void evictIfNeeded() throws IOException {
        while (cache.size() > maxSize) {
            String leastRecentUsedKey = queue.poll();
            if (leastRecentUsedKey != null) {
                KVPair removedKey = cache.remove(leastRecentUsedKey);
                System.out.println("Removed Key: " + leastRecentUsedKey);
                kvPersistentStorage.store(removedKey);
            }
        }
    }

    private void removeFromQueue(String key) {
        queue.remove(key);
    }
}
