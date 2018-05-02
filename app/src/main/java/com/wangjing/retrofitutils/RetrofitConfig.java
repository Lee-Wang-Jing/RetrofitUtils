package com.wangjing.retrofitutils;

import java.util.HashMap;

/**
 * 作者：Created by WangJing on 2018/4/16.
 * 邮箱：wangjinggm@gmail.com
 * 描述：RetrofitConfig
 * 最近修改：2018/4/16 16:08 by WangJing
 */
public class RetrofitConfig {

    private static String baseUrl;
    private static int connectTimeout;
    private static int readTimeout;
    private static int writeTimeout;
    private static String userAgent;
    //headerHashMap header的键值对集合
    private static HashMap<String, String> headerHashMap;


    protected RetrofitConfig(String baseUrl1, int connectTimeout1, int readTimeout1, int writeTimeout1, String userAgent1, HashMap<String, String> headerHashMap1) {
        baseUrl = baseUrl1;
        connectTimeout = connectTimeout1;
        readTimeout = readTimeout1;
        writeTimeout = writeTimeout1;
        userAgent = userAgent1;
        headerHashMap = headerHashMap1;
    }

//    public RetrofitConfig(String baseUrl) {
//        RetrofitConfig.baseUrl = baseUrl;
//    }


    public static HashMap<String, String> getHeaderHashMap() {
        return headerHashMap;
    }

    public static String getUserAgent() {
        return userAgent;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getConnectTimeout() {
        return connectTimeout != 0 ? connectTimeout : 30;
    }

    public int getReadTimeout() {
        return readTimeout != 0 ? connectTimeout : 30;
    }

    public int getWriteTimeout() {
        return writeTimeout != 0 ? connectTimeout : 30;
    }
}
