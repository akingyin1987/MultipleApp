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

package com.akingyin.sharelibs.adapter.internal;

import java.util.List;

/**
 * <p>Select, insert, update, delete.</p>
 * Created by Cheney on 16/4/1.
 */
public interface CRUD<T> {
    void add(T item);

    void insert(int index, T item);

    void addAll(List<T> items);

    void remove(T item);

    void remove(int index);

    void set(T oldItem, T newItem);

    void set(int index, T item);

    void replaceAll(List<T> items);

    void addTop(T  item);

    void addAllTop(List<T>  items);

    boolean contains(T item);

    void clear();
}
