package us.xingkong.streamsdk.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 饶翰新 on 2017/12/20.
 * <p>
 * Apps结果模型
 */

public class AppsResult extends Result {

    private String src;

    private List<App> apps;

    public AppsResult() {
        super();
        src = "";
        apps = new ArrayList<>();
    }

    public AppsResult(String result) throws JSONException {
        this();
        Log.d(TAG, "Result: " + result);
        setResult(result);
        JSONObject json = new JSONObject(result);
        setStatus(json.getInt("status"));
        if (json.optString("msg") != null)
            setMsg(json.optString("msg"));

        setSrc(json.getString("src"));

        JSONArray arr;
        if (json.optJSONArray("apps") != null) {
            arr = json.getJSONArray("apps");
        }else {
            arr = json.getJSONArray("result");
        }
        for (int i = 0; i < arr.length(); i++) {
            apps.add(new App(arr.getJSONObject(i)));
        }
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }

    public void setAppst(List<App> result) {
        this.apps = result;
    }

    public List<App> getApps() {
        return apps;
    }
}
