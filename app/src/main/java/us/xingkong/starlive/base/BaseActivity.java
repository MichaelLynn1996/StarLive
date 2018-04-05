package us.xingkong.starlive.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by SeaLynn0 on 2018/3/26.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
        implements BaseView<P>{

    /**
     * 泛型确定Presenter
     */
    protected P mPresenter;

    Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(getLayoutId());
        super.onCreate(savedInstanceState);
        // ButterKnife绑定布局
        bind = ButterKnife.bind(this);

        mPresenter = createPresenter();
        if (mPresenter != null) {
            // 调用Presenter初始化方法
            mPresenter.subscribe();
        }

        init(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract P createPresenter();

    protected abstract int getLayoutId();

    /**
     * Activity销毁时清理资源
     */
    @Override
    protected void onDestroy() {
        // ButterKnife解除绑定
        bind.unbind();
        // 销毁Presenter
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
        super.onDestroy();
    }
}
