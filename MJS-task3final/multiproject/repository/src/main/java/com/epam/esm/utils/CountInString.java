package com.epam.esm.utils;

public class CountInString {

    private CountInString() {
    }

    public static int getCountOfElInStr(String str) {
        if (str != null) {
            return str.split(",").length;
        }
        return 0;
    }


}
