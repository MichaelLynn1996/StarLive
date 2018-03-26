package us.xingkong.starlive.module.main;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import us.xingkong.starlive.R;
import us.xingkong.starlive.adapter.ItemAdapter;
import us.xingkong.starlive.base.BaseActivity;
import us.xingkong.starlive.module.live.LiveActivity;
import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.network.Client;

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {

    @BindView(R.id.apps_list)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private ItemAdapter adapter;
    private List<App> apps;


    @Override
    protected void init(Bundle savedInstanceState) {
        apps = new ArrayList<>();

        adapter = new ItemAdapter(apps, true);
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, App app) {
                Intent intent = new Intent(MainActivity.this, LiveActivity.class);
                intent.putExtra("app", app.getAppname());
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData(new Client(MainActivity.this),apps,adapter,refreshLayout);
            }
        });

        mPresenter.refreshData(new Client(MainActivity.this),apps,adapter,refreshLayout);
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void showToast(CharSequence msg) {

    }

    @Override
    public void showToast(int msgId) {

    }

    @Override
    public void showLoadingDialog(CharSequence msg) {

    }

    @Override
    public void hideLoadingDialog() {

    }
}
