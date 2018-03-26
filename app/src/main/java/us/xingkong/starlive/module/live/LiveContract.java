package us.xingkong.starlive.module.live;

import us.xingkong.starlive.base.BasePresenter;
import us.xingkong.starlive.base.BaseView;

/**
 * Created by SeaLynn0 on 2018/3/27.
 */

public interface LiveContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
