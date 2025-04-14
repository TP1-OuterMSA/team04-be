package com.example.crawler.common.utils;

@FunctionalInterface
public interface CheckedSupplier<T> {
    T get() throws Exception;
}
