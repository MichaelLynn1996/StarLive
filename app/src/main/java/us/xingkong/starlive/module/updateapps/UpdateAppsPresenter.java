package us.xingkong.starlive.module.updateapps;

import android.text.TextUtils;
import android.widget.Toast;

import us.xingkong.starlive.base.BasePresenterImpl;
import us.xingkong.streamsdk.model.Result;
import us.xingkong.streamsdk.network.Client;
import us.xingkong.streamsdk.network.ResultListener;

public class UpdateAppsPresenter extends BasePresenterImpl implements UpdateAppsContract.Presenter {

    private UpdateAppsContract.View mView;

    UpdateAppsPresenter(UpdateAppsContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void updateApp(Client client, String app, String apptitle, String maintext) {
        if (!TextUtils.isEmpty(app))
            mView.freeze();
            client.updateApp(app, apptitle, maintext, new ResultListener<Result>() {
                @Override
                public void onFinish(Result result) {
                    if (result.getStatus() == 200) {
                        Toast.makeText(mView.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                        mView.getActivity().finish();
                    } else {
                        Toast.makeText(mView.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mView.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinal() {
                    mView.unfreeze();
                }
            });
    }
}
