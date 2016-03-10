package com.akingyin.presenter.impl;

import android.os.Bundle;
import android.view.View;

import com.akingyin.presenter.IRxbindPresenter;
import com.akingyin.view.IRxBindView;

import org.apache.commons.lang.math.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zlcd on 2016/2/4.
 */
public class RxBindPresenterImpl implements IRxbindPresenter {

    private IRxBindView   rxBindView;

    public RxBindPresenterImpl(IRxBindView rxBindView) {
        this.rxBindView = rxBindView;
    }

    @Override
    public void initialize(Bundle savedInstanceState, View view) {
         rxBindView.initialize(savedInstanceState,view);
    }

    StringBuffer  sb = new StringBuffer();

    @Override
    public void rxEach() {

        List<Integer>   data = new ArrayList<>();
        for(int i=0;i<10;i++){
            data.add(RandomUtils.nextInt(100));
        }
        sb = new StringBuffer();

        Observable.from(data).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                System.out.println("filter="+integer);
                sb.append("filter="+integer);
                return integer % 2 == 0;
            }
        }).map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {

                sb.append("map="+integer);
                return null == integer?0:integer+1;
            }
        }).observeOn(AndroidSchedulers.mainThread())
            .finallyDo(new Action0() {
                @Override
                public void call() {
                    rxBindView.printMessage(sb.toString());
                }
            }).forEach(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                sb.append("each=" + integer);
            }
        });
    }

    @Override
    public void rxMap() {
        Observable.just(1).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
        final Subscriber<Integer>  subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {

            }
        };

        Observable<Integer> observable =Observable.just(1).map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                subscriber.onError(new Throwable());
                return integer;
            }
        });


        observable.subscribe(subscriber);
    }


}
