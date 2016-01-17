package com.md.appdemo.tuwen;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/15.
 */
public class TuwenDataVo implements Serializable {

    public   String     data;

    public   int     style;//1=文字 2=图片 3=视频

    public TuwenDataVo() {
    }

    public TuwenDataVo(String data, int style) {
        this.data = data;
        this.style = style;
    }
}
