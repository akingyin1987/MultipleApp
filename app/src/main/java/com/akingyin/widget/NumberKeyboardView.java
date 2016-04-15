/*
 *
 *   Copyright (c) 2016 [akingyin@163.com]
 *
 *   Licensed under the Apache License, Version 2.0 (the "License”);
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.akingyin.widget;

import android.content.Context;

import android.support.v7.widget.CardView;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.md.multipleapp.R;

/**
 * Created by Administrator on 2016/4/9.
 */
public class NumberKeyboardView  extends CardView {
    public NumberKeyboardView(Context context) {
        super(context);
        initView(context);
    }

    public NumberKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NumberKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private  Context  mContext;

    Button btn_zero, btn_one, btn_two, btn_three, btn_four, btn_five, btn_six,
            btn_seven, btn_eight, btn_nine, btnC, btn_del;

    private   int   maxlength;

    public int getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    private   KeyboardListion   listion;

    public KeyboardListion getListion() {
        return listion;
    }

    public void setListion(KeyboardListion listion) {
        this.listion = listion;
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.number_keyboard, null);
        btn_zero = (Button)view.findViewById(R.id.btn_zero);
        btn_one = (Button)view.findViewById(R.id.btn_one);
        btn_two = (Button)view.findViewById(R.id.btn_two);
        btn_three = (Button)view.findViewById(R.id.btn_three);
        btn_four = (Button)view.findViewById(R.id.btn_four);
        btn_five = (Button)view.findViewById(R.id.btn_five);
        btn_six = (Button)view.findViewById(R.id.btn_six);
        btn_seven = (Button)view.findViewById(R.id.btn_seven);
        btn_eight = (Button)view.findViewById(R.id.btn_eight);
        btn_nine = (Button)view.findViewById(R.id.btn_nine);
        btnC = (Button)view.findViewById(R.id.btnC);
        btn_del = (Button)view.findViewById(R.id.btn_del);
        btn_zero.setOnClickListener(listener);
        btn_one.setOnClickListener(listener);
        btn_two.setOnClickListener(listener);
        btn_three.setOnClickListener(listener);
        btn_four.setOnClickListener(listener);
        btn_five.setOnClickListener(listener);
        btn_six.setOnClickListener(listener);
        btn_seven.setOnClickListener(listener);
        btn_eight.setOnClickListener(listener);
        btn_nine.setOnClickListener(listener);
        btn_del.setOnClickListener(listener);
        btnC.setOnClickListener(listener);
        this.addView(view);

    }

    public   void   setKeyboardEnable(boolean enable){
        btn_zero.setEnabled(enable);
        btn_one.setEnabled(enable);
        btn_two.setEnabled(enable);
        btn_three.setEnabled(enable);
        btn_four.setEnabled(enable);
        btn_five.setEnabled(enable);
        btn_six.setEnabled(enable);
        btn_seven.setEnabled(enable);
        btn_eight.setEnabled(enable);
        btn_nine.setEnabled(enable);
        btn_del.setEnabled(enable);
        btnC.setEnabled(enable);
    }



    public   interface    KeyboardListion{

       void onKeyboard(String  keyNum);

       void  onDelect();

       void  onClean();
    }


    public   OnClickListener     listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_del:
                     if(null != listion){
                         listion.onDelect();
                     }
                    break;
                case R.id.btnC:
                    if(null != listion){
                        listion.onClean();
                    }
                    break;
                default:
                    String   tag  = v.getTag().toString();
                    if(null != listion){
                        listion.onKeyboard(tag);
                    }

            }
        }
    };

}
