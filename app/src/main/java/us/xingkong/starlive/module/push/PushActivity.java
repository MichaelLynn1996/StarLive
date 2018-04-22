package us.xingkong.starlive.module.push;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.laifeng.sopcastsdk.camera.CameraListener;
import com.laifeng.sopcastsdk.configuration.AudioConfiguration;
import com.laifeng.sopcastsdk.configuration.CameraConfiguration;
import com.laifeng.sopcastsdk.configuration.VideoConfiguration;
import com.laifeng.sopcastsdk.entity.Watermark;
import com.laifeng.sopcastsdk.entity.WatermarkPosition;
import com.laifeng.sopcastsdk.stream.packer.rtmp.RtmpPacker;
import com.laifeng.sopcastsdk.stream.sender.rtmp.RtmpSender;
import com.laifeng.sopcastsdk.ui.CameraLivingView;
import com.laifeng.sopcastsdk.utils.SopCastLog;
import com.laifeng.sopcastsdk.video.effect.GrayEffect;
import com.laifeng.sopcastsdk.video.effect.NullEffect;

import butterknife.BindView;
import us.xingkong.starlive.R;
import us.xingkong.starlive.base.BaseActivity;
import us.xingkong.starlive.ui.MultiToggleImageButton;

import static com.laifeng.sopcastsdk.constant.SopCastConstant.TAG;

public class PushActivity extends BaseActivity<PushContract.Presenter> implements PushContract.View {

//    @BindView(R.id.liveView)
    CameraLivingView mLFLiveView;
    @BindView(R.id.record_mic_button)
    MultiToggleImageButton mMicBtn;
    @BindView(R.id.camera_flash_button)
    MultiToggleImageButton mFlashBtn;
    @BindView(R.id.camera_switch_button)
    MultiToggleImageButton mFaceBtn;
    @BindView(R.id.camera_render_button)
    MultiToggleImageButton mBeautyBtn;
    @BindView(R.id.camera_focus_button)
    MultiToggleImageButton mFocusBtn;
    @BindView(R.id.btnRecord)
    ImageButton mRecordBtn;
    @BindView(R.id.progressConnecting)
    ProgressBar mProgressConnecting;

    private EditText mAddressET;

    private GestureDetector mGestureDetector;
    private GrayEffect mGrayEffect;
    private NullEffect mNullEffect;

    private boolean isGray;
    private boolean isRecording;
    private RtmpSender mRtmpSender;
    private VideoConfiguration mVideoConfiguration;
    private int mCurrentBps;
    private Dialog mUploadDialog;

    String url;

    @Override
    protected void init(Bundle savedInstanceState) {

//        url = new Intent().getStringExtra("address");
        url = getIntent().getStringExtra("url");
        Log.d(TAG, "init: url=" + url);

        checkPermission();
        initEffects();
        initViews();
        initListeners();
        initLiveView();
        initRtmpAddressDialog();
    }

    private void checkPermission() {

    }

    private void initViews() {
        mLFLiveView = findViewById(R.id.liveView);
//        mMicBtn = findViewById(R.id.record_mic_button);
//        mFlashBtn = findViewById(R.id.camera_flash_button);
//        mFaceBtn = findViewById(R.id.camera_switch_button);
//        mBeautyBtn = findViewById(R.id.camera_render_button);
//        mFocusBtn = findViewById(R.id.camera_focus_button);
//        mRecordBtn = findViewById(R.id.btnRecord);
//        mProgressConnecting = findViewById(R.id.progressConnecting);
    }

    private void initEffects() {
        mGrayEffect = new GrayEffect(this);
        mNullEffect = new NullEffect(this);
    }

