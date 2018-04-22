package us.xingkong.starlive.module.createapp;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import butterknife.BindView;
import us.xingkong.starlive.R;
import us.xingkong.starlive.base.BaseActivity;
import us.xingkong.streamsdk.network.Client;

public class CreateAppActivity extends BaseActivity<CreateAppContract.Presenter> implements CreateAppContract.View {

    @BindView(R.id.bt_create)
    AppCompatButton create;
    @BindView(R.id.appname)
    AppCompatEditText appanme;
    @BindView(R.id.apptitle)
    AppCompatEditText apptitle;
    @BindView(R.id.maintext)
    AppCompatEditText maintext;

    @Override
    protected void init(Bundle savedInstanceState) {
/*
        设置ActionBar的属性
         */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.create_app);
        }

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.createApp(new Client(CreateAppActivity.this)
                ,appanme.getText().toString().trim()
                ,apptitle.getText().toString().trim()
                ,maintext.getText().toString().trim());
            }
        });
    }

    @Override
    protected CreateAppContract.Presenter createPresenter() {
        return new CreateAppPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_app;
    }

    @Override
    public void freeze() {
        create.setEnabled(false);
        appanme.setEnabled(false);
        apptitle.setEnabled(false);
        maintext.setEnabled(false);
    }

    @Override
    public void unfreeze() {
        create.setEnabled(true);
        appanme.setEnabled(true);
        apptitle.setEnabled(true);
        maintext.setEnabled(true);
    }
}
