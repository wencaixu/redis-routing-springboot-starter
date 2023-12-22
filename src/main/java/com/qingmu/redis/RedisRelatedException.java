package com.qingmu.redis;

public class RedisRelatedException extends RuntimeException {
    public RedisRelatedException() {
        super();
    }

    public RedisRelatedException(String message) {
        super(message);
    }

    public RedisRelatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisRelatedException(Throwable cause) {
        super(cause);
    }

    protected RedisRelatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
