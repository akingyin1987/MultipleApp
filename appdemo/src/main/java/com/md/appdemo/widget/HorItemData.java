package com.md.appdemo.widget;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zlcd on 2016/1/15.
 */
public class HorItemData  implements Serializable {

    public     String   itemName;

    private    List<String>    itemUrl;

    public List<String> getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(List<String> itemUrl) {
        this.itemUrl = itemUrl;
    }
}
