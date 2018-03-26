package us.xingkong.streamsdk.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by SeaLynn0 on 2018/2/3.
 */

public class GetAppResult extends Result {

    private String src;

    private boolean alive;

    private String appname;

    private String title;

    private String maintext;

    private String token;

    private int id;

    private String username;

    public GetAppResult() {
        super();
        src = appname = title = maintext = token = username = "";
    }

    public GetAppResult(String result) throws JSONException {
        this();
        Log.d(TAG, "Result: " + result);
        setResult(result);
        JSONObject json = new JSONObject(result);
        setStatus(json.getInt("status"));
        setMsg(json. getString("msg"));

        JSONObject getResult = null;
        if (this.getStatus()==403||this.getStatus()==200) {
            setSrc(json.getString("src"));
            setAlive(json.getBoolean("alive"));

            getResult = new JSONObject(json.getString("result"));
            setAppname(getResult.getString("appname"));
            setTitle(getResult.getString("title"));
            setMaintext(getResult.getString("maintext"));
        }

        if (this.getStatus()==200&&getResult!=null){
            setId(getResult.getInt("id"));
            setToken(getResult.getString("token"));
            setUsername(getResult.getString("username"));
        }
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
