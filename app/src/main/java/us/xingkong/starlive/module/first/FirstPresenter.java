package us.xingkong.starlive.module.first;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import us.xingkong.starlive.base.BasePresenterImpl;
import us.xingkong.starlive.utils.PreferenceUtil;
import us.xingkong.streamsdk.model.StatusResult;
import us.xingkong.streamsdk.network.Client;
import us.xingkong.streamsdk.network.ResultListener;

public class FirstPresenter extends BasePresenterImpl implements FirstContract.Presenter {

    private FirstContract.View mView;

    FirstPresenter(FirstContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void showFirstPage(Intent intent) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mView.getContext().startActivity(intent);
        mView.getActivity().finish();
    }

//    @Override
//    public void checkLoginState(Client client, final Intent intent) {
//        client.checkLoginStatus(new ResultListener<StatusResult>() {
//            @Override
//            public void onFinish(StatusResult result) {
//                if (result.getStatus() == 200) {
//                    Toast.makeText(mView.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
//                    PreferenceUtil.saveLoginstatus(mView.getContext(),true,result.getUserinfo().getNickname());
////                    intent.putExtra("nickname", result.getUserinfo().getNickname());
//                } else if (result.getStatus() == 403) {
//                    Toast.makeText(mView.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onError(Exception e) {
//                e.printStackTrace();
//                Toast.makeText(mView.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFinal() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                mView.getContext().startActivity(intent);
//                mView.getActivity().finish();
//            }
//        });
//    }
}
