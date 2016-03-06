package com.xsf.citypickerdemo.model;

/**
 * @author hzxushangfei
 * @time Created at 2016/3/2.
 * @email xsf_uestc_ncl@163.com
 */
public class City {
    private String name;
    private String pinyin;

    public City() {
    }


    public City(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
