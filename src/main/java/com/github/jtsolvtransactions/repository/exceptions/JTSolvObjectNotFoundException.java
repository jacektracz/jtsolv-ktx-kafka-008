package com.github.jtsolvtransactions.repository.exceptions;

public class JTSolvObjectNotFoundException extends RuntimeException {

    public JTSolvObjectNotFoundException(Object key, String storeName) {
        super("Object not found in store %s for key %s".formatted(storeName, key.toString()));
    }
}
