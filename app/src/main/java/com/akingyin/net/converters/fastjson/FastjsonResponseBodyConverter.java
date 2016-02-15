package com.akingyin.net.converters.fastjson;

import com.alibaba.fastjson.JSON;

import java.io.IOException;


import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by zlcd on 2016/2/15.
 */
public class FastjsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {

       private   Class<T>    t;

        public FastjsonResponseBodyConverter(Class<T> t) {
        this.t = t;
        }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
          return  JSON.parseObject(value.string(),t);
        }catch (Exception e){

        }
        return  null;
    }
}
