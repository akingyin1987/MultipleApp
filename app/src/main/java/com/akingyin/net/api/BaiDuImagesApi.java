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

package com.akingyin.net.api;


import com.akingyin.pojo.ImageListBean;



import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/3/10.
 */
public interface BaiDuImagesApi {



    @GET("/data/imgs")
    Observable<ImageListBean>   queryImages(@Query("col")String  category,@Query("tag")String tag,
                                        @Query("pn")int  pn,@Query("rn")int rn,@Query("from")int from);

}
