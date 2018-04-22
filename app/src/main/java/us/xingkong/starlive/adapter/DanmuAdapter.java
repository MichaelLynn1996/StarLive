package us.xingkong.starlive.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.xingkong.starlive.R;

public class DanmuAdapter extends RecyclerView.Adapter<DanmuAdapter.DanmuViewHolder> {

    private Context mContext;
    @NonNull
    @Override
    public DanmuAdapter.DanmuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_danmu, parent, false);
        return new DanmuAdapter.DanmuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanmuAdapter.DanmuViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class DanmuViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.time)
        AppCompatTextView time;
        @BindView(R.id.content)
        AppCompatTextView content;

        public DanmuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
