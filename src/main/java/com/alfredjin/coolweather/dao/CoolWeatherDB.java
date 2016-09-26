package com.alfredjin.coolweather.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alfredjin.coolweather.bean.City;
import com.alfredjin.coolweather.bean.Country;
import com.alfredjin.coolweather.bean.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by AlfredJin on 2016/9/26.
 *         创建一个 CoolWeatherDB类，这个类将会把一些常用的
 *         数据库操作封装起来，以方便我们后面使用
 */
public class CoolWeatherDB {
    /**
     * 数据库名
     */
    public final static String DB_NAME = "coll_weather";
    /**
     * 数据库版本
     */
    public final static int VERSION = 1;

    private static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(
                context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取CoolWeatherDB的实例。
     */
    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    /**
     * 将Province实例存储到数据库。
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    /**
     * 从数据库读取全国所有的省份信息
     */
    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                //设置ID
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                //设置provinceName
                province.setProvinceName(
                        cursor.getString(cursor.
                                getColumnIndex("province_name"))
                );
                //设置provinceCode
                province.setProvinceCode(
                        cursor.getString(cursor
                                .getColumnIndex("province_code"))
                );
                //添加到集合
                list.add(province);
            } while (cursor.moveToNext());
        }//if结束
        return list;
    }

    /**
     * 将City实例存储到数据库
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    /**
     * 从数据库中读取某省下所有的城市信息
     */
    public List<City> loadCities(int provinceId) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id = ? ",
                new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(
                        cursor.getColumnIndex("city_name")
                ));
                city.setCityCode(cursor.getString(
                        cursor.getColumnIndex("city_code")
                ));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将Country实例存储到数据库
     */
    public void saveCountry(Country country) {
        if (country != null) {
            ContentValues values = new ContentValues();
            values.put("country_name", country.getCountryName());
            values.put("country_code", country.getCountryCode());
            values.put("city_id", country.getCityId());
            db.insert("Country", null, values);
        }
    }

    /**
     * 从数据库读取某城市下所有的县信息
     */
    public List<Country> loadCounties(int cityId) {
        List<Country> list = new ArrayList<Country>();
        //查询
        Cursor cursor = db.query("Country", null, "city_id = ?",
                new String[]{String.valueOf(cityId)}, null, null, null);
        //如果游标在第一个即有值时，执行do...while()循环
        if (cursor.moveToFirst()) {
            do {
                Country country = new Country();
                //设置ID
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                //设置CountryName
                country.setCountryName(cursor.getString(
                        cursor.getColumnIndex("country_name")
                ));
                //设置CountryCode
                country.setCountryCode(cursor.getString(
                        cursor.getColumnIndex("country_code")
                ));
                country.setCityId(cityId);//设置CityId
                list.add(country);//将country对象添加到集合中
            } while (cursor.moveToNext());//循环结束
        }//if结束
        return list;//将集合返回
    }
}
