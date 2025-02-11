package com.qianfan.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wangjing.retrofitutils.RetrofitBuilder;
import com.wangjing.retrofitutils.RetrofitUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String Tag = MainActivity.class.getSimpleName();

    private Button btn_getallname, btn_search, btn_logthread, btn_getallname_service, btn_search_service, btn_testBaseUrl;

    private WanAndroidService wanAndroidService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_getallname = findViewById(R.id.btn_getallname);
        btn_search = findViewById(R.id.btn_search);
        btn_logthread = findViewById(R.id.btn_logthread);
        btn_getallname_service = findViewById(R.id.btn_getallname_service);
        btn_search_service = findViewById(R.id.btn_search_service);
        btn_testBaseUrl = findViewById(R.id.btn_testBaseUrl);


        btn_getallname.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_logthread.setOnClickListener(this);
        btn_getallname_service.setOnClickListener(this);
        btn_search_service.setOnClickListener(this);
        btn_testBaseUrl.setOnClickListener(this);

        RetrofitBuilder retrofitBuilder = new RetrofitBuilder.Builder()
                .baseUrl("https://www.wanandroid.com")//设置BaseUrl
                .connectTimeout(30)//设置connectTimeout
                .writeTimeout(30)//设置writeTimeout
                .readTimeout(30)//设置readTimeout
                .callTimeout(30)//设置callTimeout
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
            case R.id.btn_getallname_service:
                getSearchData_Service();
                break;
            case R.id.btn_search_service:
                getAllName_Service();
                break;
            case R.id.btn_testBaseUrl:
                testBaseUrl();
                break;
            case R.id.btn_logthread:
                logAllThread();
                break;
            default:
                break;
        }
    }



    private void getSearchData() {
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
        Log.e("Tag","timeoutNanos==> "+call.timeout().timeoutNanos());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    public WanAndroidService getWanAndroidService() {
        if (wanAndroidService == null) {
            wanAndroidService = RetrofitUtils.getInstance().creatBaseApi(WanAndroidService.class);
        }
        return wanAndroidService;
    }

    private void getAllName_Service() {
        Call<String> call = getWanAndroidService().getMavenPom();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void getSearchData_Service() {
        Call<String> call = getWanAndroidService().search("viewpager2");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    /**
     * 测试@Url注解 代替BaseUrl
     */
    private void testBaseUrl() {
        Call<String> call = RetrofitUtils.getInstance().creatBaseApi(WanAndroidService.class).getTop("https://api.apiopen.top/api/sentences");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    /**
     * 打印当前App所有的线程信息
     */
    private void logAllThread() {
        Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
        Log.e("albertThreadDebug", "all start==============================================");
        for (Map.Entry<Thread, StackTraceElement[]> entry : threadMap.entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] stackElements = entry.getValue();
            Log.e("albertThreadDebug", "name:" + thread.getName() + " id:" + thread.getId() + " thread:" + thread.getPriority() + " begin==========");
            for (int i = 0; i < stackElements.length; i++) {
                StringBuilder stringBuilder = new StringBuilder("    ");
                stringBuilder.append(stackElements[i].getClassName() + ".")
                        .append(stackElements[i].getMethodName() + "(")
                        .append(stackElements[i].getFileName() + ":")
                        .append(stackElements[i].getLineNumber() + ")");
                Log.e("albertThreadDebug", stringBuilder.toString());
            }
            Log.e("albertThreadDebug", "name:" + thread.getName() + " id:" + thread.getId() + " thread:" + thread.getPriority() + " end==========");
        }
        Log.e("albertThreadDebug", "all end==============================================");
    }



}
