package us.xingkong.starlive.module.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import us.xingkong.starlive.R;
import us.xingkong.starlive.base.BaseActivity;
import us.xingkong.streamsdk.network.Client;

public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.bt_signin)
    AppCompatButton signin;
    @BindView(R.id.bt_login)
    AppCompatButton login;
    @BindView(R.id.et_username)
    AppCompatEditText username;
    @BindView(R.id.et_password)
    AppCompatEditText usercode;

    @Override
    protected void init(Bundle savedInstanceState) {

        /*
        设置ActionBar的属性
         */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.login);
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, SigninActivity.class));
                Toast.makeText(LoginActivity.this, "暂未开放！", Toast.LENGTH_SHORT).show();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login(new Client(LoginActivity.this),
                        username.getText().toString().trim(),
                        usercode.getText().toString().trim());
//                String userName = username.getText().toString();
//                String userPass = usercode.getText().toString();
//                loginMethod(userName,userPass);
            }
        });
    }

    @Override
    protected LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void freeze() {
        signin.setEnabled(false);
        login.setEnabled(false);
        username.setEnabled(false);
        usercode.setEnabled(false);
    }

    @Override
    public void unFreeze() {
        signin.setEnabled(true);
        login.setEnabled(true);
        username.setEnabled(true);
        usercode.setEnabled(true);
    }

    /**
     * 复写onOptionsItemSelected(MenuItem item)方法
     * 监听返回键
     *
     * @param item
     * @return
     */
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

}
