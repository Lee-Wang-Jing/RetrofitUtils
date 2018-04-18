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

    public RetrofitConfig build() {
        return new RetrofitConfig(baseUrl);
    }
}
