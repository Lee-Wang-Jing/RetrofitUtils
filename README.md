# RetrofitUtils [![](https://jitpack.io/v/Lee-Wang-Jing/RetrofitUtils.svg)](https://jitpack.io/#Lee-Wang-Jing/RetrofitUtils) [![license](http://img.shields.io/badge/license-Apache2.0-brightgreen.svg?style=flat)](https://github.com/Lee-Wang-Jing/RetrofitUtils/blob/master/LICENSE)

技术交流群：598627802，加群前请务必阅读[群行为规范](https://github.com/Lee-Wang-Jing/GroupStandard)

有问题或者某种需求欢迎加群或者提issues，Thanks

RetrofitUtils是Retrofit的封装工具类，方便开发使用，方便快捷

# Dependencies
* Gradle
```groovy
implementation 'com.github.Lee-Wang-Jing:RetrofitUtils:1.1.2'
```
* Maven
```xml
<dependency>
  <groupId>com.github.Lee-Wang-Jing</groupId>
  <artifactId>RetrofitUtils</artifactId>
  <version>1.1.2</version>
  <type>pom</type>
</dependency>
```
* Retrofit2 ProGuard

```
# Retrofit does reflection on generic parameters and InnerClass is required to use Signature.
-keepattributes Signature, InnerClasses
# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**
# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit
# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.-KotlinExtensions
```
您可能还需要OKHTTP和OKIO的规则，这些规则是依赖关系。

* OkHttp3 ProGuard
```
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*
# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
```
* OKIO ProGuard
```
-dontwarn okio.**
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
        .headerHashMap(headerHashMap)//设置网络请求通用的Header，会移除和当前设置的key相同的所有header，然后添加进当前设置的key value 的header
        .addHeaderHashMap(addHeaderHashMap)//不会移除现有的Header，即使相同的key的header存在，也不会移除或者覆盖，会新增一条新的key和value的header
        .headersHashMap(headersHashMap)//会移除所有的Header然后添加新的设置的所有的Headers
        .setDebug(true)//设置是否是debug模式，debug模式则会输出日志
        .builder();

RetrofitUtils.initialize(retrofitBuilder);
```

    如果同时设置了 headerHashMap、addHeaderHashMap和headersHashMap
    则优先顺序为 headersHashMap--headerHashMap--addHeaderHashMap
    只会设置一种

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

#### 添加动态、静态添加Header
- @Header
    -  动态添加Header
    ```
    @GET("/")
    Call<ResponseBody> foo(@Header("Accept-Language") String lang);
    ```  
- @HeaderMap 
    - 动态添加HeaderMap
    
    ```
    @GET("/search")
    Call<ResponseBody> list(@HeaderMap Map<String, String> headers);
    ```
##### 静态添加

```
@Headers("Cache-Control: max-age=640000")
@GET("/tasks")
Call<List<Task>> getDataList();

@Headers({
    "X-Foo: Bar",
    "X-Ping: Pong"
})
@GET("/")
Call(ResponseBody) getData(@Query("id") String id);
```
        
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

### 版本树
- 1.1.2版本发布 - 2018年12月12日
    - update logging-interceptor from 3.11.0 to 3.12.0
    - update retrofit frome 2.4.0 to 2.5.0
- 1.0.2版本发布 - 2018年9月19日
    - 增加JsonString转成JsonObject方法
- 1.0.1版本发布 - 2018年9月18日
    - 增加GsonUtils相关方法，方便使用
- 0.1.17版本发布
    - 升级'com.squareup.okhttp3:logging-interceptor:3.10.0' to 'com.squareup.okhttp3:logging-interceptor:3.11.0'





# License
```text
Copyright 2018 Wang Jing

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


        
