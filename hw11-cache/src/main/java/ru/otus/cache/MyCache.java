package ru.otus.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы

    private List<HwListener<K, V>> listenerList;

    private Map<K, V> cache;

    public MyCache() {
        this.cache = new WeakHashMap<>();
        listenerList = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {

        HwListener<K, V> listener = (k, v, action) -> System.out.println("key:{" + k + "}, value:{" + v + "}, action: {" + action + "}");

        listener.notify(key, value, "put");
        addListener(listener);
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        V value = cache.get(key);
        HwListener<K, V> listener = (k, v, action) -> System.out.println("key:{" + k + "}, value:{" + v + "}, action: {" + action + "}");
        listener.notify(key, value, "delete");
        removeListener(listener);
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenerList.remove(listener);
    }
}
