package com.xp.automaticrolling.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

public class AutoPollRecyclerView extends RecyclerView {
    private static final long TIME_AUTO_POLL = 30;
    private AutoPollTask autoPollTask;
    private boolean running; //是否正在自动轮询
    private boolean canRun;//是否可以自动轮询,可在不需要的是否置false
    private int mMaxHeight;//最大高度
    public AutoPollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        autoPollTask = new AutoPollTask(this);
        mMaxHeight = dip2px(context,100);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    static class AutoPollTask implements Runnable {
        private final WeakReference<AutoPollRecyclerView> mReference;
        /**
         * 使用弱引用持有外部类引用,防止内存泄漏
         */

        private AutoPollTask(AutoPollRecyclerView reference) {
            this.mReference = new WeakReference<>(reference);
        }
        @Override
        public void run() {
            AutoPollRecyclerView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.running) {
                recyclerView.scrollBy(2, 2);//每次滚动距离
                /**
                 * 判断是否为无限循环
                 */
                if (recyclerView.canRun){
                    /**
                     * 判断是否触底
                     */
                    if (isSlideToBottom(recyclerView)) {
                        /**跳至顶部*/
                        recyclerView.smoothScrollToPosition(0);
                    }
                }

                recyclerView.postDelayed(recyclerView.autoPollTask,TIME_AUTO_POLL);
            }
        }
    }
    /**判断Recycler是否滑动至最底部  是返回true  不是返回false*/
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int heightMode = MeasureSpec.getMode(heightSpec);
        int heightSize = MeasureSpec.getSize(heightSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                heightMode);
        super.onMeasure(widthSpec, maxHeightMeasureSpec);
    }

    //开启
    public void start() {
        if (running){
            return;
        }
        canRun = true;
        running = true;
        postDelayed(autoPollTask,TIME_AUTO_POLL);
    }
    public void setCanRun(boolean canRun){
        this.canRun = canRun;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
