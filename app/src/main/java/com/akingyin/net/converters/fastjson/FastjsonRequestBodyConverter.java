package com.akingyin.net.converters.fastjson;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * Created by zlcd on 2016/2/15.
 */
final class FastjsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    public FastjsonRequestBodyConverter(JSON gson, TypeReference<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    private final JSON gson;
    private final TypeReference<T> adapter;
    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JSONWriter   jsonWriter = new JSONWriter(writer);


        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}
