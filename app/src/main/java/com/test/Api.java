package com.test;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.test.interceptors.AHandler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okio.BufferedSink;

/**
 * Created by yuanwenfei on 2015/5/28.
 */
public class Api {
    private static Api ourInstance = new Api();

    public static Api getInstance() {
        return ourInstance;
    }

    private Api() {
    }

    public AHandler getUserInfo(String username){

        //get
        Request  request = new Request.Builder()
                .addHeader("key", "value")
                .addHeader("key1", "value1")
//                .cacheControl(CacheControl.FORCE_CACHE)//设置该请求使用缓存（第一种方式(默认)）
                .cacheControl(new CacheControl.Builder()//设置请求使用缓存(第二种方式（自定义）)
                        .onlyIfCached()
                        .maxStale(1000, TimeUnit.SECONDS)
                        .build())
                .url("")
                .get()
                .tag("tag1")
                .build();

        //post
        RequestBody requestBody = new RequestBody() {
            @Override public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }
            @Override public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("-------\n");
            }
        };
        Request  request2 = new Request.Builder()
                .addHeader("key", "value")
                .addHeader("key1", "value1")
                .url("")
                .post(requestBody)
                .tag("tag1")
                .build();

        //put
        RequestBody putBody = new FormEncodingBuilder()
                .add("search", "Jurassic Park")
                .addEncoded("s", "����")
                .build();
        Request  request3 = new Request.Builder()
                .addHeader("key", "value")
                .addHeader("key1", "value1")
                .url("")
                .put(putBody)
                .build();

        //delete
        RequestBody deleteBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"title\""),
                        RequestBody.create(null, "Square Logo"))
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"image\""),
                        RequestBody.create(MediaType.parse("image/png"), new File("website/static/logo-square.png")))
                .addFormDataPart("key", "value")
                .addFormDataPart("name", null, RequestBody.create(null, new File("")))
                .build();

        Request  request4 = new Request.Builder()
                .addHeader("key", "value")
                .addHeader("key1", "value1")
                .url("")
                .delete(deleteBody)
                .build();

        return new AHandler(request);

    }





}
