package us.xingkong.starlive.module.login;

import us.xingkong.starlive.base.BasePresenter;
import us.xingkong.starlive.base.BaseView;
import us.xingkong.starlive.module.main.MainContract;
import us.xingkong.streamsdk.network.Client;

public interface LoginContract {

    interface View extends BaseView<LoginContract.Presenter> {
        void freeze();
        void unFreeze();
    }

    interface Presenter extends BasePresenter {
        void login(Client client,String username,String password);
    }
}
