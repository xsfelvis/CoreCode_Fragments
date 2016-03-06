package com.xsf.citypickerdemo.db;

import com.xsf.citypickerdemo.model.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author hzxushangfei
 * @time Created at 2016/3/6.
 * @email xsf_uestc_ncl@163.com
 */
public class TestDb {
    private List<City> citylist;

    public TestDb(List<City> citylist) {
        this.citylist = citylist;
    }

    public static List<City> getAllCities(){
        List<City> result = new ArrayList<>();
        result.add(new City("北京","beijin"));
        result.add(new City("鞍山","anshan"));
        result.add(new City("安庆","anqing"));
        result.add(new City("保定","baoding"));
        result.add(new City("包头","baotou"));
        result.add(new City("上海","shanghai"));
        result.add(new City("广州","guangzhou"));
        result.add(new City("深圳","shenzhen"));
        result.add(new City("杭州","hangzhou"));
        result.add(new City("南京","nanjin"));
        result.add(new City("天津","tianjn"));
        result.add(new City("武汉","wuhan"));
        result.add(new City("重庆","chongiqng"));
        result.add(new City("阿拉善","alashan"));
        result.add(new City("阿拉善","alashan"));
        result.add(new City("澳门","aomen"));
        result.add(new City("亳州","bozhou"));
        result.add(new City("宝鸡","baoji"));
        result.add(new City("成都","chengdu"));
        result.add(new City("巢湖","chaohu"));

        Collections.sort(result, new CityComparator());
        return result;
    }
    /**
     * a-z排序
     */
    private static class CityComparator implements Comparator<City> {
        @Override
        public int compare(City cityone, City citytwo) {
            String a = cityone.getPinyin().substring(0, 1);
            String b = citytwo.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }

}
