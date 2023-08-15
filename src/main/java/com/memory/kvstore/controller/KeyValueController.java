package com.memory.kvstore.controller;

import com.memory.kvstore.exceptions.KeyNotFoundException;
import com.memory.kvstore.kvPair.KVPair;
import com.memory.kvstore.kvPair.KVStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Additional Task: KeyValueController
 * Scenario in which the KVStore can be used: Shopping cart
 */

@RestController
@RequestMapping("/api/cart")
public class KeyValueController {

    private final KVStore kvStore;

    public KeyValueController(KVStore kvStore) {
        this.kvStore = kvStore;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<KVPair> getCart(@PathVariable String userId) {
        try {
            KVPair kvPair = kvStore.get(userId).orElseThrow(() -> new KeyNotFoundException("Key not found for user:" + userId));
            return ResponseEntity.ok(kvPair);
        } catch (KeyNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> addToCart(@PathVariable String userId, @RequestBody String productInfo) {
        // Assuming productInfo is something like productId:quantity
        String[] productParts = productInfo.split(":");

        if (productParts.length != 2) {
            return ResponseEntity.badRequest().build();
        }

        String product = productParts[0];
        String quantity = productParts[1];

        kvStore.put(new KVPair(userId + "_" + product, quantity));

        return ResponseEntity.status(HttpStatus.CREATED).body("Added to cart:" + productInfo);
    }
}
