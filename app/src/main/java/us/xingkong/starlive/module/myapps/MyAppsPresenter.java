package us.xingkong.starlive.module.myapps;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import java.util.List;

import us.xingkong.starlive.adapter.ItemAdapter;
import us.xingkong.starlive.base.BasePresenterImpl;
import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.Client;
import us.xingkong.streamsdk.network.ResultListener;

class MyAppsPresenter extends BasePresenterImpl implements MyAppsContract.Presenter {

    MyAppsContract.View mView;

    MyAppsPresenter(MyAppsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getMyApps(Client client) {
        client.getUserApps(new ResultListener<AppsResult>() {
            @Override
            public void onFinish(AppsResult result) {
                mView.onRefreshDataFinish(result);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onFinal() {
                if (!mView.getActivity().isDestroyed())
                    mView.stopRefreshing();
            }
        });
    }
}
