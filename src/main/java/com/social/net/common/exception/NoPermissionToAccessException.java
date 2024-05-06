package com.social.net.common.exception;

public class NoPermissionToAccessException extends RuntimeException {
    public NoPermissionToAccessException(String message) {
        super(message);
    }

    public NoPermissionToAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
