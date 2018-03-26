package us.xingkong.streamsdk.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by 饶翰新 on 2017/12/20.
 * <p>
 * 通用结果模型
 */

public class Result {

    private int status;

    private String msg;

    public String result;

    public Result() {
        msg = "";
        result = "";
    }

    public Result(String result) throws JSONException {
        this();
        Log.d(TAG, "Result: " + result);
        setResult(result);
        JSONObject json = new JSONObject(result);
        setStatus(json.getInt("status"));
        if (json.optString("msg") != null)
            setMsg(json.optString("msg"));
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
