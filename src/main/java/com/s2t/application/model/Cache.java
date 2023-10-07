package com.s2t.application.model;

public interface Cache<K, V> {
    void addKey(K key, V value);

    boolean containsKey(K key);

    V getValue(K key);

    void actionAfterTimeout(K key);
}
