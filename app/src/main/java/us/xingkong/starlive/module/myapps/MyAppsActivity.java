package us.xingkong.starlive.module.myapps;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import us.xingkong.starlive.R;
import us.xingkong.starlive.adapter.ItemAdapter;
import us.xingkong.starlive.base.BaseActivity;
import us.xingkong.starlive.module.createapp.CreateAppActivity;
import us.xingkong.starlive.module.live.LiveActivity;
import us.xingkong.starlive.module.main.MainActivity;
import us.xingkong.starlive.module.push.PushActivity;
import us.xingkong.starlive.module.updateapps.UpdateAppsActivity;
import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.Client;

public class MyAppsActivity extends BaseActivity<MyAppsContract.Presenter>
        implements MyAppsContract.View, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.apps_list)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ItemAdapter adapter;
    private List<App> apps;

    String appName;
    String token;

    private static final String[] PERMS = {Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO};  //直播权限
    private static final int RC_CAMERA_AND_RECORD = 100;

    @Override
    protected void init(Bundle savedInstanceState) {

        apps = new ArrayList<>();

        initView();

        mPresenter.getMyApps(new Client(MyAppsActivity.this));
    }

    private void initView() {

        /*
        设置ActionBar的属性
         */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.my_apps);
        }

        adapter = new ItemAdapter(apps, false);
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, App app) {
                appName = app.getAppname();
                token = app.getToken();
                if (EasyPermissions.hasPermissions(MyAppsActivity.this, PERMS)) { //权限申请
                    // Already have permission, do the thing
                    startPushActivity();
                } else {
                    // Do not have permissions, request them now
                    EasyPermissions.requestPermissions(MyAppsActivity.this, getString(R.string.need_permission),
                            RC_CAMERA_AND_RECORD, PERMS);
                }


            }
        });
        adapter.setOnItemLongClickListener(new ItemAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, App app) {
                Intent intent = new Intent(MyAppsActivity.this, UpdateAppsActivity.class);
                intent.putExtra("app",app.getAppname());
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(MyAppsActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMyApps(new Client(MyAppsActivity.this));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAppsActivity.this, CreateAppActivity.class));
            }
        });
    }

    private void startPushActivity() {
        if (appName != null && token != null) {
            Intent intent = new Intent(MyAppsActivity.this, PushActivity.class);
//                String url = "rtmp://live.xingkong.us/hls/" + app.getAppname()
//                        + "?token=" + app.getToken();
            String url = "rtmp://xingkongus.gqt.gcu.edu.cn/hls/" + appName
                    + "?token=" + token;
            intent.putExtra("url", url);
            startActivity(intent);
        }
    }

    @Override
    protected MyAppsContract.Presenter createPresenter() {
        return new MyAppsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myapps;
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

    @Override
    public void stopRefreshing() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshDataFinish(AppsResult result) {
        Log.d("result", result.toString());
        apps.clear();
        apps.addAll(result.getApps());
        adapter.notifyDataSetChanged();
    }

    /**
     * 以下是关于EasyPermissions对权限的操作
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
        if (EasyPermissions.hasPermissions(this, PERMS)) {
            startPushActivity();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new AppSettingsDialog.Builder(this).build().show();
        } else {
            Toast.makeText(this, "没有权限无法开始直播！", Toast.LENGTH_SHORT).show();
        }
    }
}
