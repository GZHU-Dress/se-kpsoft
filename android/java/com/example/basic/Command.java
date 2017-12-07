package com.example.basic;

/**
 * Created by visn on 17-12-7.
 */

public interface Command<T> {
    T execute();
}
