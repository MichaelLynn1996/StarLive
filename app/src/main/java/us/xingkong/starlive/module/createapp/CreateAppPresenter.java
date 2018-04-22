package us.xingkong.starlive.module.createapp;

import android.text.TextUtils;
import android.widget.Toast;

import us.xingkong.starlive.base.BasePresenterImpl;
import us.xingkong.streamsdk.model.Result;
import us.xingkong.streamsdk.network.Client;
import us.xingkong.streamsdk.network.ResultListener;

public class CreateAppPresenter extends BasePresenterImpl implements CreateAppContract.Presenter {

    private CreateAppContract.View mView;

    CreateAppPresenter(CreateAppContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void createApp(Client client, String appname, String apptitle, String maintext) {
        if (!TextUtils.isEmpty(appname)
                && !TextUtils.isEmpty(apptitle)
                && !TextUtils.isEmpty(maintext))
            mView.freeze();
        client.createApp(appname, apptitle, maintext, new ResultListener<Result>() {
            @Override
            public void onFinish(Result result) {
                if (result.getStatus() == 100)
                    Toast.makeText(mView.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                else if (result.getStatus() == 101) {
                    Toast.makeText(mView.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                } else if (result.getStatus() == 200) {
                    Toast.makeText(mView.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                    mView.getActivity().finish();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(mView.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinal() {
                mView.unfreeze();
            }
        });
    }
}
