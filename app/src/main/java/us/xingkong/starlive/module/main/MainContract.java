package us.xingkong.starlive.module.main;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;

import java.util.List;

import us.xingkong.starlive.adapter.ItemAdapter;
import us.xingkong.starlive.base.BasePresenter;
import us.xingkong.starlive.base.BaseView;
import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.Client;

/**
 * Created by SeaLynn0 on 2018/3/26.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void stopRefreshing();
        void onRefreshDataFinish(AppsResult result);
        void setNickname(String nickname);
        void setNickname(int resId);
    }

    interface Presenter extends BasePresenter {
        void refreshData(Client client);
        void checkLoginState(Client client);
        void logout(Client client);
    }
}
