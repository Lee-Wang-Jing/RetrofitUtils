package com.qianfan.example;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Invocation;

public class DynamicTimeoutInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //核心代码!!!
        final Invocation tag = request.tag(Invocation.class);
        final Method method = tag != null ? tag.method() : null;
        final DynamicTimeOut timeout = method != null ? method.getAnnotation(DynamicTimeOut.class) : null;

        Log.d("invocation", tag != null ? tag.toString() : "");
        int callTimeOut = 0;
        if (timeout != null && timeout.timeout() > 0) {
            callTimeOut = timeout.timeout();
        } else {
            callTimeOut = 30;
        }
        chain.call().timeout().timeout(callTimeOut, TimeUnit.SECONDS);
        Response proceed = chain
                .withConnectTimeout(callTimeOut, TimeUnit.SECONDS)
                .withReadTimeout(callTimeOut, TimeUnit.SECONDS)
                .withWriteTimeout(callTimeOut, TimeUnit.SECONDS)
                .proceed(request);
        return proceed;
    }
}
