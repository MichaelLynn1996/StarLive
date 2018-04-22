package us.xingkong.starlive.module.main;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import us.xingkong.starlive.R;
import us.xingkong.starlive.adapter.ItemAdapter;
import us.xingkong.starlive.base.BaseActivity;
import us.xingkong.starlive.module.live.LiveActivity;
import us.xingkong.starlive.module.login.LoginActivity;
import us.xingkong.starlive.module.myapps.MyAppsActivity;
import us.xingkong.starlive.module.push.PushActivity;
import us.xingkong.starlive.utils.AlertUtil;
import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.Client;

public class MainActivity extends BaseActivity<MainContract.Presenter>
        implements MainContract.View, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.apps_list)   //直播列表
            RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)  //刷新
            SwipeRefreshLayout refreshLayout;
    @BindView(R.id.drawer_layout)   //抽屉
            DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)    //导航
            NavigationView navigationView;

    View headerView;
    AppCompatImageView imageView;
    AppCompatTextView nickname;
    private Dialog mUploadDialog;
    private AppCompatEditText mAddressET;

    private ItemAdapter adapter;
    private List<App> apps;

    private static final String[] PERMS = {Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO};  //直播权限
    private static final int RC_CAMERA_AND_RECORD = 100;

    @Override
    protected void init(Bundle savedInstanceState) {

        apps = new ArrayList<>();

        /*
        设置ActionBar的属性
         */
        if (getSupportActionBar() != null) {
            final ActionBar bar = getSupportActionBar();

            bar.setHomeAsUpIndicator(R.drawable.ic_menu);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowTitleEnabled(true);
            bar.setTitle(getResources().getString(R.string.star_live));
        }

        initAddressDialog();

        initViews();

        mPresenter.checkLoginState(new Client(MainActivity.this));
        mPresenter.refreshData(new Client(MainActivity.this));
    }

    /**
     * 初始化地址输入框的Dialog
     */
    private void initAddressDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View playView = inflater.inflate(R.layout.address_dialog, (ViewGroup) findViewById(R.id.dialog));
        mAddressET = playView.findViewById(R.id.address);
        AppCompatButton okBtn = playView.findViewById(R.id.ok);
        AppCompatButton cancelBtn = playView.findViewById(R.id.cancel);
        AlertDialog.Builder uploadBuilder = new AlertDialog.Builder(this);
        uploadBuilder.setTitle("Upload Address");
        uploadBuilder.setView(playView);
        mUploadDialog = uploadBuilder.create();
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uploadUrl = mAddressET.getText().toString();
                if (TextUtils.isEmpty(uploadUrl)) {
                    Toast.makeText(MainActivity.this, "Upload address is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    if (EasyPermissions.hasPermissions(MainActivity.this, PERMS)) { //权限申请
                        // Already have permission, do the thing
                        startPushActivity();
                    } else {
                        // Do not have permissions, request them now
                        EasyPermissions.requestPermissions(MainActivity.this, getString(R.string.need_permission),
                                RC_CAMERA_AND_RECORD, PERMS);
                    }

                }
                mUploadDialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUploadDialog.dismiss();
            }
        });
    }

    private void initViews() {
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_drawer_layout);

        imageView = headerView.findViewById(R.id.iv_header);
        Glide.with(MainActivity.this).load(R.drawable.banner3).into(imageView);
        nickname = headerView.findViewById(R.id.nickname);

        /*
          监听NavigationView的选择事件
         */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_apps:
                        startActivity(new Intent(MainActivity.this, MyAppsActivity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.exit_app:
                        AlertUtil.buildAlert(MainActivity.this, R.string.exit_app, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        return true;
                    case R.id.custom_live:
                        mUploadDialog.show();
                        return true;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

        adapter = new ItemAdapter(apps, true);
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, App app) {
                Intent intent = new Intent(MainActivity.this, LiveActivity.class);
                intent.putExtra("app", app.getAppname());
                startActivity(intent);
            }
        });
        adapter.setOnItemLongClickListener(new ItemAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, App app) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData(new Client(MainActivity.this));
            }
        });

        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nickname.getText() == getResources().getString(R.string.pls_press_to_login)) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    AlertUtil.buildAlert(MainActivity.this, R.string.confirm_exit_login, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.logout(new Client(MainActivity.this));
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
            }
        });
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkLoginState(new Client(MainActivity.this));
    }

    private void startPushActivity() {
        Intent intent = new Intent(MainActivity.this, PushActivity.class);
        String url = mAddressET.getText().toString();
        intent.putExtra("url", url);
        startActivity(intent);
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

    @Override
    public void stopRefreshing() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshDataFinish(AppsResult result) {
        apps.clear();
        apps.addAll(result.getApps());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname.setText(nickname);
    }

    @Override
    public void setNickname(int resId) {
        this.nickname.setText(resId);
    }
}
