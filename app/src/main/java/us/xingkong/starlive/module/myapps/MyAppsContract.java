package us.xingkong.starlive.module.myapps;

import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import us.xingkong.starlive.adapter.ItemAdapter;
import us.xingkong.starlive.base.BasePresenter;
import us.xingkong.starlive.base.BaseView;
import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.Client;

public interface MyAppsContract {

    interface View extends BaseView<Presenter> {
        void stopRefreshing();
        void onRefreshDataFinish(AppsResult result);
    }

    interface Presenter extends BasePresenter {
        void getMyApps(Client client);
    }
}
