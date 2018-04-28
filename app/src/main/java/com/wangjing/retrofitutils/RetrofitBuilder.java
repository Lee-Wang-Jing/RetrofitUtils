package com.wangjing.retrofitutils;

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

    /**
     *
     * @return
     */
    public static RetrofitBuilder newBuilder() {
        return new RetrofitBuilder();
    }

    /**
     * 设置请求的userAgent
     *
     * @param userAgent usetAgent
     * @return
     */
    public RetrofitBuilder usetAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }


    /**
     * 设置请求的baseUrl
     *
     * @param baseUrl 请求的baseUrl地址
     * @return This Builder
     */
    public RetrofitBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /**
     * 设置网络请求的连接超时时间
     *
     * @param connectTimeout 超时秒数
     * @return
     */
    public RetrofitBuilder connectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * 设置网络请求的读取超时时间
     *
     * @param readTimeout 超时秒数
     * @return
     */
    public RetrofitBuilder readTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * 设置网络请求的写入超时时间
     *
     * @param writeTimeout 超时秒数
     * @return
     */
    public RetrofitBuilder writeTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public RetrofitConfig build() {
        return new RetrofitConfig();
    }
}
