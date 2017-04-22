package com.example.administrator.stablayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tablayout.STabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private STabLayout sTabLayout;

    private ViewPager viewPager;

    private List<View> textViews = new ArrayList<>();

    private Adater adater;

    private List<Integer> count = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sTabLayout = (STabLayout) findViewById(R.id.stab);
        List<String> list = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.vp);

        list.add("邓紫棋");
        list.add("范冰冰");
        list.add("李冰冰");
        list.add("唐嫣");
        list.add("高圆圆");
        list.add("刘诗诗");
        list.add("柳岩");
        list.add("张柏芝");
        list.add("苍井空");
        list.add("赵丽颖");
        list.add("杨幂");
        list.add("濑亚美莉");
        list.add("刘亦菲");
        list.add("田静");
        list.add("长泽梓");

        for (int i = 0; i < list.size(); i++) {
            View inflate = getLayoutInflater().inflate(R.layout.item_content, viewPager, false);
            TextView textView = (TextView) inflate.findViewById(R.id.tv_content);
            textView.setText(list.get(i));
            count.add(i);
            textViews.add(inflate);
        }

        STabLayout.Builder builder = new STabLayout.Builder().data(list).textSize(15).selectTextSize(20).lineSelectColor("#0000FF").lineHeight(3);
        adater = new Adater(textViews);
        sTabLayout.setTitleData(builder, viewPager);
        sTabLayout.updateCount(count);
        viewPager.setAdapter(adater);
    }
}
