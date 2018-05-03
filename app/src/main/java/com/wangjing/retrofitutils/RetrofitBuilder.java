package com.wangjing.retrofitutils;

import java.util.HashMap;

/**
 * 作者：Created by WangJing on 2018/4/17.
 * 邮箱：wangjinggm@gmail.com
 * 描述：RetrofitBuilder
 * 最近修改：2018/4/17 15:23 by WangJing
 */
public class RetrofitBuilder {

    /**
     * 请求的baseUrl
     */
    private String baseUrl;
    private int connectTimeout;
    private int readTimeout;
    private int writeTimeout;
    private String userAgent;
    //headerHashMap header的键值对集合
    private HashMap<String, String> headerHashMap;
    private boolean isDebug;

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public HashMap<String, String> getHeaderHashMap() {
        return headerHashMap;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public static class Builder {
        /**
         * 请求的baseUrl
         */
        private String baseUrl;
        private int connectTimeout;
        private int readTimeout;
        private int writeTimeout;
        private String userAgent;
        //headerHashMap header的键值对集合
        private HashMap<String, String> headerHashMap;
        private boolean isDebug;

        /**
         * 设置请求的userAgent
         *
         * @param userAgent usetAgent
         * @return
         */
        public Builder usetAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }


        /**
         * 设置请求的baseUrl
         *
         * @param baseUrl 请求的baseUrl地址
         * @return This Builder
         */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 设置网络请求的连接超时时间
         *
         * @param connectTimeout 超时秒数
         * @return
         */
        public Builder connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 设置网络请求的读取超时时间
         *
         * @param readTimeout 超时秒数
         * @return
         */
        public Builder readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * 设置网络请求的写入超时时间
         *
         * @param writeTimeout 超时秒数
         * @return
         */
        public Builder writeTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder headerHashMap(HashMap<String, String> headerHashMap) {
            this.headerHashMap = headerHashMap;
            return this;
        }

        public Builder setDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public RetrofitBuilder builder() {
            return new RetrofitBuilder(this);
        }
    }

    private RetrofitBuilder(Builder b) {
        baseUrl = b.baseUrl;
        connectTimeout = b.connectTimeout;
        readTimeout = b.readTimeout;
        writeTimeout = b.writeTimeout;
        userAgent = b.userAgent;
        headerHashMap = b.headerHashMap;
        isDebug = b.isDebug;
    }

}
