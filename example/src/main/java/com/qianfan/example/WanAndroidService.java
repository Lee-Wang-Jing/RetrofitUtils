package com.qianfan.example;

import com.wangjing.retrofitutils.DynamicTimeOut;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

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

    /**
     * 获取最新实时段子
     * https://api.apiopen.top/getJoke?page=1&count=2&type=video
     * @param url String
     * @return String
     */
    @GET
    Call<String> getTop(@Url String url);
}
