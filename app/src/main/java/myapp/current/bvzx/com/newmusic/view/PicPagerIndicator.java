package myapp.current.bvzx.com.newmusic.view;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

/**
 * Created by wuhao on 2016/1/3.
 */
public class PicPagerIndicator implements View.OnClickListener {

    ViewPager viewPager;

    RadioButton[] radioButtons;

    Activity mActivity;


    public PicPagerIndicator(Activity context) {
        mActivity = context;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public void apply() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setData(int... params) {
        radioButtons = new RadioButton[params.length];
        for (int i = 0; i < params.length; i++) {
            RadioButton layout = (RadioButton) mActivity.findViewById(params[i]);
            radioButtons[i] = layout;
            radioButtons[i].setTag(i);
            radioButtons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int id = (int) v.getTag();
        viewPager.setCurrentItem(id);
    }

    public void selectTab(int id) {
        for (RadioButton b : radioButtons) {
            b.setSelected(false);
        }
        radioButtons[id].setSelected(true);
    }


}