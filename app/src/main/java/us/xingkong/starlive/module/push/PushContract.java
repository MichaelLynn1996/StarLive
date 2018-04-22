package us.xingkong.starlive.module.push;

import us.xingkong.starlive.base.BasePresenter;
import us.xingkong.starlive.base.BaseView;

public interface PushContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
