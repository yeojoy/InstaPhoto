package me.yeojoy.instaapp.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class Validator {

    /**
     * Insta userName validation 체크
     * 영어, 숫자, _(under bar), .(dot)만 가능
     * @param query
     * @return
     */
    public static boolean isQueryValidated(String query) {
        if (TextUtils.isEmpty(query)) return false;

        Pattern userNamePatterns = Pattern.compile("[\\w\\d_\\.]+");

        return userNamePatterns.matcher(query).matches();
    }
}
