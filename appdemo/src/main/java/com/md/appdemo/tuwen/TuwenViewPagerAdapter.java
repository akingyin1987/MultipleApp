package com.md.appdemo.tuwen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.md.appdemo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016/1/15.
 */
public class TuwenViewPagerAdapter  extends PagerAdapter {

   public Context  context;

    private List<TuwenDataVo>   itemDatas;

    private  String  uri;

    public   ViewHolder[]  mImageViews  = new ViewHolder[4];

    public TuwenViewPagerAdapter(@NonNull Context context,@NonNull List<TuwenDataVo> itemDatas, String uri) {
        this.context = context;
        this.itemDatas = itemDatas;
        this.uri = uri;
    }



    @Override
    public int getCount() {
        return itemDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewHolder   holder = mImageViews[position % mImageViews.length];
        if(null != holder){
            container.removeView(holder.view);
        }
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {


        ViewHolder   holder = mImageViews[position % mImageViews.length];
        TuwenDataVo  itemdata = itemDatas.get(position);
        if(null == holder){
            View   view = LayoutInflater.from(context).inflate(R.layout.tuwen_info_item,null);
            holder = new ViewHolder();
            holder.view = view;
            holder.imageView = (PhotoView)view.findViewById(R.id.tuwen_info_img);
            holder.videotag = (ImageView)view.findViewById(R.id.tuwen_info_tagvideo);
            holder.textView = (TextView)view.findViewById(R.id.tuwen_info_text);
            ((ViewPager)container).addView(view, 0);
            mImageViews[position % mImageViews.length]=holder;
        }else{
            ((ViewPager)container).removeView(holder.view);
            ((ViewPager)container).addView(holder.view, 0);
        }
        String   locurl;
        if(TextUtils.isEmpty(uri)){
            locurl="file://"+itemdata.data;
        }else{
            locurl=uri;
        }
        holder.bind(locurl,itemdata,context);
       return holder.view;
    }

    static   final  class  ViewHolder{
        ImageView    videotag;
        TextView     textView;
        PhotoView    imageView;
        View     view;

        public  void  bind(String  url,TuwenDataVo  dataVo,Context context){
            if(dataVo.style == 1){
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                videotag.setVisibility(View.GONE);
                textView.setText(dataVo.data);
            }else if(dataVo.style == 2){
                textView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                videotag.setVisibility(View.GONE);
                Picasso.with(context) //
                        .load(url) //
                        .placeholder(R.drawable.placeholder) //
                        .error(R.mipmap.ic_launcher) //
                        .fit() //
                        .tag(context) //
                        .into(imageView);
            }else if(dataVo.style == 3){
                textView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                videotag.setVisibility(View.VISIBLE);
                Picasso.with(context) //
                        .load(url) //
                        .placeholder(R.drawable.placeholder) //
                        .error(R.mipmap.ic_launcher) //
                        .fit() //
                        .tag(context) //
                        .into(imageView);
            }
        }
    }
}
