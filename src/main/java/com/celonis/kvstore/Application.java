package com.celonis.kvstore;


import com.celonis.kvstore.kvPair.KVPair;
import com.celonis.kvstore.kvPair.KVPersistentStorage;
import com.celonis.kvstore.kvPair.KVStore;

import java.io.FileNotFoundException;

public class Application {
    public static void main(String[] Args) throws FileNotFoundException {
        KVStore store = new KVStore(5L);
        KVPersistentStorage persistentStorage = new KVPersistentStorage();


        store.put(new KVPair("A", "1"));
        store.put(new KVPair("B", "1"));
        store.put(new KVPair("C", "1"));
        store.put(new KVPair("A", "2"));
        store.put(new KVPair("D", "1"));
        store.put(new KVPair("E", "1"));
        store.put(new KVPair("G", "1"));
        store.put(new KVPair("H", "1"));
        store.put(new KVPair("I", "1"));

        /**
         * Try to retrieve evicted key*/
        persistentStorage.retrieve("B");

    }
}