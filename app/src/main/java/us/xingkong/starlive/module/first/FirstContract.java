package us.xingkong.starlive.module.first;

import android.content.Context;
import android.content.Intent;

import us.xingkong.starlive.base.BasePresenter;
import us.xingkong.starlive.base.BaseView;
import us.xingkong.streamsdk.network.Client;

public interface FirstContract {

    interface View extends BaseView<FirstContract.Presenter> {
    }

    interface Presenter extends BasePresenter{
        void showFirstPage(Intent intent);
//        void checkLoginState(Client client, Intent intent);
    }
}
