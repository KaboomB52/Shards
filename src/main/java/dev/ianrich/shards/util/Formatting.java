package dev.ianrich.shards.util;

public class Formatting {

    public static String addCommasToInteger(Integer integer){
        String result = "";
        String digits = String.valueOf(integer);
        for (int i=1; i <= digits.length(); ++i) {
            char ch = digits.charAt(digits.length() - i);
            if (i % 3 == 1 && i > 1) {
                result = "," + result;
            }
            result = ch + result;
        }

        return result;
    }
}
