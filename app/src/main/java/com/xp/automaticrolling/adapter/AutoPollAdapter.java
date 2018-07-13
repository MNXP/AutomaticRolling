package com.xp.automaticrolling.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xp.automaticrolling.R;

import java.util.List;

/**
 * 滚动adapter
 */
public class AutoPollAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<String> mData;

    public AutoPollAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mData = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_message, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            if (mData.size()!=0){
                String s = mData.get(position % mData.size());
                viewHolder.contentTv.setText(s);
            }


        }
    }


    @Override
    public int getItemCount() {
        if (mData!=null){
            if (mData.size()<=3)
                return mData.size();
            else
                return mData.size()*20;//return Integer.MAX_VALUE;
        }else {
            return 0;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView contentTv;

        private ViewHolder(View itemView) {
            super(itemView);
            initView(itemView);

        }

        private void initView(View itemView) {

            contentTv = (TextView) itemView.findViewById(R.id.content_tv);


        }
    }
}
