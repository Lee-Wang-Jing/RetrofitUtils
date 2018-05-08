# RetrofitUtils [![](https://ci.novoda.com/buildStatus/icon?job=bintray-release)](https://ci.novoda.com/job/bintray-release/lastBuild/console) [![Download](https://api.bintray.com/packages/wangjinggm/maven/retrofitutils/images/download.svg) ](https://bintray.com/wangjinggm/maven/retrofitutils/_latestVersion) [![license](http://img.shields.io/badge/license-Apache2.0-brightgreen.svg?style=flat)](https://github.com/Lee-Wang-Jing/RetrofitUtils/blob/master/LICENSE)

技术交流群：598627802，加群前请务必阅读[群行为规范](https://github.com/Lee-Wang-Jing/GroupStandard)
有问题或者某种需求欢迎加群或者提issues，Thanks
RetrofitUtils是Retrofit的封装工具类，方便开发使用，方便快捷

# Dependencies
* Gradle
```groovy
compile 'com.wangjing:retrofitutils:0.0.13'
```
* Maven
```xml
<dependency>
  <groupId>com.wangjing</groupId>
  <artifactId>retrofitutils</artifactId>
  <version>0.0.13</version>
  <type>pom</type>
</dependency>
```

* Eclipse ADT请放弃治疗

- **初始化 RetrofitUtils**

```
//比如要在所有请求的Header中添加User-Agent
HashMap<String, String> headerHashMap = new HashMap<>();
headerHashMap.put("User-Agent", AppConfig.USERAGENT.replaceAll(" ", ""));

RetrofitBuilder retrofitBuilder = new RetrofitBuilder.Builder()
        .baseUrl("" + AppUrls.URL_HOST)//设置BaseUrl
        .connectTimeout(30)//设置connectTimeout
        .writeTimeout(30)//设置writeTimeout
        .readTimeout(30)//设置readTimeout
        .headerHashMap(headerHashMap)//设置网络请求通用的Header
        .setDebug(true)//设置是否是debug模式，debug模式则会输出日志
        .builder();

RetrofitUtils.initialize(retrofitBuilder);
```

- **如何使用 How to use**

```
1、声明Call
retrofit2.Call<T> call;
```

```
2、书写接口
public interface AdService {
    /**
     * 获取启动广告
     * @return data
     */
    @POST("site/start-ad")
    Call<T> getStartAd();
}
```

```
3、创建接口
call = RetrofitUtils.creatBaseApi(AdService.class).getStartAd();
```


```
4、发起请求

异步请求
call.enqueue(new Callback<T>() {
    @Override
    public void onResponse(retrofit2.Call<T> call, Response<T> response) {
    }
    @Override
    public void onFailure(retrofit2.Call<T> call, Throwable t) {
    }
});

同步请求
try {
    Response<T> response = call.execute();
    Log.d(TAG, "response:" + response.body().toString());
} catch (IOException e) {
    e.printStackTrace();
}
```

- **RetrofitUtils中方法介绍**
    - 初始化RetrofitUtils
        - RetrofitUtils.initialize(builder)

    - 创建带有初始化的时候设置的默认BaseUrl的serviceClass
        - RetrofitUtils.creatBaseApi(AdService.class)

    - 由于RetrofitUtils中的Retrofit只有一个实例，所以如果期间修改了初始化时填写的全局header或者超时时间等配置，则需要以下方法

        - RetrofitUtils.creatNewBaseApi(AdService.class)

    - 创建不带有默认BaseUrl的serviceClass，记住使用了creatNoBaseUrlApi方法后如果再次使用creatBaseApi是无效的，因为retrofit不为null，需要clearRetrofit
        - RetrofitUtils.creatNoBaseUrlApi(AdService.class)

    - 清空RetrofitUtils中的Retrofit实例
        - RetrofitUtils.clearRetrofit

    - 清空RetrofitUtils中的clearOkhttpBuilder实例
        - RetrofitUtils.clearOkhttpBuilder

    - 清空RetrofitUtils中的clearOkhttpBuilder和Retrofit实例
        - RetrofitUtils.clearAll

    - 获取初始化时设置的RetrofitBuilder，如果为null，则新建一个空的
    
## **Retrofit参数介绍**    

- @Path
    - 所有在网址中的参数（URL的问号前面），如： 
          http://102.10.10.132/api/Accounts/{accountId} 

- @Query
    - URL问号后面的参数，如： 
           http://102.10.10.132/api/Comments?access_token={access_token} 

- @QueryMap
    - 相当于多个@Query 
    
- @Field
    - 用于POST请求，提交单个数据 
    
- @FieldMap
    - 用于POST请求，提交多个数据  
    
- @Body
    - 相当于多个@Field，以对象的形式提交
        
## **Tips** 
- Tip1  
    - 使用@Field时记得添加@FormUrlEncoded

- Tip2 
    - 若需要重新定义接口地址，可以使用@Url，将地址以参数的形式传入即可。如

```
@GET
Call<List<Activity>> getActivityList(
        @Url String url,
        @QueryMap Map<String, String> map);
```

```
Call<List<Activity>> call = service.getActivityList(
            "http://115.159.198.162:3001/api/ActivitySubjects", map);
```



        
