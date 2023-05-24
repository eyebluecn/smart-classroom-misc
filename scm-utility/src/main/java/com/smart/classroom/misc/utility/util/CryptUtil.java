package com.smart.classroom.misc.utility.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author lishuang
 * @date 2023-05-15
 * 加密解密
 */
public class CryptUtil {

    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public static String bCryptEncode(CharSequence rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    public static boolean bCryptMatches(CharSequence rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

}
