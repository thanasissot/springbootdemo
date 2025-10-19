package com.asot.springbootdemo.utils;

import java.util.LinkedList;

public class HashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 101; // Prime number for better distribution

    private LinkedList<Entry<K, V>>[] buckets;
    private HashFunction<K> hashFunction;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTable(HashFunction<K> hashFunction) {
        this.buckets = new LinkedList[DEFAULT_CAPACITY];
        this.hashFunction = hashFunction;

        // Initialize all buckets
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int hash = hashFunction.hash(key);
        int index = Math.abs(hash % buckets.length);

        // Check if key already exists
        for (Entry<K, V> entry : buckets[index]) {
            if (entry.key.equals(key)) {
                entry.value = value; // Update existing key
                return;
            }
        }

        // Add new entry
        buckets[index].add(new Entry<>(key, value));
        size++;
    }

    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int hash = hashFunction.hash(key);
        int index = Math.abs(hash % buckets.length);

        for (Entry<K, V> entry : buckets[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null; // Key not found
    }

    public int size() {
        return size;
    }

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public interface HashFunction<K> {
        int hash(K key);
    }

}


