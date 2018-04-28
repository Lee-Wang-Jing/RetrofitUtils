package com.wangjing.retrofitutils;

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

    public RetrofitConfig() {
        super();
    }

//    public RetrofitConfig(String baseUrl) {
//        RetrofitConfig.baseUrl = baseUrl;
//    }


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
