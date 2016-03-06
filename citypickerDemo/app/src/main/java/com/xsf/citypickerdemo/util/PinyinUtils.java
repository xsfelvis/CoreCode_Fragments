package com.xsf.citypickerdemo.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * @author hzxushangfei
 * @time Created at 2016/3/4.
 * @email xsf_uestc_ncl@163.com
 */
public class PinyinUtils {
    public static String getFirstLetter(final String pinyin){
        if(TextUtils.isEmpty(pinyin)) return "定位";
        String firstCharacter = pinyin.substring(0,1);
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if(pattern.matcher(firstCharacter).matches()){
            return  firstCharacter.toUpperCase();
        }else if("0".equals(firstCharacter)){
            return "定位";
        }else if("1".equals(firstCharacter)){
            return "热门";
        }
        return "定位";
    }
}
