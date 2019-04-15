Table of contents
=================

<!--ts-->
      * [依赖](#依赖)
      * [未进行封装的使用方法](#未进行封装的使用方法)
         * [定义接口](#定义接口)
         * [创建Retrofit实例](#创建retrofit实例)
         * [执行请求](#执行请求)
      * [封装对返回数据的处理](#封装对返回数据的处理)
         * [使用Transform处理](#使用transform处理)
            * [compose操作符](#compose操作符)
            * [处理线程问题](#处理线程问题)
            * [处理返回结果](#处理返回结果)
            * [完整的一个请求](#完整的一个请求)
<!--te-->


## 依赖
```
api "io.reactivex.rxjava2:rxjava:2.2.8"
api 'io.reactivex.rxjava2:rxandroid:2.1.1'
api 'com.squareup.retrofit2:retrofit:2.5.0'
api 'com.squareup.retrofit2:converter-gson:2.5.0'
//用于返回Observable类型的call
api 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0'
```

## 未进行封装的使用方法

### 定义接口
```
方法返回改为Observable<>
public interface TranslateService {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<TranslationBean> getTranslationCall();
}
```

### 创建Retrofit实例
```
Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://fy.iciba.com/")
        .addConverterFactory(GsonConverterFactory.create())
        //这里是关键，添加这个Adapter后，返回的数据就变成了Observable
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
```

### 执行请求
获取Observable后对Observable进行观察。
```
TranslateService service = retrofit.create(TranslateService.class);
Observable<TranslationBean> observable = service.getTranslationCall();
observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<TranslationBean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(TranslationBean translationBean) {
                Log.d("xx", translationBean.toString());
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        });
```

## 封装对返回数据的处理
假设返回的数据结构为
```java
{
   int code;
   String msg;
   Data data;
}
```
业务最终关心的是Data的内容，所以要增加一层封装，来处理code和msg。

### 使用Transform处理
rxjava的设计宗旨之一就是对数据处理得到最终想要的数据结构。在rxjava中有一个专门的概念叫做Transformation。Transformation用于对Observable进行改造。

#### compose操作符
compose操作符用于做Observable到Observable的转变。与flatMap不同的是，compose操作的是上层的Observable，而flatMap操作的是发射的事件。

#### 处理线程问题
```
public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
    return observable -> observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
}
```
链式调用
```
ServiceCreater.createService(TranslateService.class)
        .getTranslationCall()
        .compose(RxApiHelper.rxSchedulerHelper())
        .subscribe(new Observer<TranslationBean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(TranslationBean translationBean) {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        });
```

#### 处理返回结果

```java
{
   int code;
   String msg;
   Data data;
}
```
目标是从整个返回的数据中获取Data这个数据，并对code和msg进行处理。

定义一个基类
```
public class BaseApiBean<T> {
    private int code;
    private T content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
```

定义api时返回值使用基类
```
@GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
Observable<BaseApiBean<TranslationBean>> getTranslationCall();
```

为了能在Observable的层面操作，这里使用flatMap而不是map。
```
public static <T> ObservableTransformer<BaseApiBean<T>, T> handleResult() {
    return httpResponseObservable ->
            httpResponseObservable.flatMap((Function<BaseApiBean<T>, Observable<T>>) baseApiBean -> {
                if (baseApiBean.getCode() == 1) {
                    return createData(baseApiBean.getContent());
                } else {
                    return Observable.error(new ApiException(baseApiBean.getCode()));
                }
            });
}

private static <T> Observable<T> createData(final T t) {
    return Observable.create(emitter -> {
        try {
            emitter.onNext(t);
            emitter.onComplete();
        } catch (Exception e) {
            emitter.onError(e);
        }
    });
}
```
这里对compose传入的Observable处理。使用flatMap操作符，在回调中根据baseApiBean的code判断请求是否成功。<br>
成功的情况下创建一个新的Observable，把目标Bean发送出去。
<br>
失败的情况下抛出一个异常，携带服务器返回的code(也许还有message)。

#### 完整的一个请求
```
ServiceCreater.createService(TranslateService.class)
        .getTranslationCall()
        .compose(RxApiHelper.rxSchedulerHelper())
        .compose(RxApiHelper.handleResult())
        .subscribe(new Observer<TranslationBean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(TranslationBean translationBean) {
                Log.d("xx", translationBean.toString());
            }
            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException){
                    ApiException apiException = (ApiException) e;
                    int code = apiException.getCode();
                    Log.d("xx","code--->"+code);
                }
            }
            @Override
            public void onComplete() {
            }
        });
```