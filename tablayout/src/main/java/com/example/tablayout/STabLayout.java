package com.example.tablayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stablayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/21 0021.
 */

public class STabLayout extends HorizontalScrollView implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private Context context;

    private Builder builder;

    private ViewPager viewPager;

    private LinearLayout linearLayout;

    private List<View> textViews = new ArrayList<>();

    private int screenWidth;

    private int preIndex = 0;

    private int dd;

    private List<Integer> distance = new ArrayList<>();

    private List<CountView> badgeViews = new ArrayList<>();

    private int start = 0;

    private Paint paint = new Paint();

    private int height;

    public STabLayout(Context context) {
        super(context);
    }

    public STabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        WindowManager windowManager = ((Activity) context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        paint.setAntiAlias(true);
        paint.setDither(true);
        height = dip2Px(3);
    }

    public void setTitleData(Builder builder, ViewPager viewPager) {
        this.viewPager = viewPager;
        this.builder = builder;
        height = dip2Px(builder.getLineHeight());
        List<String> strings = builder.getData();
        paint.setColor(Color.parseColor(builder.getLineSelectColor()));
        if (strings == null) {
            throw new RuntimeException("build.setData 不能为空");
        }

        if (viewPager == null) {
            throw new RuntimeException("viewPager 不能为空");
        }
        viewPager.addOnPageChangeListener(this);
        linearLayout = new LinearLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setLayoutParams(params);
        measureChild(linearLayout, MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
        for (int i = 0; i < strings.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item, linearLayout, false);
            TextView textView = (TextView) view.findViewById(R.id.tv_title);
            CountView badgeView = (CountView) view.findViewById(R.id.badg);
            badgeView.setVisibility(GONE);
            badgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, builder.getCountSize());
            badgeView.setTextColor(Color.parseColor(builder.getCountColor()));
            badgeView.setBgColorSize(builder.getCountBgColor(), builder.getCountBgSize());
            badgeViews.add(badgeView);
            textView.setText(strings.get(i));
            measureChild(view, MeasureSpec.makeMeasureSpec(linearLayout.getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(linearLayout.getMeasuredHeight(), MeasureSpec.EXACTLY));
            if (0 == i) {
                textView.setTextColor(Color.parseColor(builder.getSelectTestColor()));
                textView.setTextSize(builder.getTextSize() == builder.getSelectTextSize() ? builder.getTextSize() : builder.getSelectTextSize());
            } else {
                textView.setTextColor(Color.parseColor(builder.getTextColor()));
                textView.setTextSize(builder.getTextSize());
            }
            textView.setTag(i);
            textView.setOnClickListener(this);
            linearLayout.addView(view);
            distance.add(dd);
            dd += textView.getMeasuredWidth();
            textViews.add(view);
        }
        addView(linearLayout);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        reSetTextColor(position);

        if (builder.getTextSize() != builder.getSelectTextSize()) {
            resetDistance();
        }

        if (position > preIndex) {
            if (distance.get(position) < getScrollX()) {
                scrollTo(distance.get(position), 0);
            } else {
                if (position < textViews.size() - 1) {
                    int scroll = getScrollX() + screenWidth;
                    Integer integer = distance.get(position) + textViews.get(position).getMeasuredWidth() + textViews.get(position + 1).getMeasuredWidth();
                    if (scroll <= integer) {
                        scrollBy(integer - scroll, 0);
                    }
                } else {
                    scrollTo(distance.get(position) + textViews.get(position).getMeasuredWidth() - screenWidth, 0);
                }
            }
        } else {
            if (getScrollX() + screenWidth < distance.get(position) + textViews.get(position).getMeasuredWidth()) {
                scrollTo(distance.get(position) + textViews.get(position).getMeasuredWidth() - screenWidth, 0);
            } else {
                if (position > 0) {
                    int scroll = getScrollX();
                    int integer = distance.get(position - 1);
                    if (scroll >= integer) {
                        scrollBy(-(scroll - integer), 0);
                    }
                } else {
                    scrollTo(0, 0);
                }
            }
        }
        preIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        viewPager.setCurrentItem(tag);
    }

    private void resetDistance() {
        dd = 0;
        distance.clear();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View childAt = linearLayout.getChildAt(i);
            measureChild(childAt, MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
            distance.add(dd);
            dd += childAt.getMeasuredWidth();
        }
    }

    private void reSetTextColor(int position) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            RelativeLayout relativeLayout = (RelativeLayout) linearLayout.getChildAt(i);
            TextView childAt = (TextView) relativeLayout.findViewById(R.id.tv_title);
            if (position == i) {
                childAt.setTextColor(Color.parseColor(builder.getSelectTestColor()));
                childAt.setTextSize(builder.getTextSize() == builder.getSelectTextSize() ? builder.getTextSize() : builder.getSelectTextSize());
            } else {
                childAt.setTextColor(Color.parseColor(builder.getTextColor()));
                childAt.setTextSize(builder.getTextSize());
            }
        }
    }

    public void updateCount(List<Integer> integers) {

        if (viewPager == null) {
            throw new RuntimeException("在setTitleData 方法后调用");
        }
        for (int i = 0; i < badgeViews.size(); i++) {
            if (integers.get(i) > 0) {
                View view = textViews.get(i);
                TextView viewById = (TextView) view.findViewById(R.id.tv_title);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewById.getLayoutParams();
                layoutParams.setMargins(dip2Px(10), dip2Px(5), dip2Px(15), dip2Px(5));
                CountView badgeView = badgeViews.get(i);
                badgeView.setVisibility(VISIBLE);
                badgeView.setText(integers.get(i) + "");
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(distance.get(preIndex), 0);
        canvas.drawRect(start, getMeasuredHeight() - height, textViews.get(preIndex).getMeasuredWidth(), getMeasuredHeight(), paint);
        canvas.restore();
    }

    private int dip2Px(float dip) {
        return (int) (dip * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    public static class Builder {
        //  未选择的tab字体颜色
        private String textColor = "#000000";
        //  选中的tab字体颜色
        private String selectTestColor = "#00FF00";
        //  未选择的tab字体大小
        private int textSize = 15;
        //  选中的tab字体大小
        private int selectTextSize = 15;
        //  右上角数字的颜色
        private String countColor = "#FFFFFF";
        //  右上角圆形背景颜色
        private String countBgColor = "#FF0000";
        //  右上角字体的大小
        private int countSize = 8;
        // 右上角背景的大小
        private int countBgSize = 13;
        //  tab 标题的集合
        private List<String> data;
        //  下面横线的颜色
        private String lineSelectColor = "#00FF00";
        //  下面横线的高度
        private int lineHeight = 3;

        protected String getLineSelectColor() {
            return lineSelectColor;
        }

        public Builder lineSelectColor(String lineSelectColor) {
            this.lineSelectColor = lineSelectColor;
            return this;
        }

        protected int getLineHeight() {
            return lineHeight;
        }

        public Builder lineHeight(int lineHeight) {
            this.lineHeight = lineHeight;
            return this;
        }

        protected List<String> getData() {
            return data;
        }

        public Builder data(List<String> data) {
            this.data = data;
            return this;
        }

        protected String getTextColor() {
            return textColor;
        }

        public Builder textColor(String textColor) {
            this.textColor = textColor;
            return this;
        }

        protected String getSelectTestColor() {
            return selectTestColor;
        }

        public Builder selectTestColor(String selectTestColor) {
            this.selectTestColor = selectTestColor;
            return this;
        }

        protected int getTextSize() {
            return textSize;
        }

        public Builder textSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        protected int getSelectTextSize() {
            return selectTextSize;
        }

        public Builder selectTextSize(int selectTextSize) {
            this.selectTextSize = selectTextSize;
            return this;
        }

        protected String getCountColor() {
            return countColor;
        }

        public Builder countColor(String countColor) {
            this.countColor = countColor;
            return this;
        }

        protected String getCountBgColor() {
            return countBgColor;
        }

        public Builder countBgColor(String countBgColor) {
            this.countBgColor = countBgColor;
            return this;
        }

        protected int getCountSize() {
            return countSize;
        }

        public Builder countSize(int countSize) {
            this.countSize = countSize;
            return this;
        }

        protected int getCountBgSize() {
            return countBgSize;
        }

        public Builder countBgSize(int countBgSize) {
            this.countBgSize = countBgSize;
            return this;
        }
    }
}
