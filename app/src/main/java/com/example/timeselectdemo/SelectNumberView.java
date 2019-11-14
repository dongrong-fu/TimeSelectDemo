package com.example.timeselectdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * Created by dongrong.fu on 2019/10/29
 * 选择数字控件，使用时传入数字数组
 */
public class SelectNumberView extends LinearLayout {

    private CallBackScrollStatusScrollView mScrollView;
    private LinearLayout mLinearLayout;
    private String[] mSelectArr;
    private int mDefaultSelectPosition;
    private int mSelectPosition;
    private int mLastSelectPosition;
    private boolean mIsNotFirstScroll;
    private int mFirstScrollY;
    private Context mContext;

    public SelectNumberView(Context context) {
        this(context,null);

    }

    public SelectNumberView(Context context, AttributeSet attrs) {
        super(context,attrs);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mScrollView = new CallBackScrollStatusScrollView(context);
        mScrollView.setVerticalScrollBarEnabled(false);
        mScrollView.setLayoutParams(lp);
        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.setLayoutParams(lp);
        mContext = context;
        mScrollView.addView(mLinearLayout);
        addView(mScrollView);
    }

    public String getNumber(){
        if(mSelectArr != null && mSelectArr.length > 0){
            return mSelectArr[mSelectPosition];
        }
        return "";
    }

    public void setSelectArr(String[] selectArr, int defaultPosition){
        if(selectArr == null || selectArr.length == 0) return ;
        this.mSelectArr = selectArr;
        mDefaultSelectPosition = defaultPosition + 1;
        mSelectPosition = defaultPosition;
        mLastSelectPosition = mSelectPosition;
        mLinearLayout.removeAllViews();
        for(int i = 0;i < selectArr.length;i++){
            String number = selectArr[i];
            TextView textView = new TextView(mContext);
            textView.setText(number);
            if(i == mDefaultSelectPosition - 1){
                textView.setTextColor(Color.parseColor("#0067E5"));
            }else {
                textView.setTextColor(Color.parseColor("#7A7A7A"));
            }
            textView.setTextSize(16);
            textView.setClickable(true);
            textView.setBackgroundResource(R.drawable.selector_itemcolor);
            textView.setGravity(Gravity.CENTER);
            final int index = i;
            textView.setOnClickListener(v-> {
                mScrollView.smoothScrollTo(0, ScreenUtils.dp2px(mContext,48) * index);
                mScrollView.setDefaultY(ScreenUtils.dp2px(mContext,48) * index);
            });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ScreenUtils.dp2px(mContext,48));
            if(i == 0){
                lp.topMargin = ScreenUtils.dp2px(mContext,48) * 2;
            } else if(i == selectArr.length - 1){
                lp.bottomMargin = ScreenUtils.dp2px(mContext,48) * 2;
            }
            textView.setLayoutParams(lp);
            mLinearLayout.addView(textView);
        }
    }

    public void init(){
        mScrollView.post(() -> {
            mScrollView.scrollTo(0,ScreenUtils.dp2px(mContext,48) * (mDefaultSelectPosition - 1));
            mIsNotFirstScroll = true;
        });
        mScrollView.setOnScrollListener(scrollY -> {
            if(!mIsNotFirstScroll) {
                mFirstScrollY = scrollY;
                return;
            }
            int scrollPosition = (int) Math.round((scrollY - mFirstScrollY) * 1.0 / ScreenUtils.dp2px(mContext,48));
            mSelectPosition = mDefaultSelectPosition - 1 + scrollPosition;

            TextView lastSelectTv = (TextView) mLinearLayout.getChildAt(mLastSelectPosition);
            lastSelectTv.setTextColor(Color.parseColor("#7A7A7A"));

            TextView currentSelectTv;
            if(mDefaultSelectPosition - 1 + scrollPosition < 0){
                currentSelectTv = (TextView) mLinearLayout.getChildAt(0);
                mLastSelectPosition = 0;
            }else if(mDefaultSelectPosition - 1 + scrollPosition > mLinearLayout.getChildCount() - 1){
                currentSelectTv = (TextView) mLinearLayout.getChildAt(mLinearLayout.getChildCount() - 1);
                mLastSelectPosition = mLinearLayout.getChildCount() - 1;
            }else {
                currentSelectTv = (TextView) mLinearLayout.getChildAt(mDefaultSelectPosition - 1 + scrollPosition);
                mLastSelectPosition = mDefaultSelectPosition - 1 + scrollPosition;
            }
            currentSelectTv.setTextColor(Color.parseColor("#0067E5"));
        });
    }

}
