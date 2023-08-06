package com.epam.esm.utils;

import org.mindrot.jbcrypt.BCrypt;

public class CryptUtil {

    private CryptUtil() {
    }

    private static final int HASH_ROUNDS = 12;

    public static String hashString(String string) {
        return BCrypt.hashpw(string, BCrypt.gensalt(HASH_ROUNDS));
    }

    public static boolean isValidHash(String testString, String hash) {
        return BCrypt.checkpw(testString, hash);
    }
}

