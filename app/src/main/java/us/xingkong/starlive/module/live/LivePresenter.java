package us.xingkong.starlive.module.live;

import us.xingkong.starlive.base.BasePresenterImpl;
import us.xingkong.starlive.module.main.MainContract;

/**
 * Created by SeaLynn0 on 2018/3/27.
 */

public class LivePresenter extends BasePresenterImpl implements LiveContract.Presenter {

    private LiveContract.View mView;

    public LivePresenter(LiveContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
