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

package com.md.appdemo.jobmanager;


import com.md.appdemo.entity.UserEntity;

/**
 * Author:  king
 * Email:
 * Date:  2016/4/7 11:24
 * Description:
 */
public class TestJobManager  extends  AsyncJobManager<UserEntity> {

    public  TestJobManager(){
        super();
    }

    public  TestJobManager (int  nThreads,OnJobManagerListion  listion){
        super(nThreads,listion);
    }

}
