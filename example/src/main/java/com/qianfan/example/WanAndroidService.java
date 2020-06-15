package com.qianfan.example;

import com.wangjing.retrofitutils.DynamicTimeOut;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WanAndroidService {
    /**
     * 获取所有包名
     * https://wanandroid.com/blog/show/2751
     *
     * @return String
     */
    @GET("maven_pom/package/json")
    @DynamicTimeOut(timeout = 30)
    Call<String> getMavenPom();

    /**
     * 查询对应库版本
     * https://wanandroid.com/blog/show/2751
     *
     * @param k 参数
     * @return String
     */
    @GET("maven_pom/search/json")
    @DynamicTimeOut(timeout = 10)
    Call<String> search(@Query("k") String k);
}