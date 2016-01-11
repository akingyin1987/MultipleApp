/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2012-2015 Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.net.Uri;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.md.multipleapp.BuildConfig;
import com.md.multipleapp.UserEntity;
import com.provider.base.BaseContentProvider;
import com.provider.config.UserEneityColumns;


public class SampleProvider extends BaseContentProvider {
    private static final String TAG = SampleProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "org.jraf.androidcontentprovidergenerator.sample.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_USER = 0;
    private static final int URI_TYPE_USER_ID = 1;





    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, UserEneityColumns.TABLE_NAME, URI_TYPE_USER);
        URI_MATCHER.addURI(AUTHORITY,UserEneityColumns.TABLE_NAME+"/#",URI_TYPE_USER_ID);//#号为通配符

    }



    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }


    /**
     * 该方法用于返回当前Url所代表数据的MIME类型。  
     * 如果操作的数据属于集合类型，那么MIME类型字符串应该以vnd.android.cursor.dir/开头  
     * 如果要操作的数据属于非集合类型数据，那么MIME类型字符串应该以vnd.android.cursor.item/开头  
     * @param uri
     * @return
     */
    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_USER:
                return TYPE_CURSOR_DIR + UserEneityColumns.TABLE_NAME;
            case URI_TYPE_USER_ID:
                return TYPE_CURSOR_ITEM + UserEneityColumns.TABLE_NAME;



        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_USER:
                res.table = UserEneityColumns.TABLE_NAME;
                res.idColumn = UserEneityColumns._ID;
                System.out.println("ID="+UserEneityColumns._ID);
                res.orderBy = UserEneityColumns.DEFAULT_ORDER;
                break;
            case URI_TYPE_USER_ID:
                res.table = UserEneityColumns.TABLE_NAME;
                res.idColumn = UserEneityColumns._ID;
                res.orderBy = UserEneityColumns.DEFAULT_ORDER;
                break;



            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_USER_ID:
                id = uri.getLastPathSegment();
                break;
            case URI_TYPE_USER:


        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }

    @Override
    protected SQLiteDatabase createSqLiteOpenHelper() {
        System.out.println("provider Create");
        if( null != getContext()){
            Configuration cfg = new Configuration.Builder(getContext())
                .addModelClass(UserEntity.class)
                .create();
            ActiveAndroid.initialize(cfg,true);
            return ActiveAndroid.getDatabase();
        }
        return  null;

    }
}
