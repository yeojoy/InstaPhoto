package me.yeojoy.instaapp.utils;

import android.text.TextUtils;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class Validator {
    public static boolean isValidUserName(String userName) {
        return !TextUtils.isEmpty(userName);
    }
}