    private void initListeners() {
        mMicBtn.setOnStateChangeListener(new MultiToggleImageButton.OnStateChangeListener() {
            @Override
            public void stateChanged(View view, int state) {
                mLFLiveView.mute(true);
            }
        });
        mFlashBtn.setOnStateChangeListener(new MultiToggleImageButton.OnStateChangeListener() {
            @Override
            public void stateChanged(View view, int state) {
                mLFLiveView.switchTorch();
            }
        });
        mFaceBtn.setOnStateChangeListener(new MultiToggleImageButton.OnStateChangeListener() {
            @Override
            public void stateChanged(View view, int state) {
                mLFLiveView.switchCamera();
            }
        });
        mBeautyBtn.setOnStateChangeListener(new MultiToggleImageButton.OnStateChangeListener() {
            @Override
            public void stateChanged(View view, int state) {
                if (isGray) {
                    mLFLiveView.setEffect(mNullEffect);
                    isGray = false;
                } else {
                    mLFLiveView.setEffect(mGrayEffect);
                    isGray = true;
                }
            }
        });
        mFocusBtn.setOnStateChangeListener(new MultiToggleImageButton.OnStateChangeListener() {
            @Override
            public void stateChanged(View view, int state) {
                mLFLiveView.switchFocusMode();
            }
        });
        mRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    mProgressConnecting.setVisibility(View.GONE);
                    Toast.makeText(PushActivity.this, "stop living", Toast.LENGTH_SHORT).show();
                    mRecordBtn.setBackgroundResource(R.mipmap.ic_record_start);
                    mLFLiveView.stop();
                    isRecording = false;
                } else {
//                    mUploadDialog.show();
                    mRtmpSender.setAddress(url);
                    mProgressConnecting.setVisibility(View.VISIBLE);
                    Toast.makeText(PushActivity.this, "start connecting", Toast.LENGTH_SHORT).show();
                    mRecordBtn.setBackgroundResource(R.mipmap.ic_record_stop);
                    mRtmpSender.connect();
                    isRecording = true;
                }
            }
        });
    }

    private void initRtmpAddressDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View playView = inflater.inflate(R.layout.address_dialog, (ViewGroup) findViewById(R.id.dialog));
        mAddressET = playView.findViewById(R.id.address);
        Button okBtn = playView.findViewById(R.id.ok);
        Button cancelBtn = playView.findViewById(R.id.cancel);
        AlertDialog.Builder uploadBuilder = new AlertDialog.Builder(this);
        uploadBuilder.setTitle("Upload Address");
        uploadBuilder.setView(playView);
        mUploadDialog = uploadBuilder.create();
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uploadUrl = mAddressET.getText().toString();
                if (TextUtils.isEmpty(uploadUrl)) {
                    Toast.makeText(PushActivity.this, "Upload address is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    mRtmpSender.setAddress(uploadUrl);
                    mProgressConnecting.setVisibility(View.VISIBLE);
                    Toast.makeText(PushActivity.this, "start connecting", Toast.LENGTH_SHORT).show();
                    mRecordBtn.setBackgroundResource(R.mipmap.ic_record_stop);
                    mRtmpSender.connect();
                    isRecording = true;
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

    @SuppressLint("ClickableViewAccessibility")
    private void initLiveView() {
        SopCastLog.isOpen(true);
        mLFLiveView.init();
        CameraConfiguration.Builder cameraBuilder = new CameraConfiguration.Builder();
        cameraBuilder.setOrientation(CameraConfiguration.Orientation.LANDSCAPE)
                .setFacing(CameraConfiguration.Facing.BACK);
        CameraConfiguration cameraConfiguration = cameraBuilder.build();
        mLFLiveView.setCameraConfiguration(cameraConfiguration);

        VideoConfiguration.Builder videoBuilder = new VideoConfiguration.Builder();
        videoBuilder.setSize(1280, 720);
        mVideoConfiguration = videoBuilder.build();
        mLFLiveView.setVideoConfiguration(mVideoConfiguration);

        //设置水印
        Bitmap watermarkImg = BitmapFactory.decodeResource(getResources(), R.mipmap.watermark);
        Watermark watermark = new Watermark(watermarkImg, 100, 50, WatermarkPosition.WATERMARK_ORIENTATION_TOP_LEFT, 10, 10);
        mLFLiveView.setWatermark(watermark);

        //设置预览监听
        mLFLiveView.setCameraOpenListener(new CameraListener() {
            @Override
            public void onOpenSuccess() {
                Toast.makeText(PushActivity.this, "camera open success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onOpenFail(int error) {
                Toast.makeText(PushActivity.this, "camera open fail", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCameraChange() {
                Toast.makeText(PushActivity.this, "camera switch", Toast.LENGTH_LONG).show();
            }
        });

//        //设置手势识别
//        mGestureDetector = new GestureDetector(this, new GestureListener());
//        mLFLiveView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mGestureDetector.onTouchEvent(event);
//                return false;
//            }
//        });

        //初始化flv打包器
        RtmpPacker packer = new RtmpPacker();
        packer.initAudioParams(AudioConfiguration.DEFAULT_FREQUENCY, 16, false);
        mLFLiveView.setPacker(packer);
        //设置发送器
        mRtmpSender = new RtmpSender();
        mRtmpSender.setVideoParams(640, 360);
        mRtmpSender.setAudioParams(AudioConfiguration.DEFAULT_FREQUENCY, 16, false);
        mRtmpSender.setSenderListener(mSenderListener);
        mLFLiveView.setSender(mRtmpSender);
        mLFLiveView.setLivingStartListener(new CameraLivingView.LivingStartListener() {
            @Override
            public void startError(int error) {
                //直播失败
                Toast.makeText(PushActivity.this, "start living fail", Toast.LENGTH_SHORT).show();
                mLFLiveView.stop();
            }

            @Override
            public void startSuccess() {
                //直播成功
                Toast.makeText(PushActivity.this, "start living", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private RtmpSender.OnSenderListener mSenderListener = new RtmpSender.OnSenderListener() {
        @Override
        public void onConnecting() {

        }

        @Override
        public void onConnected() {
            mProgressConnecting.setVisibility(View.GONE);
            mLFLiveView.start();
            mCurrentBps = mVideoConfiguration.maxBps;
        }

        @Override
        public void onDisConnected() {
            mProgressConnecting.setVisibility(View.GONE);
            Toast.makeText(PushActivity.this, "fail to live", Toast.LENGTH_SHORT).show();
            mRecordBtn.setBackgroundResource(R.mipmap.ic_record_start);
            mLFLiveView.stop();
            isRecording = false;
        }

        @Override
        public void onPublishFail() {
            mProgressConnecting.setVisibility(View.GONE);
            Toast.makeText(PushActivity.this, "fail to publish stream", Toast.LENGTH_SHORT).show();
            mRecordBtn.setBackgroundResource(R.mipmap.ic_record_start);
            isRecording = false;
        }

        @Override
        public void onNetGood() {
            if (mCurrentBps + 50 <= mVideoConfiguration.maxBps) {
                SopCastLog.d(TAG, "BPS_CHANGE good up 50");
                int bps = mCurrentBps + 50;
                if (mLFLiveView != null) {
                    boolean result = mLFLiveView.setVideoBps(bps);
                    if (result) {
                        mCurrentBps = bps;
                    }
                }
            } else {
                SopCastLog.d(TAG, "BPS_CHANGE good good good");
            }
            SopCastLog.d(TAG, "Current Bps: " + mCurrentBps);
        }

        @Override
        public void onNetBad() {
            if (mCurrentBps - 100 >= mVideoConfiguration.minBps) {
                SopCastLog.d(TAG, "BPS_CHANGE bad down 100");
                int bps = mCurrentBps - 100;
                if (mLFLiveView != null) {
                    boolean result = mLFLiveView.setVideoBps(bps);
                    if (result) {
                        mCurrentBps = bps;
                    }
                }
            } else {
                SopCastLog.d(TAG, "BPS_CHANGE bad down 100");
            }
            SopCastLog.d(TAG, "Current Bps: " + mCurrentBps);
        }
    };

//    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            if (e1.getX() - e2.getX() > 100
//                    && Math.abs(velocityX) > 200) {
//                // Fling left
//                Toast.makeText(PushActivity.this, "Fling Left", Toast.LENGTH_SHORT).show();
//            } else if (e2.getX() - e1.getX() > 100
//                    && Math.abs(velocityX) > 200) {
//                // Fling right
//                Toast.makeText(PushActivity.this, "Fling Right", Toast.LENGTH_SHORT).show();
//            }
//            return super.onFling(e1, e2, velocityX, velocityY);
//        }
//    }

    @Override
    protected PushContract.Presenter createPresenter() {
        return new PushPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_push;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLFLiveView.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLFLiveView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLFLiveView.stop();
        mLFLiveView.release();
    }
}
