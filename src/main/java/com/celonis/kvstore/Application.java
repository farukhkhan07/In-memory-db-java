package com.celonis.kvstore;


import com.celonis.kvstore.kvPair.KVStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Bean
    public KVStore kvStore() {
        return new KVStore(5L);
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}