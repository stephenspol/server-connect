package com.stephenspol.server.connect.util;

public class Utility {

    private Utility() {}

    public static String toCamelCase(String s) {
        StringBuilder sb = new StringBuilder();
        for(String part : s.toLowerCase().split("_"))
        {
            sb.append(part.substring(0,1).toUpperCase());
            sb.append(part.substring(1));
        }

        return sb.toString();
    }
}