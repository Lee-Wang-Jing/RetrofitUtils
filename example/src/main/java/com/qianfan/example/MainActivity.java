package com.qianfan.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wangjing.retrofitutils.RetrofitBuilder;
import com.wangjing.retrofitutils.RetrofitUtils;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_getallname, btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_getallname = findViewById(R.id.btn_getallname);
        btn_search = findViewById(R.id.btn_search);
        btn_getallname.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        RetrofitBuilder retrofitBuilder = new RetrofitBuilder.Builder()
                .baseUrl("https://www.wanandroid.com")//设置BaseUrl
                .connectTimeout(30)//设置connectTimeout
                .writeTimeout(30)//设置writeTimeout
                .readTimeout(30)//设置readTimeout
                .setIsRetry(true)//设置请求失败是否会重试
                .setDebug(true)//设置是否是debug模式，debug模式则会输出日志
                .builder();
        RetrofitUtils.getInstance().initialize(retrofitBuilder);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getallname:
                getAllName();
                break;
            case R.id.btn_search:
                getSearchData();
                break;

            default:
                break;
        }
    }

    private void getSearchData() {
//        RetrofitUtils.getInstance().setOkHttpClient(RetrofitUtils.getInstance().getHttpClient().newBuilder().readTimeout(10, TimeUnit.SECONDS)
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .build());
        RetrofitUtils.getInstance().getHttpClient().newBuilder().readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Log.e("getSearchData", "readTimeoutMillis==>" + RetrofitUtils.getInstance().getHttpClient().readTimeoutMillis());
        Log.e("getSearchData", "readTimeoutMillis==>" + RetrofitUtils.getInstance().getHttpClient().connectTimeoutMillis());
        Log.e("getSearchData", "readTimeoutMillis==>" + RetrofitUtils.getInstance().getHttpClient().writeTimeoutMillis());

        Call<String> call = RetrofitUtils.getInstance().creatBaseApi(WanAndroidService.class).search("viewpager2");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    private void getAllName() {
        Call<String> call = RetrofitUtils.getInstance().creatBaseApi(WanAndroidService.class).getMavenPom();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}
