package by.htp;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class StringUtils {

    public static void main(String[] args) {
        System.out.println("run in main");
    }

    public static boolean isPositiveNumber(String str) {
        return isNumeric(str) && !isEmpty(str) && Integer.parseInt(str) >= 0;
    }
}