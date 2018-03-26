package us.xingkong.starlive.module.live;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;
import us.xingkong.starlive.R;
import us.xingkong.starlive.base.BaseActivity;

/**
 * Created by SeaLynn0 on 2018/3/27.
 */

public class LiveActivity extends BaseActivity<LiveContract.Presenter> implements LiveContract.View {

    @BindView(R.id.video_view)
    VideoView mVideoView;
    @BindView(R.id.video_frame)
    FrameLayout mVideoFrame;
    @BindView(R.id.danmaku_view)
    DanmakuView mDanmakuView;

    private MediaController mMediaController;
    private BaseDanmakuParser mParser;
    private DanmakuContext mDanmakuContext;

    private HashMap<Integer, Integer> maxLinesPair;// 弹幕最大行数
    private HashMap<Integer, Boolean> overlappingEnablePair;// 设置是否重叠

    private static final String STREAM_BASE_URL = "http://live.xingkong.us/hls/";

    @Override
    public void setPresenter(LiveContract.Presenter presenter) {

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

    @Override
    protected void init(Bundle savedInstanceState) {
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }

        String app = getIntent().getStringExtra("app");

        mMediaController = new MediaController(this);

        mVideoView.setVideoPath(STREAM_BASE_URL + app + ".m3u8");
        mVideoView.setMediaController(mMediaController);
        mVideoView.start();

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                new AlertDialog.Builder(LiveActivity.this)
                        .setTitle(getResources().getString(R.string.videoErrTitle))
                        .setMessage(getResources().getString(R.string.videoErrMsg))
                        .setPositiveButton(getResources().getString(R.string.videoErrBtn),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setCancelable(false).show();
                return false;
            }
        });
    }

    @Override
    protected LivePresenter createPresenter() {
        return new LivePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live;
    }
}
