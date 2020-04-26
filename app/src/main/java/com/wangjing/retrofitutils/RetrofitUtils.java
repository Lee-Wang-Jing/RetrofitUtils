package com.wangjing.retrofitutils;

import android.util.Log;

import com.wangjing.retrofitutils.adapter.LiveDataCallAdapter;
import com.wangjing.retrofitutils.adapter.LiveDataCallAdapterFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
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
    private static RetrofitBuilder retrofitBuilder;
    private static OkHttpClient.Builder okhttpBuilder;
    private static HttpLoggingInterceptor interceptor;

    /**
     * 初始化
     *
     * @param builder RetrofitBuilder
     */
    public static void initialize(RetrofitBuilder builder) {
        clearAll();
        retrofitBuilder = builder;
        Log.e("RetrofitUtils", "RetrofitUtils初始化成功==》" + retrofitBuilder.getBaseUrl());
    }


    /**
     * 创建带有默认BaseUrl的serviceClass
     *
     * @param serviceClass serviceClass
     * @param <T>          泛型
     * @return <T> 泛型
     */
    public static <T> T creatBaseApi(Class<T> serviceClass) {
        synchronized (RetrofitUtils.class) {
            if (getRetrofitBuilder().getFactory() != null) {
                return new Retrofit.Builder()
                        .baseUrl("" + getRetrofitBuilder().getBaseUrl())
                        .addConverterFactory(getRetrofitBuilder().getFactory())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            } else {
                //默认使用GsonConverterFactory
                return new Retrofit.Builder()
                        .baseUrl("" + getRetrofitBuilder().getBaseUrl())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            }
        }
    }


    /**
     * 创建带有默认BaseUrl的serviceClass
     *
     * @param serviceClass serviceClass
     * @param <T>          泛型
     * @return <T> 泛型
     */
    public static <T> T creatBaseApiWithUrl(Class<T> serviceClass, String url) {
        synchronized (RetrofitUtils.class) {
            if (getRetrofitBuilder().getFactory() != null) {
                return new Retrofit.Builder()
                        .baseUrl("" + url)
                        .addConverterFactory(getRetrofitBuilder().getFactory())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            } else {
                //默认使用GsonConverterFactory
                return new Retrofit.Builder()
                        .baseUrl("" + url)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            }
        }
    }

    /**
     * 创建带有默认BaseUrl的serviceClass，并且含有LiveDataCallAdapterFactory
     *
     * @param serviceClass serviceClass
     * @param <T>          泛型
     * @return <T> 泛型
     */
    public static <T> T creatBaseApiWithAdapter(Class<T> serviceClass) {
        synchronized (RetrofitUtils.class) {
            if (getRetrofitBuilder().getFactory() != null) {
                return new Retrofit.Builder()
                        .baseUrl("" + getRetrofitBuilder().getBaseUrl())
                        .addConverterFactory(getRetrofitBuilder().getFactory())
                        .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            } else {
                return new Retrofit.Builder()
                        .baseUrl("" + getRetrofitBuilder().getBaseUrl())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            }
        }
    }

    /**
     * 创建新的带有默认BaseUrl的serviceClass,会将原来的retrofit置空生成新的
     *
     * @param serviceClass serviceClass
     * @param <T>          泛型
     * @return serviceClass
     */
    public static <T> T creatNewBaseApi(Class<T> serviceClass) {
        synchronized (RetrofitUtils.class) {
            clearAll();
            if (getRetrofitBuilder().getFactory() != null) {
                return new Retrofit.Builder()
                        .baseUrl("" + getRetrofitBuilder().getBaseUrl())
                        .addConverterFactory(getRetrofitBuilder().getFactory())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            } else {
                return new Retrofit.Builder()
                        .baseUrl("" + getRetrofitBuilder().getBaseUrl())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            }
        }
    }

    /**
     * 创建新的带有默认BaseUrl的serviceClass,会将原来的retrofit置空生成新的，并且含有自定义的CallAdapterFactory
     *
     * @param serviceClass serviceClass
     * @param <T>          泛型
     * @return serviceClass
     */
    public static <T> T creatNewBaseApiWithCustomAdapter(Class<T> serviceClass, CallAdapter.Factory callAdapterFactory) {
        synchronized (RetrofitUtils.class) {
            clearAll();
            if (getRetrofitBuilder().getFactory() != null) {
                return new Retrofit.Builder()
                        .baseUrl("" + getRetrofitBuilder().getBaseUrl())
                        .addConverterFactory(getRetrofitBuilder().getFactory())
                        .addCallAdapterFactory(callAdapterFactory)
                        .client(getHttpClient())
                        .build().create(serviceClass);
            } else {
                return new Retrofit.Builder()
                        .baseUrl("" + getRetrofitBuilder().getBaseUrl())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(callAdapterFactory)
                        .client(getHttpClient())
                        .build().create(serviceClass);
            }
        }
    }

    /**
     * 创建新的带有默认BaseUrl的serviceClass,会将原来的retrofit置空生成新的，并且含有LiveDataCallAdapterFactory
     *
     * @param serviceClass serviceClass
     * @param <T>          泛型
     * @return serviceClass
     */
    public static <T> T creatNewBaseApiWithAdapter(Class<T> serviceClass) {
        synchronized (RetrofitUtils.class) {
            clearAll();
            if (getRetrofitBuilder().getFactory() != null) {
                return new Retrofit.Builder()
                        .baseUrl("" + getRetrofitBuilder().getBaseUrl())
                        .addConverterFactory(getRetrofitBuilder().getFactory())
                        .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            } else {
                return new Retrofit.Builder()
                        .baseUrl("" + getRetrofitBuilder().getBaseUrl())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            }

        }
    }


    /**
     * 创建不带有默认BaseUrl的serviceClass，记住使用了creatNoBaseUrlApi方法后如果再次使用creatBaseApi是无效的，因为retrofit不为null，需要clearRetrofit
     *
     * @param serviceClass serviceClass
     * @param <T>          泛型
     * @return <T> 泛型
     */
    public static <T> T creatNoBaseUrlApi(Class<T> serviceClass) {
        synchronized (RetrofitUtils.class) {
            //如果refrofit不为null，则滞空，创建新的retrofit
            clearAll();
            if (getRetrofitBuilder().getFactory() != null) {
                return new Retrofit.Builder()
                        .addConverterFactory(getRetrofitBuilder().getFactory())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            } else {
                return new Retrofit.Builder()
                        //设置 Json 转换器
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            }

        }
    }

    /**
     * 创建不带有默认BaseUrl的serviceClass，记住使用了creatNoBaseUrlApi方法后如果再次使用creatBaseApi是无效的，因为retrofit不为null，需要clearRetrofit，并且含有LiveDataCallAdapterFactory
     *
     * @param serviceClass serviceClass
     * @param <T>          泛型
     * @return <T> 泛型
     */
    public static <T> T creatNoBaseUrlApiWithAdapter(Class<T> serviceClass) {
        synchronized (RetrofitUtils.class) {
            //如果refrofit不为null，则滞空，创建新的retrofit
            clearAll();
            if (getRetrofitBuilder().getFactory() != null) {
                return new Retrofit.Builder()
                        .addConverterFactory(getRetrofitBuilder().getFactory())
                        .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            } else {
                return new Retrofit.Builder()
                        //设置 Json 转换器
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                        .client(getHttpClient())
                        .build().create(serviceClass);
            }

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
                //设置Http 请求 协议
                if (getRetrofitBuilder().getProtocols() != null && !getRetrofitBuilder().getProtocols().isEmpty()) {
                    okhttpBuilder.protocols(getRetrofitBuilder().getProtocols());
                }
                if (getRetrofitBuilder().getEventListenerFactory() != null) {
                    okhttpBuilder.eventListenerFactory(getRetrofitBuilder().getEventListenerFactory());
                }
                //错误重连
                okhttpBuilder.retryOnConnectionFailure(true);
                if (getRetrofitBuilder().getInterceptor() != null) {
                    okhttpBuilder.addInterceptor(getRetrofitBuilder().getInterceptor());
                } else {
                    //设置请求头Header
                    okhttpBuilder.addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            //.headers(Headers headers) 会移除所有的Header然后添加新的设置的所有的Headers
                            //.addHeader(String name, String value) 不会移除现有的Header，即使相同的key的header存在，也不会移除或者覆盖，会新增一条新的key和value的header
                            //.header(String name, String value) 会移除和当前设置的key相同的所有header，然后添加进当前设置的key value 的header
                            //如果同时设置了 headerHashMap、addHeaderHashMap和headersHashMap
                            //则优先顺序为 headersHashMap--headerHashMap--addHeaderHashMap 只会设置一种
                            Request.Builder builder = chain.request().newBuilder();
                            HashMap<String, String> headersHashMap = getRetrofitBuilder().getHeadersHashMap();
                            HashMap<String, String> headerHashMap = getRetrofitBuilder().getHeaderHashMap();
                            HashMap<String, String> addHeaderHashMap = getRetrofitBuilder().getAddHeaderHashMap();
                            if (headersHashMap != null && headersHashMap.size() > 0) {
                                Set<String> keys = headersHashMap.keySet();
                                if (keys != null && keys.size() > 0) {
                                    Headers.Builder headersBuilder = new Headers.Builder();
                                    for (String key : keys) {
                                        headersBuilder.set(key, headersBuilder.get(key));
                                    }
                                    builder.headers(headersBuilder.build());
                                }
                            } else if (headerHashMap != null && headerHashMap.size() > 0) {
                                Set<String> keys = headerHashMap.keySet();
                                if (keys != null && keys.size() > 0) {
                                    for (String key : keys) {
                                        builder.header("" + key, "" + headerHashMap.get(key));
                                    }
                                }
                            } else if (addHeaderHashMap != null && addHeaderHashMap.size() > 0) {
                                Set<String> keys = addHeaderHashMap.keySet();
                                if (keys != null && keys.size() > 0) {
                                    for (String key : keys) {
                                        builder.header("" + key, "" + addHeaderHashMap.get(key));
                                    }
                                }
                            }
                            return chain.proceed(builder.build());
                        }
                    });
                }

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
            if (getRetrofitBuilder().isTrustSSL()) {
                if (getRetrofitBuilder().getSslSocketFactory() != null) {
                    okhttpBuilder.sslSocketFactory(getRetrofitBuilder().getSslSocketFactory());
                } else {
                    final TrustManager[] trustAllCerts = new TrustManager[]{
                            new X509TrustManager() {
                                @Override
                                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return new java.security.cert.X509Certificate[]{};
                                }
                            }
                    };
                    // Install the all-trusting trust manager
                    final SSLContext sslContext;
                    try {
                        sslContext = SSLContext.getInstance("SSL");
                        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                        // Create an ssl socket factory with our all-trusting manager
                        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                        okhttpBuilder.sslSocketFactory(sslSocketFactory);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                okhttpBuilder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            }
            if (getRetrofitBuilder().isDebug()) {
                // Log信息拦截器
                if (interceptor == null) {
                    interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                }
                //设置Debug Log 模式
                okhttpBuilder.addInterceptor(interceptor);
            }
            //设置超时
            okhttpBuilder.connectTimeout(getRetrofitBuilder().getConnectTimeout(), TimeUnit.SECONDS);
            okhttpBuilder.readTimeout(getRetrofitBuilder().getReadTimeout(), TimeUnit.SECONDS);
            okhttpBuilder.writeTimeout(getRetrofitBuilder().getWriteTimeout(), TimeUnit.SECONDS);
            //以上设置结束，才能build(),不然设置白搭
            OkHttpClient okHttpClient = okhttpBuilder.build();
            return okHttpClient;
        }
    }

    /**
     * 获取RetrofitBuilder，如果为null，则新建一个空的
     *
     * @return RetrofitBuilder
     */
    public static RetrofitBuilder getRetrofitBuilder() {
        if (retrofitBuilder != null) {
            return retrofitBuilder;
        } else {
            retrofitBuilder = new RetrofitBuilder.Builder().builder();
            return retrofitBuilder;
        }
    }
}
