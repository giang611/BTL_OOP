package org.thuvien.controller;

// Lớp tiện ích MyUtility
public class MyUtility {
    // Phương thức tĩnh tổng quát để tìm phần tử lớn nhất
    public static <T extends Comparable<T>> T findMax(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        T max = array[0];
        for (T element : array) {
            if (element.compareTo(max) > 0) {
                max = element;
            }
        }
        return max;
    }
}
