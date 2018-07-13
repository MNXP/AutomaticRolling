package com.xp.automaticrolling.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.xp.automaticrolling.R;
import com.xp.automaticrolling.bean.ContentBean;
import com.xp.automaticrolling.view.AutoPollRecyclerView;

import java.util.List;

/**
 * 首页adapter
 */
public class MainAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ContentBean> mainShowList;


    public MainAdapter(Context context, List<ContentBean> mainShowList) {
        this.context = context;
        this.mainShowList = mainShowList;
    }

    public void upData(List<ContentBean> mainShowList, int position) {
        this.mainShowList = mainShowList;
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_main_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            ContentBean item = mainShowList.get(position);


            AutoPollAdapter adapter = new AutoPollAdapter(context, item.list);

            viewHolder.autoPollRecyclerView.setAdapter(adapter);

            /**
             * 我的是超过3项开始滚动
             */
            if (item.list.size() > 0) {
                if (item.list.size() <= 3) {
                    viewHolder.autoPollRecyclerView.setCanRun(false);
                } else {
                    viewHolder.autoPollRecyclerView.setCanRun(true);
                }
            }


        }
    }

    @Override
    public int getItemCount() {
        return mainShowList != null ? mainShowList.size() : 0;
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        private AutoPollRecyclerView autoPollRecyclerView;

        private ViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
            //给列表item点击点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });
        }

        private void initView(View itemView) {

            autoPollRecyclerView = (AutoPollRecyclerView) itemView.findViewById(R.id.main_recycler);
            autoPollRecyclerView.start();
            autoPollRecyclerView.setNestedScrollingEnabled(false);
            autoPollRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            ((SimpleItemAnimator) autoPollRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        }
    }

    //对外部暴漏点击事件接口
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
