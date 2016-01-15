package com.md.appdemo.widget;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zlcd on 2016/1/15.
 */
public class HorItemDataS implements Serializable {

    private List<HorItemData>   itemDatas;

    public List<HorItemData> getItemDatas() {
        return itemDatas;
    }

    public void setItemDatas(List<HorItemData> itemDatas) {
        this.itemDatas = itemDatas;
    }
}
