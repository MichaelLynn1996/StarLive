package us.xingkong.starlive.module.updateapps;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import us.xingkong.starlive.R;
import us.xingkong.starlive.base.BaseActivity;
import us.xingkong.streamsdk.network.Client;

public class UpdateAppsActivity extends BaseActivity<UpdateAppsContract.Presenter> implements UpdateAppsContract.View {

    @BindView(R.id.bt_update)
    AppCompatButton update;
    @BindView(R.id.apptitle)
    AppCompatEditText apptitle;
    @BindView(R.id.maintext)
    AppCompatEditText maintext;

    String app;

    @Override
    protected void init(Bundle savedInstanceState) {

        app = getIntent().getStringExtra("app");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.update_app);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.updateApp(new Client(UpdateAppsActivity.this),app
                        ,apptitle.getText().toString().trim()
                        ,maintext.getText().toString().trim());
            }
        });
    }

    @Override
    protected UpdateAppsContract.Presenter createPresenter() {
        return new UpdateAppsPresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_app;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void freeze() {
        update.setEnabled(false);
        apptitle.setEnabled(false);
        maintext.setEnabled(false);
    }

    @Override
    public void unfreeze() {
        update.setEnabled(true);
        apptitle.setEnabled(true);
        maintext.setEnabled(true);
    }
}
