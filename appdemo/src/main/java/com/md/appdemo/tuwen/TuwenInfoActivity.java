package com.md.appdemo.tuwen;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.md.appdemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/15.
 */
public class TuwenInfoActivity  extends AppCompatActivity{

    public String pictureName = "1.jpg";
    public String path = Environment.getExternalStorageDirectory().toString()
            + File.separator + "test" + File.separator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuweninfo);
        if(null == savedInstanceState){
            ViewPagerDatas  viewPagerDatas = new ViewPagerDatas();
            List<ViewPagerItemData>  dataList = new ArrayList<ViewPagerItemData>();
             for(int i=0;i<3;i++){
                 ViewPagerItemData   itemData = new ViewPagerItemData();
                 itemData.itemName="texttttt"+i;
                 List<TuwenDataVo>  tuwenDataVos = new ArrayList<TuwenDataVo>();
                 for(int k=0;k<7;k++){
                     TuwenDataVo  tuwenDataVo = new TuwenDataVo();
                     tuwenDataVo.style=2;
                     tuwenDataVo.data=path+pictureName;
                     tuwenDataVos.add(tuwenDataVo);
                 }
                 itemData.datas = tuwenDataVos;
                 dataList.add(itemData);

             }
            viewPagerDatas.setItems(dataList);
            getSupportFragmentManager().beginTransaction().add(R.id.tuwein_content,TuwenInfoFragment.newInstance(null,viewPagerDatas))
            .commit();
        }
    }
}
