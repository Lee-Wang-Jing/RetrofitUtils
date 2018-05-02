package com.wangjing.retrofitutils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 作者：Created by WangJing on 2018/4/16.
 * 邮箱：wangjinggm@gmail.com
 * 描述：RetrofitUtils
 * 最近修改：2018/4/16 16:09 by WangJing
 */
public class RetrofitUtils {
    private static RetrofitConfig retrofitConfig;
    private static Retrofit retrofit;
    private static OkHttpClient.Builder okhttpBuilder;
    private static HttpLoggingInterceptor interceptor;

    /**
     * 初始化
     *
     * @param config  RetrofitConfig
     */
    public static void initialize(RetrofitConfig config) {
        retrofitConfig = config;
        Log.e("RetrofitUtils","RetrofitUtils初始化成功==》"+retrofitConfig.getBaseUrl());
    }


    /**
     * 创建带有默认BaseUrl的serviceClass
     *
     * @param serviceClass
     * @return
     */
    public static <T> T creatBaseApi(Class<T> serviceClass) {
        synchronized (RetrofitUtils.class) {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl("" + retrofitConfig.getBaseUrl())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(getHttpClient())
                        .build();
            }
            return retrofit.create(serviceClass);
        }
    }

    /**
     * 创建新的带有默认BaseUrl的serviceClass,会将原来的retrofit置空生成新的
     *
     * @param serviceClass
     * @return serviceClass
     */
    public static <T> T creatNewBaseApi(Class<T> serviceClass) {
        synchronized (RetrofitUtils.class) {
            clearAll();
            retrofit = new Retrofit.Builder()
                    .baseUrl("" + retrofitConfig.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(getHttpClient())
                    .build();
            return retrofit.create(serviceClass);
        }
    }

    /**
     * 创建不带有默认BaseUrl的serviceClass，记住使用了creatNoBaseUrlApi方法后如果再次使用creatBaseApi是无效的，因为retrofit不为null，需要clearRetrofit
     *
     * @param serviceClass serviceClass
     * @return
     */
    public static <T> T creatNoBaseUrlApi(Class<T> serviceClass) {
        synchronized (RetrofitUtils.class) {
            //如果refrofit不为null，则滞空，创建新的retrofit
            clearAll();
            retrofit = new Retrofit.Builder()
                    //设置 Json 转换器
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getHttpClient())
                    .build();
            return retrofit.create(serviceClass);
        }
    }

    /**
     * 清空Retrofit
     */
    public static void clearRetrofit() {
        if (retrofit != null) {
            retrofit = null;
        }
    }

    /**
     * 清空okhttpBuilder
     */
    public static void clearOkhttpBuilder() {
        if (okhttpBuilder != null) {
            okhttpBuilder = null;
        }
    }

    /**
     * 清空Retrofit和okhttpBuilder
     */
    public static synchronized void clearAll() {
        clearRetrofit();
        clearOkhttpBuilder();
    }

    /**
     * 获取OkHttpClient
     *
     * @return
     */
    private static OkHttpClient getHttpClient() {
        synchronized (RetrofitUtils.class) {
            if (okhttpBuilder == null) {
                okhttpBuilder = new OkHttpClient.Builder();
                //设置超时
                okhttpBuilder.connectTimeout(retrofitConfig.getConnectTimeout(), TimeUnit.SECONDS);
                okhttpBuilder.readTimeout(retrofitConfig.getReadTimeout(), TimeUnit.SECONDS);
                okhttpBuilder.writeTimeout(retrofitConfig.getWriteTimeout(), TimeUnit.SECONDS);
                //错误重连
                okhttpBuilder.retryOnConnectionFailure(true);
                //设置请求头Header
                okhttpBuilder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //.headers(Headers headers) 会移除所有的Header然后添加新的设置的所有的Headers
                        //.addHeader(String name, String value) 不会移除现有的Header，即使相同的key的header存在，也不会移除或者覆盖，会新增一条新的key和value的header
                        //.header(String name, String value) 会移除和当前设置的key相同的所有header，然后添加进当前设置的key value 的header
                        Request.Builder builder = chain.request().newBuilder();
                        HashMap<String, String> headerHashMap = retrofitConfig.getHeaderHashMap();
                        if (headerHashMap != null && headerHashMap.size() > 0) {
                            Set<String> keys = headerHashMap.keySet();
                            if (keys != null && keys.size() > 0) {
                                for (String key : keys) {
                                    builder.header("" + key, "" + headerHashMap.get(key));
                                }
                            }
                        }
                        builder.build();

                        return chain.proceed(builder.build());
                    }
                });
//                //缓存机制,无网络时，也能显示数据
//                File cacheFile = new File(MyApplication.getmContext().getExternalCacheDir(), "ZongHengCache");
//                Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);//设置缓存大小50M
//                Interceptor cacheInterceptor = new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        if (!NetworkUtils.isNetworkConnected()) {//如果没有网络
//                            request = request.newBuilder()
//                                    .cacheControl(CacheControl.FORCE_CACHE)
//                                    .build();
//                        }
//                        Response response = chain.proceed(request);
//                        if (NetworkUtils.isNetworkConnected()) {
//                            int maxAge = 0;
//                            // 有网络时 设置缓存超时时间0个小时
//                            response.newBuilder()
//                                    .header("Cache-Control", "public, max-age=" + maxAge)
//                                    .removeHeader("ZongHeng")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                                    .build();
//                        } else {
//                            // 无网络时，设置超时为4周
//                            int maxStale = 60 * 60 * 24 * 28;
//                            response.newBuilder()
//                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                                    .removeHeader("nyn")
//                                    .build();
//                        }
//                        return response;
//                    }
//                };
//                okhttpBuilder.cache(cache).addInterceptor(cacheInterceptor);
            }

            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                if (interceptor == null) {
                    interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                }
                //设置Debug Log 模式
                okhttpBuilder.addInterceptor(interceptor);
            }
            //以上设置结束，才能build(),不然设置白搭
            OkHttpClient okHttpClient = okhttpBuilder.build();
            return okHttpClient;
        }
    }

    public static RetrofitConfig getRetrofitConfig() {
        return retrofitConfig;
    }
}
