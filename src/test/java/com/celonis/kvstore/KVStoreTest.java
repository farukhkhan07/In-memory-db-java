package com.celonis.kvstore;

import com.celonis.kvstore.kvPair.KVPair;
import com.celonis.kvstore.kvPair.KVStore;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class KVStoreTest {


    @Test
    public void aKeyStoredShouldBeAbleToBeRetrieved() {
        KVStore store = new KVStore(5);
        store.put(new KVPair("A", "1"));
        Optional<KVPair> retrievedPair = store.get("A");
        assertFalse(retrievedPair.isEmpty());
        assertEquals(new KVPair("A", "1"), retrievedPair.get());
    }

    @Test
    public void shouldEvictLeastRecentlyUsedKey() {
        KVStore store = new KVStore(5);

        givenCacheSizeExceeds(store);
        //when
        Optional<KVPair> checkLeastRecentlyUsedKey = store.get("A");
        //then
        assertFalse(checkLeastRecentlyUsedKey.isPresent());
    }

    private static void givenCacheSizeExceeds(KVStore store) {
        store.put(new KVPair("A", "1"));
        store.put(new KVPair("B", "2"));
        store.put(new KVPair("C", "3"));
        store.put(new KVPair("D", "4"));
        store.put(new KVPair("E", "5"));
        store.put(new KVPair("F", "2"));
    }
}
