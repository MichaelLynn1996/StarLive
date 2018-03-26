package us.xingkong.streamsdk.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * Created by SeaLynn0 on 2018/1/14.
 *
 * 登陆状态结果的模型
 */

public class StatusResult extends Result {

    private UserInfo userinfo;

    public StatusResult(){
        super();
    }

    public StatusResult(String result) throws JSONException {
        this();
        Log.d(TAG, "Result: " + result);
        setResult(result);
        JSONObject json = new JSONObject(result);
        setStatus(json.getInt("status"));
        setMsg(json.getString("msg"));

        Log.d(TAG, "StatusResult: "+ json.optString("userinfo"));
        if (!Objects.equals(json.optString("userinfo"), "")){
            JSONObject jsonObject = new JSONObject(json.optString("userinfo"));
            userinfo = new UserInfo(jsonObject.optString("username"),jsonObject.optString("nickname"));
        }

    }

    class UserInfo {

        private String username;

        private String nickname;

        UserInfo(String username, String nickname){
            this.username = username;
            this.nickname = nickname;
        }

        public UserInfo() {
        }

        public String getUsername() {
            return username;
        }

        public String getNickname() {
            return nickname;
        }
    }
}
