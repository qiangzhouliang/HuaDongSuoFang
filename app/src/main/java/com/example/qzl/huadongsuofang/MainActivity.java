package com.example.qzl.huadongsuofang;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int sWidthPadding = UIUtils.dip2px(24);
    public static int sHeightPadding = UIUtils.dip2px(32);

    private ViewPager mvp_guid;
    private List<ImageView> mImageViewList;//imageview集合
    //引导页图片id数组
    private int[] mImageId = new int[]{ R.drawable.guide_1,R.drawable.guide_2, R.drawable.guide_3,R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private ImageView mImageView;
    private int mOutHeightPadding;
    private int mOutWidthPadding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取标题
        setContentView(R.layout.activity_guide);
        mvp_guid = (ViewPager) findViewById(R.id.vp_guid);

        InitData();
        mvp_guid.setAdapter(new GuideAdapter());
        mvp_guid.setOffscreenPageLimit(3);
        mvp_guid.setCurrentItem(mImageViewList.size()*10000/2);
        //页面滑动的监听
        mvp_guid.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //当页面滑动过程中的回调
            // position ： 当前位置  positionOffset ： 移动偏移量（百分比）  positionOffsetPixels ： 具体移动了多少个像素
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                position = position % mImageViewList.size();
                if (mImageViewList.size() > 0 && position < mImageViewList.size()) {
                    //当前手指触摸滑动的页面,从0页滑动到1页 offset越来越大，padding越来越大
                    mOutHeightPadding = (int) (positionOffset * sHeightPadding);
                    mOutWidthPadding = (int) (positionOffset * sWidthPadding);
                    mImageViewList.get(position).setPadding(mOutWidthPadding, mOutHeightPadding, mOutWidthPadding, mOutHeightPadding);

                    if (position < mImageViewList.size() - 1) {
                        int inWidthPadding = (int) ((1 - positionOffset) * sWidthPadding);
                        int inHeightPadding = (int) ((1 - positionOffset) * sHeightPadding);
                        mImageViewList.get(position + 1).setPadding(inWidthPadding, inHeightPadding, inWidthPadding, inHeightPadding);
                    }else {
                        int inWidthPadding = (int) ((1 - positionOffset) * sWidthPadding);
                        int inHeightPadding = (int) ((1 - positionOffset) * sHeightPadding);
                        mImageViewList.get(0).setPadding(inWidthPadding, inHeightPadding, inWidthPadding, inHeightPadding);
                    }
                }
            }

            //页面被选中的时候
            @Override
            public void onPageSelected(int position) {
            }

            //页面状态改变的时候
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化数据（ImageView）
     */
    private void InitData() {
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageId.length; i++) {
            mImageView = new ImageView(this);
            mImageView.setImageResource(mImageId[i]);
            mImageView.setPadding(sWidthPadding,sHeightPadding,sWidthPadding,sHeightPadding);
            mImageViewList.add(mImageView);
        }
    }

    class GuideAdapter extends PagerAdapter {
        private ImageView mView;

        //返回item的个数
        @Override
        public int getCount() {
            //return mImageViewList == null ? 0 : mImageViewList.size();
            return Integer.MAX_VALUE;
        }

        //判断是不是view
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //初始化item的布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mImageViewList.size();
            ViewParent parent = mImageViewList.get(position).getParent();
            if (parent != null){
                container.removeView(mImageViewList.get(position));
            }
            mView = mImageViewList.get(position);
            container.addView(mView);//将图片添加到布局中
            return mView;

        }

        //销毁item布局
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //container.removeView((View) object);
        }
    }
}
