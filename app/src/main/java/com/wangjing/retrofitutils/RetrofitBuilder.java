package com.wangjing.retrofitutils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import retrofit2.Converter;

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
    //headerHashMap header的键值对集合
    private HashMap<String, String> headerHashMap;
    private HashMap<String, String> addHeaderHashMap;
    private HashMap<String, String> headersHashMap;
    private Interceptor interceptor;
    private Converter.Factory factory;
    private SSLSocketFactory sslSocketFactory;

    private List<Protocol> protocols;

    private boolean isDebug;

    private boolean isTrustSSL = false;

    private RetrofitBuilder() {
    }

    public String getBaseUrl() {
        return TextUtils.isEmpty(baseUrl) ? "" : baseUrl;
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


    public HashMap<String, String> getHeaderHashMap() {
        if (headerHashMap == null) {
            headerHashMap = new HashMap<>();
        }
        return headerHashMap;
    }

    public HashMap<String, String> getAddHeaderHashMap() {
        if (addHeaderHashMap == null) {
            addHeaderHashMap = new HashMap<>();
        }
        return addHeaderHashMap;
    }

    public HashMap<String, String> getHeadersHashMap() {
        if (headersHashMap == null) {
            headersHashMap = new HashMap<>();
        }
        return headersHashMap;
    }

    public void setAddHeaderHashMap(HashMap<String, String> addHeaderHashMap) {
        this.addHeaderHashMap = addHeaderHashMap;
    }

    public void setHeadersHashMap(HashMap<String, String> headersHashMap) {
        this.headersHashMap = headersHashMap;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public Converter.Factory getFactory() {
        return factory;
    }

    public void setFactory(Converter.Factory factory) {
        this.factory = factory;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public void setHeaderHashMap(HashMap<String, String> headerHashMap) {
        this.headerHashMap = headerHashMap;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public boolean isTrustSSL() {
        return isTrustSSL;
    }

    public void setTrustSSL(boolean trustSSL) {
        isTrustSSL = trustSSL;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    public List<Protocol> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<Protocol> protocols) {
        this.protocols = protocols;
    }

    public static class Builder {
        /**
         * 请求的baseUrl
         */
        private String baseUrl;
        private int connectTimeout;
        private int readTimeout;
        private int writeTimeout;
        //headerHashMap header的键值对集合
        private HashMap<String, String> headerHashMap;
        private HashMap<String, String> addHeaderHashMap;
        private HashMap<String, String> headersHashMap;

        private boolean isDebug;
        private boolean isTrustSSL;

        private Interceptor interceptor;
        private Converter.Factory factory;
        private SSLSocketFactory sslSocketFactory;

        private List<Protocol> protocols;


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
         * @return Builder
         */
        public Builder connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 设置网络请求的读取超时时间
         *
         * @param readTimeout 超时秒数
         * @return Builder
         */
        public Builder readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * 设置网络请求的写入超时时间
         *
         * @param writeTimeout 超时秒数
         * @return Builder
         */
        public Builder writeTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        /**
         * 设置网络请求的Header 会移除和当前设置的key相同的所有header，然后添加进当前设置的key value 的header
         *
         * @param headerHashMap headerHashMap
         * @return Builder
         */
        public Builder headerHashMap(HashMap<String, String> headerHashMap) {
            this.headerHashMap = headerHashMap;
            return this;
        }


        /**
         * 设置网络请求的Header  不会移除现有的Header，即使相同的key的header存在，也不会移除或者覆盖，会新增一条新的key和value的header
         *
         * @param addHeaderHashMap headerHashMap
         * @return Builder
         */
        public Builder addHeaderHashMap(HashMap<String, String> addHeaderHashMap) {
            this.addHeaderHashMap = addHeaderHashMap;
            return this;
        }

        /**
         * 设置网络请求的Header  会移除所有的Header然后添加新的设置的所有的Headers
         *
         * @param headersHashMap headersHashMap
         * @return Builder
         */
        public Builder headersHashMap(HashMap<String, String> headersHashMap) {
            this.headersHashMap = headersHashMap;
            return this;
        }

        /**
         * 设置是否开启Debug，开启则会打印日志
         *
         * @param isDebug isDebug
         * @return Builder
         */
        public Builder setDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        /**
         * 设置是否信任所有SSL证书
         *
         * @param isTrustSSL isTrustSSL
         * @return Builder
         */
        public Builder setTrustSSL(boolean isTrustSSL) {
            this.isTrustSSL = isTrustSSL;
            return this;
        }

        /**
         * 设置拦截器
         *
         * @param interceptor Interceptor
         * @return Builder
         */
        public Builder setInterceptor(Interceptor interceptor) {
            this.interceptor = interceptor;
            return this;
        }

        /**
         * 支持自己设置ConverterFactory解析
         *
         * @param factory
         * @return Builder
         */
        public Builder setConverterFactory(Converter.Factory factory) {
            this.factory = factory;
            return this;
        }


        /**
         * 支持自己设置SSLSocketFactory
         *
         * @param sslSocketFactory
         * @return Builder
         */
        public Builder setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        /**
         * 设置http请求协议
         *
         * @param protocols
         * @return Builder
         */
        public Builder setProtocols(List<Protocol> protocols) {
            this.protocols = protocols;
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
        headerHashMap = b.headerHashMap;
        addHeaderHashMap = b.addHeaderHashMap;
        headersHashMap = b.headersHashMap;
        isDebug = b.isDebug;
        isTrustSSL = b.isTrustSSL;
        interceptor = b.interceptor;
        factory = b.factory;
        sslSocketFactory = b.sslSocketFactory;
        protocols = b.protocols;
    }

}
