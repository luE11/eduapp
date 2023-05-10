package pra.luis.eduapp.eduapp.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.Normalizer;

public class MyStringUtils {

    private static final String VALID_PW_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+{}[]|:;<>?,./";

    public static String generateRandomString(int length)
    {
        return RandomStringUtils.random(length, 0, VALID_PW_CHARS.length(), false,
                false, VALID_PW_CHARS.toCharArray(), new SecureRandom());
    }
}
