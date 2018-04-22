package us.xingkong.starlive.module.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.widget.Toast;

import java.util.List;

import us.xingkong.starlive.R;
import us.xingkong.starlive.adapter.ItemAdapter;
import us.xingkong.starlive.base.BasePresenterImpl;
import us.xingkong.starlive.module.push.PushActivity;
import us.xingkong.starlive.utils.PreferenceUtil;
import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.model.StatusResult;
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
    public void refreshData(Client client) {
//获取直播信息
        client.getApps(new ResultListener<AppsResult>() {
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
                mView.stopRefreshing();
            }
        });
    }

    @Override
    public void checkLoginState(Client client) {
        client.checkLoginStatus(new ResultListener<StatusResult>() {
            @Override
            public void onFinish(StatusResult result) {
                if (result.getStatus() == 200) {
                    Toast.makeText(mView.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                    PreferenceUtil.saveLoginstatus(mView.getContext(), true,
                            result.getUserinfo().getNickname(),
                            result.getUserinfo().getUsername());
                    mView.setNickname(result.getUserinfo().getNickname());
//                    intent.putExtra("nickname", result.getUserinfo().getNickname());
                } else if (result.getStatus() == 403) {
                    Toast.makeText(mView.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onFinal() {

            }
        });
    }

    @Override
    public void logout(Client client) {
        client.logout();
        PreferenceUtil.saveLoginstatus(mView.getContext(), false, null, null);
        mView.setNickname(R.string.pls_press_to_login);
    }
}
