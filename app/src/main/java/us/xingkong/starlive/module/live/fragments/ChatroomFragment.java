package us.xingkong.starlive.module.live.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import us.xingkong.starlive.R;
import us.xingkong.starlive.base.BaseFragment;
import us.xingkong.starlive.module.live.LiveContract;
import us.xingkong.starlive.module.live.LivePresenter;

public class ChatroomFragment extends BaseFragment<LiveContract.Presenter> implements LiveContract.View {


    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chatroom;
    }

    @Override
    protected LiveContract.Presenter createPresenter() {
        return new LivePresenter(this);
    }
}
