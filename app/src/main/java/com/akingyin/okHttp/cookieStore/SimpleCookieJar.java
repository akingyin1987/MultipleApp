package com.akingyin.okHttp.cookieStore;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by zlcd on 2016/1/30.
 */
public final  class SimpleCookieJar  implements CookieJar {

    private final List<Cookie> allCookies = new ArrayList<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        allCookies.addAll(cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> result = new ArrayList<>();
        for (Cookie cookie : allCookies)
        {
            if (cookie.matches(url))
            {
                result.add(cookie);
            }
        }
        return result;
    }
}
