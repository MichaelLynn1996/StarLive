package us.xingkong.starlive.module.main;

import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import us.xingkong.starlive.adapter.ItemAdapter;
import us.xingkong.starlive.base.BasePresenterImpl;
import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.Client;
import us.xingkong.streamsdk.network.ResultListener;

/**
 * Created by SeaLynn0 on 2018/3/26.
 */

class MainPresenter extends BasePresenterImpl implements MainContract.Presenter {

    private MainContract.View mView;

    MainPresenter(MainContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void refreshData(Client client, final List<App>apps, final ItemAdapter adapter, final SwipeRefreshLayout refreshLayout) {
//获取直播信息
        client.getApps(new ResultListener<AppsResult>() {
            @Override
            public void onDone(AppsResult result, Exception e) {

                if (result != null) {
                    apps.clear();
                    //System.out.println(app.getAppname());
                    //if(app.isAlive())
                    apps.addAll(result.getApps());
                    adapter.notifyDataSetChanged();
                }

                if (e != null)
                    e.printStackTrace(System.err);
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
