package org.example;

public class StringProcessor {
    public static String reverse(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}