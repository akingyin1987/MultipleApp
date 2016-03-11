package com.akingyin.okHttp;



import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;



import com.akingyin.okHttp.cookieStore.SimpleCookieJar;
import com.facebook.stetho.okhttp3.StethoInterceptor;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by zlcd on 2015/12/29.
 */
public class OkHttpUtils {

    private static OkHttpClient singleton;

    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("application/octet-stream");
    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");

    /**
     * 创建文件类
     *
     * @param file
     * @return
     */
    public static RequestBody CreateFileBody(@NonNull File file) {

        RequestBody rb = RequestBody.create(MEDIA_TYPE_JPG, file);
        return rb;
    }

    /**
     * 非文件类
     *
     * @param value
     * @return
     */
    public static RequestBody CreateTextBody(@NonNull String value) {
        return RequestBody.create(MEDIA_TYPE_TEXT, value);
    }

    /**
     * 创建form表单
     *
     * @param params
     * @param encodedKey
     * @param encodedValue
     * @return
     */
    public static RequestBody createRequestBody(@NonNull Map<String, String> params, String encodedKey, String encodedValue) {

        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                formEncodingBuilder.add(key, params.get(key));
            }
        }
        if (!TextUtils.isEmpty(encodedKey) && !TextUtils.isEmpty(encodedValue)) {
            formEncodingBuilder.addEncoded(encodedKey, encodedValue);
        }
        return formEncodingBuilder.build();
    }


    /**
     * 创建上多文件上传数据
     * @param files 文件路径
     * @param filepart  文件属性
     * @param params  普通参数
     * @return
     */
    public static   RequestBody  createRequestBody(@NonNull List<String>  files,@NonNull String filepart,@Nullable Map<String,String> params){
        MultipartBody.Builder  builder = new MultipartBody.Builder();
        for(String path : files){
            try {
                File   file = new File(path);
                builder.addFormDataPart(filepart,file.getName(),CreateFileBody(file));
            }catch (Exception e){

            }
        }
        if(null != params){
            for(String  key : params.keySet()){
                builder.addFormDataPart(key,params.get(key));
            }
        }

        return  builder.build();
    }

    public static RequestBody createRequestBody(@NonNull Map<String, String> params) {
        return createRequestBody(params, null, null);
    }


    /**
     * 该不会开启异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Call request) throws IOException {
        return request.execute();
    }

    /**
     * 不开异步且可回调
     *
     * @param request
     * @param responseCallback
     */
    public static boolean execute(@NonNull Request request, @Nullable Callback responseCallback) {
        Call call = null;
        try {
            call = singleton.newCall(request);
            Response response = execute(call);
            if (null != responseCallback) {
                responseCallback.onResponse(call, response);
            }
            return true;
        } catch (Exception e) {
            if (null != responseCallback) {
                responseCallback.onFailure(call, new IOException());
            }
            return false;
        }

    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        singleton.newCall(request).enqueue(responseCallback);
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果
     *
     * @param request
     */
    public static void enqueue(Request request) {
        singleton.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


    /**
     * 获取请求
     *
     * @param url
     * @param method
     * @param requestBody
     * @param header
     */
    public static Request request(@NonNull String url, @NonNull HttpMethod method, @Nullable RequestBody requestBody,
                                  @Nullable Headers header) throws IOException {
        Request.Builder builder = new Request.Builder()
            .url(url);

        if (null != header) {
            builder.headers(header);
        }
        switch (method) {
            case GET:
                return builder.get().build();

            case POST:
                return builder.post(requestBody).build();
            case PUT:
                return builder.put(requestBody).build();
            case DELETE:
                return builder.delete(requestBody).build();
        }
        return null;
    }

    /**
     * 同步post请求
     *
     * @param url
     * @param requestBody
     * @param callback
     * @return
     */
    public static boolean doPost(String url, RequestBody requestBody, Callback callback) {

        try {
            Request request = request(url, HttpMethod.POST, requestBody, null);
            return execute(request, callback);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean doPost(String url, Map<String, String> parmas, Callback callback) {
        RequestBody requestBody = createRequestBody(parmas);
        return doPost(url, requestBody, callback);

    }

    /**
     * 异步post请求
     *
     * @param url
     * @param requestBody
     * @param callback
     */
    public static void doAsyPost(String url, RequestBody requestBody, Callback callback) {
        try {
            Request request = request(url, HttpMethod.POST, requestBody, null);
            enqueue(request, callback);
        } catch (Exception e) {
            e.printStackTrace();
            if (null != callback) {
                callback.onFailure(null, new IOException());
            }
        }

    }


    /**
     * 同步get请求
     *
     * @param url
     * @param callback
     */
    public static void doGet(String url, Callback callback) {

        try {
            Request request = request(url, HttpMethod.GET, null, null);
            execute(request, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public   static HttpLoggingInterceptor    httpLoggingInterceptor ;

    static {
        httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    /**
     * 获取OkHttp实例
     *
     * @return
     */
    public static OkHttpClient getInstance() {
        if (singleton == null) {
            synchronized (OkHttpUtils.class) {
                if (singleton == null) {
                    File cacheDir = new File(Config.HTTP_Cache_Dir, Config.RESPONSE_CACHE);

                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .addNetworkInterceptor(new StethoInterceptor())
                        .addInterceptor(httpLoggingInterceptor)
                        .cookieJar(new SimpleCookieJar())
                        .cache(new Cache(cacheDir, Config.RESPONSE_CACHE_SIZE))
                        .connectTimeout(Config.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(Config.HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
                    singleton = builder.build();
                }
            }

        }

        return singleton;
    }

    /**
     * 取消请求
     * @param tag
     */
    public synchronized static void cancelTag(Object tag) {
        if(null == singleton){
            return;
        }
        for (Call call : singleton.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
                break;
            }
        }
        for (Call call : singleton.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
                break;
            }
        }
    }

    /**
     * 取消正在进请的请求
     */
    public synchronized static  void  cancelRunningCalls(){
        if(null == singleton){
            return;
        }
        for (Call call : singleton.dispatcher().runningCalls()) {
            call.cancel();
        }
    }

    /**
     * 取消所有的请求
     */
    public  synchronized  static  void  cancelAllCalls(){
        if(null == singleton){
            return;
        }
        for (Call call : singleton.dispatcher().queuedCalls()) {
             call.cancel();
        }
        for (Call call : singleton.dispatcher().runningCalls()) {
              call.cancel();
        }
    }

}
