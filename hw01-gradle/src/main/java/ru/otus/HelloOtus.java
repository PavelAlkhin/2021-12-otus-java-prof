package ru.otus;

import com.google.common.base.CharMatcher;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

public class HelloOtus {
    public static void main(String[] args) {

        Multimap<Integer, String> multimap = HashMultimap.create();
        multimap.put(1, "a");
        multimap.put(2, "b");
        multimap.put(3, "c");
        multimap.put(1, "a2");

        for (Collection collection : multimap.asMap().values()) {
            System.out.println(collection.toArray());
        }
    }
}
