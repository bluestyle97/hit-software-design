package hash;

import java.util.LinkedList;
import java.util.HashMap;
import java.lang.Math;

/**
 * @author Xu Jiale
 */
public class Hash<T extends java.lang.Number> {
    public HashMap<Integer, LinkedList<T>> hashMap;

    protected int capacity;
    protected int size;

    private HashFunction hashMethod;
    private static final int defaultCapacity = 100;

    public Hash() {
        this.hashMap = new HashMap<>();
        this.capacity = defaultCapacity;
        this.hashMethod = new HashByMod();
        this.size = 0;
    }

    public Hash(HashFunction method) {
        this.hashMap = new HashMap<>();
        this.capacity = defaultCapacity;
        this.hashMethod = method;
        this.size = 0;
    }

    public Hash(int newCapacity, HashFunction method) {
        this.hashMap = new HashMap<>();
        this.capacity = newCapacity;
        this.hashMethod = method;
        this.size = 0;
    }

    public void insert(T... values) {
        for (T value : values) {
            Integer key = hashMethod.hash(value);
            hashMap.putIfAbsent(key, new LinkedList<>());
            if (hashMap.get(key).indexOf(value) == -1) {
                hashMap.get(key).add(value);
            }
        }
        this.size = hashMap.size();
    }

    public boolean delete(T value) {
        Integer key = search(value);
        if (key == -1) {
            return false;
        }
        hashMap.get(key).remove(value);
        if (hashMap.get(key).isEmpty()) {
            hashMap.remove(key);
        }
        this.size = hashMap.size();
        return true;
    }

    public Integer search(T value) {
        Integer key = hashMethod.hash(value);
        if (hashMap.get(key).indexOf(value) != -1) {
            return key;
        }
        return -1;
    }

    public void print() {
        for (Integer key : hashMap.keySet()) {
            System.out.print(key + ": ");
            for (T value : hashMap.get(key)) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        double factor = loadingFactor();
        System.out.println("Loading Factor: " + factor);
    }

    public double loadingFactor() {
        return 1.0 * size / capacity;
    }
}

interface HashFunction {
    Integer hash(java.lang.Number value);
}