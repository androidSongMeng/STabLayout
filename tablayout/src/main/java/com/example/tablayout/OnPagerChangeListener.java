package com.example.tablayout;

/**
 * Created by Administrator on 2017/4/22 0022.
 */

public interface OnPagerChangeListener {

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    public void onPageSelected(int position);

    public void onPageScrollStateChanged(int state);
}
