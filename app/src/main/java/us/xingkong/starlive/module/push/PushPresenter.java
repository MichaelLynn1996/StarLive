package us.xingkong.starlive.module.push;

import us.xingkong.starlive.base.BasePresenterImpl;

public class PushPresenter extends BasePresenterImpl implements PushContract.Presenter {

    private PushContract.View mView;

    PushPresenter(PushContract.View view){
        mView = view;
        this.mView.setPresenter(this);
    }
}
