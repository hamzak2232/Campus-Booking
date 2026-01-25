package com.campus.booking.util;

import java.util.Scanner;

public final class InputUtil {

    private static final Scanner SCANNER = new Scanner(System.in);

    private InputUtil() {}

    public static String readString(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
