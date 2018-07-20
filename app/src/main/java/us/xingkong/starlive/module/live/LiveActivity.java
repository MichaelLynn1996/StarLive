package us.xingkong.starlive.module.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;
import us.xingkong.starlive.encode.MediaIjkPlayer;
import us.xingkong.starlive.R;
import us.xingkong.starlive.adapter.ViewPagerAdapter;
import us.xingkong.starlive.base.BaseActivity;
import us.xingkong.starlive.module.live.fragments.ChatroomFragment;
import us.xingkong.starlive.module.live.fragments.PartnerFragment;
import us.xingkong.starlive.module.live.fragments.XingKongFragment;

/**
 * Created by SeaLynn0 on 2018/3/27.
 */

public class LiveActivity extends BaseActivity<LiveContract.Presenter> implements LiveContract.View {

    @BindView(R.id.video_texture)
    FrameLayout mVideoFrame;
    @BindView(R.id.sv_danmaku)
    DanmakuView mDanmakuView;
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard jzVideoPlayerStandard;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private BaseDanmakuParser mParser;
    private DanmakuContext mDanmakuContext;

    private HashMap<Integer, Integer> maxLinesPair;// 弹幕最大行数
    private HashMap<Integer, Boolean> overlappingEnablePair;// 设置是否重叠

    private static final String STREAM_BASE_URL = "http://xingkongus.gqt.gcu.edu.cn/hls/";
    private static final String TAG = "LiveActivity";

    private List<Fragment> list;
    private ViewPagerAdapter mAdapter;
    private String[] titles = {"聊天室", "星空", "合作方"};

    @Override
    protected void init(Bundle savedInstanceState) {

        String app = getIntent().getStringExtra("app");

        jzVideoPlayerStandard.setUp(STREAM_BASE_URL + app + ".m3u8", JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
        JZVideoPlayer.setMediaInterface(new MediaIjkPlayer());
        jzVideoPlayerStandard.startVideo();

        initViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initViewPager() {
        list = new ArrayList<>();
        list.add(new ChatroomFragment());
        list.add(new XingKongFragment());
        list.add(new PartnerFragment());

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(),list,titles);
        viewPager.setAdapter(mAdapter);
    }

    @Override
    protected LivePresenter createPresenter() {
        return new LivePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
