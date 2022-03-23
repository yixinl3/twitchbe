package com.project.jupiter.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.Locale;

public class Util {

    // Help encrypt the user password before save to the database
    public static String encryptPassword(String userId, String password) throws IOException {
        return DigestUtils.md5Hex(userId + DigestUtils.md5Hex(password)).toLowerCase();

    }
}