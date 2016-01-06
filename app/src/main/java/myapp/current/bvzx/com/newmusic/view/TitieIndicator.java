package myapp.current.bvzx.com.newmusic.view;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import myapp.current.bvzx.com.newmusic.R;
import myapp.current.bvzx.com.newmusic.activity.MainActivity;

/**
 * Created by wuhao on 2016/1/6.
 */
public class TitieIndicator {
    ViewPager viewPager;

    ViewPager viewPager_all;

    Activity mActivity;

    LinearLayout ll;

    TextView textView;

    public TitieIndicator(Activity context,LinearLayout tv) {
        mActivity = context;
        ll=tv;
        textView= (TextView) tv.findViewById(R.id.title_action_bar);
    }

    public void setViewPager(ViewPager viewPager,ViewPager viewPager1) {
        this.viewPager = viewPager;
        this.viewPager_all=viewPager1;
    }

    public void apply() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (viewPager.getAdapter()!=null){
                    if ((1 - positionOffset * 2) <= 0) {
                        textView.setText(viewPager.getAdapter().getPageTitle(position+1));
                        textView.setAlpha(positionOffset * 2 - 1);
                        Log.e("11", "111");
                    } else {
                        textView.setText(viewPager.getAdapter().getPageTitle(position));
                        textView.setAlpha((1 - positionOffset * 2));
                        Log.e("11", "44444444444");
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager_all.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if ((position +positionOffset)<1) {
                    textView.setVisibility(View.VISIBLE);
                    ((MainActivity)mActivity).title_text.setTextSize(positionOffset * 15 + 20);
                    textView.setTextSize(15-positionOffset*15);
                    textView.setAlpha(1 - positionOffset * 2);
                }else {
                    textView.setVisibility(View.INVISIBLE);
                    textView.setAlpha(positionOffset * 2 - 1);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
