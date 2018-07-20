package us.xingkong.starlive.module.login;

import android.widget.Toast;

import us.xingkong.starlive.base.BasePresenterImpl;
import us.xingkong.streamsdk.model.Result;
import us.xingkong.streamsdk.network.Client;
import us.xingkong.streamsdk.network.ResultListener;

public class LoginPresenter extends BasePresenterImpl implements LoginContract.Presenter{

    private LoginContract.View mView;

    LoginPresenter(LoginContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void login(Client client,String username,String password) {
        mView.freeze();
        client.login(username, password, new ResultListener<Result>() {
            @Override
            public void onFinish(Result result) {
                if (result.getStatus()==200){
                    Toast.makeText(mView.getContext(),result.getMsg(),Toast.LENGTH_SHORT).show();
                    mView.getActivity().finish();
                }else if (result.getStatus()==100){
                    Toast.makeText(mView.getContext(),result.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(mView.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinal() {
                mView.unFreeze();
            }
        });
    }
}
