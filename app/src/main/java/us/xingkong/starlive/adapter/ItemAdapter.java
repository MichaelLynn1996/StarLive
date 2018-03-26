package us.xingkong.starlive.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.xingkong.starlive.R;
import us.xingkong.streamsdk.model.App;

/**
 * Created by SeaLynn0 on 2018/1/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.AppViewHolder> {

    private Context mContext;

    private List<App> mItemList;

    private List<App> livingList;

    /**
     * 获取是否在直播的Item
     */
    private boolean isGetLiving;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public ItemAdapter(List<App> mItemList, boolean isGetLiving) {
        this.mItemList = mItemList;
        this.isGetLiving = isGetLiving;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.appitem, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        final App app;

        if (isGetLiving)
            app = livingList.get(position);
        else
            app = mItemList.get(position);

        holder.itemView.setTag(position);

        holder.appTitle.setText(app.getTitle());
        holder.appUser.setText(app.getUser());
        holder.appMaintext.setText(app.getMaintext());

        Glide.with(mContext).load("http://live.xingkong.us/screen/" + app.getAppname() + ".png").into(holder.appImg);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,app);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemLongClickListener.onItemLongClick(v,app);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        //根据isLive来获取不同的列表
        if (isGetLiving) {
            livingList = new ArrayList<>();
            for (int i = 0; i < mItemList.size(); i++) {
                App app = mItemList.get(i);
                if (app.isAlive()) {
                    livingList.add(app);
                }
            }
            return livingList.size();
        } else return mItemList.size();
    }

    class AppViewHolder extends RecyclerView.ViewHolder {
        /*
        ButterKnife在Adapter的应用
         */
        @BindView(R.id.app_img)
        AppCompatImageView appImg;
        @BindView(R.id.app_title)
        AppCompatTextView appTitle;
        @BindView(R.id.app_user)
        AppCompatTextView appUser;
        @BindView(R.id.app_maintext)
        AppCompatTextView appMaintext;

        CardView cardView;

        AppViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            cardView = (CardView) itemView;
        }
    }

    //define interface
    /*
    定义Item的点击或长按监听接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, App app);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, App app);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }
}
