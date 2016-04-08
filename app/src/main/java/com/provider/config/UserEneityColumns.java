package com.provider.config;

import android.net.Uri;
import android.provider.BaseColumns;

import com.provider.SampleProvider;


/**
 * Created by zlcd on 2016/1/11.
 */
public class UserEneityColumns  implements BaseColumns {

    public static final String TABLE_NAME = "tb_user";
    public static final Uri CONTENT_URI = Uri.parse(SampleProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;


    public static final String NAME = "name";


    public static final String AGE = "age";



    public static final String DEFAULT_ORDER = _ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
        _ID,
        NAME,
        AGE,

    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(AGE) || c.contains("." + AGE)) return true;

        }
        return false;
    }



}
