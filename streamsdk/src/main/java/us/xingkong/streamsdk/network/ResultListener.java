package us.xingkong.streamsdk.network;

/**
 * Created by 饶翰新 on 2017/12/20.
 * <p>
 * 结果回调监听器
 */

public interface ResultListener<T> {
    void onDone(T result, Exception e);
}
