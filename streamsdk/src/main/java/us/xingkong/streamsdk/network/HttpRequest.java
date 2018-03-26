package us.xingkong.streamsdk.network;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static android.content.ContentValues.TAG;

/**
 * Http请求类
 */
public class HttpRequest {

    private Context context;

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求的get数据
     */
    private Map<String, String> get;

    /**
     * 请求的post数据
     */
    private Map<String, String> post;


    /**
     * 本次请求的结果
     */
    private String result;

    /**
     * 默认构造函数
     *
     * @param url 请求url
     */
    public HttpRequest(String url, Context context) {
        setUrl(url);
        this.context = context;
        this.get = new HashMap<>();
        this.post = new HashMap<>();
    }

    /**
     * 添加一个get数据
     *
     * @param key
     * @param value
     */
    public void get(String key, String value) {
        get.put(key, value);
    }

    /**
     * 获取某个get数据
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return get.get(key);
    }

    /**
     * 添加一组get数据
     *
     * @param keys
     * @param values
     */
    public void get(String[] keys, String[] values) {
        for (int i = 0; i < keys.length; i++) {
            get.put(keys[i], values[i]);
        }
    }

    /**
     * 添加一个post数据
     *
     * @param key
     * @param value
     */
    public void post(String key, String value) {
        post.put(key, value);
    }

    /**
     * 获取一个post数据
     *
     * @param key
     * @return
     */
    public String post(String key) {
        return post.get(key);
    }

    /**
     * 添加一组post数据
     *
     * @param keys
     * @param values
     */
    public void post(String[] keys, String[] values) {
        for (int i = 0; i < keys.length; i++) {
            post.put(keys[i], values[i]);
        }
    }

    /**
     * 生成本对象的完整url
     *
     * @return
     */
    public String parseURL() {
        StringBuilder result = new StringBuilder(getUrl());
        for (Entry<String, String> es : get.entrySet()) {
            result.append("&").append(es.getKey()).append("=").append(es.getValue());
        }
        return result.toString();
    }

    /**
     * 生成本对象的完整POST数据
     *
     * @return
     */
    public String parsePOST() {
        StringBuilder result = new StringBuilder();
        for (Entry<String, String> es : post.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(es.getKey()).append("=").append(es.getValue());
        }
        return result.toString();
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setCookie(String cookie) {
        /*
      请求时要提交的Cookie字符串
     */
        CookieUtil.saveCookiePreference(context, cookie);
    }

    public String getCookie() {
        return CookieUtil.getCookiePreference(context);
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
