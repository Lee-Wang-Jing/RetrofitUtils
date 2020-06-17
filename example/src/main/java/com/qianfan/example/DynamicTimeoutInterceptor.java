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
        Request request=chain.request();
        //核心代码!!!
        final Invocation tag = request.tag(Invocation.class);
        final Method method = tag != null ? tag.method() : null;
        final DynamicTimeOut timeout = method != null ? method.getAnnotation(DynamicTimeOut.class) : null;

        Log.d("invocation",tag!= null ? tag.toString() : "");

        if(timeout !=null && timeout.timeout() > 0){
            Response proceed = chain
                    .withConnectTimeout(timeout.timeout(), TimeUnit.SECONDS)
                    .withReadTimeout(timeout.timeout(), TimeUnit.SECONDS)
                    .withWriteTimeout(timeout.timeout(), TimeUnit.SECONDS)
                    .proceed(request);
            return proceed;
        }

        return chain.proceed(request);
    }
}
