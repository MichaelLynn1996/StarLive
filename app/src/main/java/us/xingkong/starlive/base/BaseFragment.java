package us.xingkong.starlive.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView<P> {

    View v;

    Unbinder bind;

    /**
     * 泛型确定Presenter
     */
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(getLayoutId(), container, false);
        // ButterKnife绑定布局
        bind = ButterKnife.bind(this, v);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            // 调用Presenter初始化方法
            mPresenter.subscribe();
        }

        init(savedInstanceState);
        return v;
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected abstract P createPresenter();

    /**
     * Activity销毁时清理资源
     */
    @Override
    public void onDestroy() {
        // ButterKnife解除绑定
        bind.unbind();
        // 销毁Presenter
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
        super.onDestroy();
    }

//    @Override
//    public Context getContext() {
//        return this;
//    }

    @Override
    public void setPresenter(P presenter) {

    }

//    @Override
//    public Activity getActivity() {
//        return this;
//    }
}
