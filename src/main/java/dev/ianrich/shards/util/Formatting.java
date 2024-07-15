package dev.ianrich.shards.util;

public class Formatting {

    public static String addCommasToInteger(Integer integer){
        return String.format("%,d", integer);
    }
}
