package com.md.appdemo.tuwen;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/15.
 */
public class ViewPagerDatas  implements Serializable {

    private    String   url;

    private List<ViewPagerItemData>   items;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ViewPagerItemData> getItems() {
        return items;
    }

    public void setItems(List<ViewPagerItemData> items) {
        this.items = items;
    }
}
