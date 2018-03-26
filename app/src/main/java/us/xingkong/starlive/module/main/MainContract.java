package us.xingkong.starlive.module.main;

import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import us.xingkong.starlive.adapter.ItemAdapter;
import us.xingkong.starlive.base.BasePresenter;
import us.xingkong.starlive.base.BaseView;
import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.network.Client;

/**
 * Created by SeaLynn0 on 2018/3/26.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
        void refreshData(Client client, final List<App> apps, final ItemAdapter adapter, final SwipeRefreshLayout refreshLayout);
    }
}
