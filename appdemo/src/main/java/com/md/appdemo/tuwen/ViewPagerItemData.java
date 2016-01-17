package com.md.appdemo.tuwen;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/15.
 */
public class ViewPagerItemData  implements Serializable {

      public    String    itemName;

     public List<TuwenDataVo>   datas;

    public ViewPagerItemData() {
    }

    public ViewPagerItemData(String itemName, List<TuwenDataVo> datas) {
        this.itemName = itemName;
        this.datas = datas;
    }
}
