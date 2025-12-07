package org.thuvien.controller;

// Chạy thử
public class Main {
    public static void main(String[] args) {
        Integer[] intArray = {1, 5, 3, 9, 2};
        String[] stringArray = {"apple", "orange", "banana", "pear"};

        System.out.println("Max in intArray: " + MyUtility.findMax(intArray)); // Output: 9
        System.out.println("Max in stringArray: " + MyUtility.findMax(stringArray)); // Output: pear
    }
}
