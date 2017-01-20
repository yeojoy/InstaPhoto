package me.yeojoy.instaapp.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class Validator {
    public static boolean isValidUserName(String userName) {
        return !TextUtils.isEmpty(userName);
    }

    public static boolean isQueryValidated(String query) {
        if (TextUtils.isEmpty(query)) return false;

        Pattern userNamePatterns = Pattern.compile("[\\w\\d_]+");

        return userNamePatterns.matcher(query).matches();
    }
}
