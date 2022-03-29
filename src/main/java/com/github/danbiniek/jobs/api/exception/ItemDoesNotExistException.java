package com.github.danbiniek.jobs.api.exception;

public class ItemDoesNotExistException extends RuntimeException {

    public ItemDoesNotExistException(String message) {
        super(message);
    }
}
