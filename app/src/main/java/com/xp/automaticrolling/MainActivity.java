package com.xp.automaticrolling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.xp.automaticrolling.adapter.MainAdapter;
import com.xp.automaticrolling.bean.ContentBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        List<ContentBean> list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        MainAdapter mainAdapter = new MainAdapter(this, list);
        recyclerView.setAdapter(mainAdapter);
        for (int i = 0; i < 4; i++) {
            ContentBean contentBean = new ContentBean();
            contentBean.title = i + "、条目";
            List<String> stringList = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                stringList.add(j + "循环滚动");
                stringList.add(j + "循环滚动ww");
            }
            contentBean.list = stringList;
            list.add(contentBean);
        }
        mainAdapter.notifyDataSetChanged();

    }
}
