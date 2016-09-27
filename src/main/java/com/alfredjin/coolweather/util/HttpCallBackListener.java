package com.alfredjin.coolweather.util;

/**
 * @author Created by AlfredJin on 2016/9/27.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
