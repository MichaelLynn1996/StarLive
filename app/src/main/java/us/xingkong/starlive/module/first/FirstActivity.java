package us.xingkong.starlive.module.first;

import android.content.Intent;
import android.os.Bundle;

import us.xingkong.starlive.R;
import us.xingkong.starlive.base.BaseActivity;
import us.xingkong.starlive.module.main.MainActivity;

/**
 * 本Activity是一个启动页，但目前没有存在的意义不当作程序的入口
 */

public class FirstActivity extends BaseActivity<FirstContract.Presenter> implements FirstContract.View {
    @Override
    protected void init(Bundle savedInstanceState) {
        mPresenter.showFirstPage(new Intent(FirstActivity.this, MainActivity.class));
    }

    @Override
    protected FirstContract.Presenter createPresenter() {
        return new FirstPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first;
    }

}
