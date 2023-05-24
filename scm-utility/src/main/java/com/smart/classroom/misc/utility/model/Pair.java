package com.smart.classroom.misc.utility.model;

import java.util.Objects;

/**
 * 将两个值封装在一个类中
 * 从而方便多值返回使用
 *
 * @author lishuang
 * @version 2023-05-11
 */
public class Pair<K, V> {

    /**
     * 键
     */
    private K key;

    public K getKey() {
        return key;
    }

    /**
     * 值
     */
    private V value;

    public V getValue() {
        return value;
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    @Override
    public int hashCode() {
        // name's hashCode is multiplied by an arbitrary prime number (13)
        // in order to make sure there is a difference in the hashCode between
        // these two parameters:
        //  name: a  value: aa
        //  name: aa value: a
        return key.hashCode() * 13 + (value == null ? 0 : value.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Pair) {
            Pair pair = (Pair) o;
            if (!Objects.equals(key, pair.key)) {
                return false;
            }
            return Objects.equals(value, pair.value);
        }
        return false;
    }
}