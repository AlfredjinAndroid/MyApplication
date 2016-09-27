package com.alfredjin.coolweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Created by AlfredJin on 2016/9/27.
 * 用来和服务器连接的类
 */
public class HttpUtil {

    public static void sendHttpRequest(final String address,
                                       final HttpCallbackListener lister) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        try {
                            URL url = new URL(address);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
                            InputStream in = connection.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }//while结束
                            if (lister != null) {
                                //回调onFinish()方法
                                lister.onFinish(response.toString());
                            }
                        } catch (Exception e) {
                            if (lister != null) {
                                //回调onError()方法
                                lister.onError(e);
                            }
                        } finally {
                            if (connection != null) {
                                connection.disconnect();
                            }
                        }
                    }//run()方法结束
                }//runnable结束
        ).start();//Thread结束并调用start()方法
    }
}
