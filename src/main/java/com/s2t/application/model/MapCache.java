package com.s2t.application.model;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class MapCache<K, V> implements Cache<K, V>{
    private final Map<K, V> map = new HashMap<>();

    private final Timer timer = new Timer();

    @Value("${cache.timeout}")
    private Long timeout;

    @Override
    public void addKey(K key, V value) {
        map.put(key, value);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                actionAfterTimeout(key);
            }
        }, timeout);
    }

    @Override
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    @Override
    public V getValue(K key) {
        return map.get(key);
    }

    @Override
    public void actionAfterTimeout(K key) {
        map.remove(key);
    }
}
