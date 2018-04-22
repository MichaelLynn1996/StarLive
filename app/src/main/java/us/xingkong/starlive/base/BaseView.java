package us.xingkong.starlive.base;

import android.app.Activity;
import android.content.Context;

/**
 * Created by SeaLynn0 on 2018/3/26.
 */

public interface BaseView<P> {
    void setPresenter(P presenter);

    Context getContext();

    Activity getActivity();
}
