package com.example.crawler.common.utils;

import lombok.Getter;

import java.util.function.Supplier;

public class Try<T> {
    private final T result;
    @Getter
    private final Exception exception;

    private Try(T result, Exception exception) {
        this.result = result;
        this.exception = exception;
    }

    public static <T> Try<T> of(CheckedSupplier<T> supplier) {
        try {
            return new Try<>(supplier.get(), null);
        } catch (Exception e) {
            return new Try<>(null, e);
        }
    }

    public <R> Try<R> map(java.util.function.Function<T, R> mapper) {
        if (exception != null) {
            return new Try<>(null, exception);
        }
        return new Try<>(mapper.apply(result), null);
    }

    public T orElse(T defaultValue) {
        return exception == null ? result : defaultValue;
    }

    public boolean isSuccess() {
        return exception == null;
    }

}
