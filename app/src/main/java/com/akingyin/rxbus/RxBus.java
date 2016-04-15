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

package com.akingyin.rxbus;

import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Administrator on 2016/3/18.
 */
public class RxBus    {

    //PublishSubject仅会向Observer释放在订阅之后Observable释放的数据。
    private   final Subject<Object,Object>  publishBus = new SerializedSubject<>(PublishSubject.create());

    // AsyncSubject仅释放Observable释放的最后一个数据，并且仅在Observable完成之后。然而如果当Observable因为异常而终止，AsyncSubject将不会释放任何数据，但是会向Observer传递一个异常通知。
    private  final  Subject<Object,Object>  asyncBus = new SerializedSubject<>(AsyncSubject.create());

    //当Observer订阅了一个BehaviorSubject，它一开始就会释放Observable最近释放的一个数据对象，当还没有任何数据释放时，它则是一个默认值。接下来就会释放Observable释放的所有数据。
    // 如果Observable因异常终止，BehaviorSubject将不会向后续的Observer释放数据，但是会向Observer传递一个异常通知。
    private  final  Subject<Object,Object>  behaviorBus = new SerializedSubject<>(BehaviorSubject.create());

    //不管Observer何时订阅ReplaySubject，ReplaySubject会向所有Observer释放Observable释放过的数据。
   // 有不同类型的ReplaySubject，它们是用来限定Replay的范围，例如设定Buffer的具体大小，或者设定具体的时间范围。
   // 如果使用ReplaySubject作为Observer，注意不要在多个线程中调用onNext、onComplete和onError方法，因为这会导致顺序错乱，这个是违反了Observer规则的。
    private  final  Subject<Object,Object>  replayBus = new SerializedSubject<>(ReplaySubject.create());

}
