package com.alfredjin.coolweather.bean;

/**
 * @author Created by AlfredJin on 2016/9/26.
 */
public class Province {
    private int id;
    private String provinceName;
    private String provinceCode;

    public int getId() {
        return id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", provinceName='" + provinceName + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                '}';
    }
}
