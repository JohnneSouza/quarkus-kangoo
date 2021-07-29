package com.easy.ecomm.utils;

import java.util.Random;

public class StringUtils {

    private static final Random RANDOM = new Random();
    private static final int LEFT_LIMIT = 97;
    private static final int RIGHT_LIMIT = 122;

    private StringUtils() {
    }

    public static String generateRandomEmail(){
        return generateRandomString(8)
                .concat("@")
                .concat(generateRandomString(8))
                .concat(".com");
    }

    public static String generateRandomString(int length){
        return RANDOM.ints(LEFT_LIMIT, RIGHT_LIMIT + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
