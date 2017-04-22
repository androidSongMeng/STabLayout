# STabLayout
>>自定义tablayout  选中字体变大 右上角  添加数量
## Demo
![](https://github.com/androidSongMeng/STabLayout/raw/master/1.png)


[![](https://jitpack.io/v/androidSongMeng/StreamList.svg)](https://jitpack.io/#androidSongMeng/StreamList)


## How to use?
 #### Step 1. Add the JitPack repository to your build file
```java
  allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
#### Step 2. Add the dependency
```java
  dependencies {
	        compile 'com.github.androidSongMeng:STabLayout:1.1'
	}
```
### Step3
```java
   >> 1 .xml 布局中添加
    <com.example.tablayout.STabLayout
        android:id="@+id/stab"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:scrollbars="none" />

    <android.support.v4.view.ViewPager
        android:id="@+id/vp"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
   >> 2 .代码中实现

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

        //  右上角数量集合添加数据
        for (int i = 0; i < list.size(); i++) {
            View inflate = getLayoutInflater().inflate(R.layout.item_content, viewPager, false);
            TextView textView = (TextView) inflate.findViewById(R.id.tv_content);
            textView.setText(list.get(i));
            count.add(i);
            textViews.add(inflate);
        }

        STabLayout.Builder builder = new STabLayout.Builder().data(list).textSize(15).selectTextSize(20).lineSelectColor("#0000FF").lineHeight(3);
        // 实例化adapter
        adater = new Adater(textViews);
        //  设置数据  绑定viewPager
        sTabLayout.setTitleData(builder, viewPager);
        //  更新右上角数量
        sTabLayout.updateCount(count);
        viewPager.setAdapter(adater);
        //  为viewPager 设置滑动监听
        sTabLayout.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

```


