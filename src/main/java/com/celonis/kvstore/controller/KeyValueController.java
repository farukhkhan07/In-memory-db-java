package com.celonis.kvstore.controller;

import com.celonis.kvstore.kvPair.KVPair;
import com.celonis.kvstore.kvPair.KVStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;

@Controller
public class KeyValueController {

    private final KVStore kvStore = new KVStore(5);

    @GetMapping("/get/{key}")
    public String getKVPair(String key) {
        Optional<KVPair> kvPair = kvStore.get(key);
        return kvPair.get().getKey() + "," + kvPair.get().getValue();
    }

    @PutMapping("/update/{key}/{value}")
    public String addInCache(String key, String value) {
        kvStore.put(new KVPair(key,value));
        return "Added key in cache";
    }
}
