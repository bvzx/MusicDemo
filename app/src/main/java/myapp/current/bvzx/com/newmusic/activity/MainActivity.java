package myapp.current.bvzx.com.newmusic.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import myapp.current.bvzx.com.newmusic.R;
import myapp.current.bvzx.com.newmusic.pager.BasePager;
import myapp.current.bvzx.com.newmusic.pager.imp.DiscoverPager;
import myapp.current.bvzx.com.newmusic.pager.imp.LocalPager;
import myapp.current.bvzx.com.newmusic.pager.imp.SearchPager;
import myapp.current.bvzx.com.newmusic.view.PicPagerIndicator;

public class MainActivity extends AppCompatActivity {

    public ViewPager content_viewPager;

    private BasePager[] basePager;

    private String[] title=new String[]{"喜爱明星","音乐管理","搜索"};

    public TextView title_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        setContentView(R.layout.main);
        //初始化view
        initView();
        //初始化view数据
        initData();
    }

    private void initView() {
        basePager=new BasePager[3];
        basePager[0]=new DiscoverPager(this);
        basePager[1]=new LocalPager(this);
        basePager[2]=new SearchPager(this);
        content_viewPager = (ViewPager) findViewById(R.id.content_vp);
        title_text= (TextView) findViewById(R.id.action_bar_text);
    }

    private void initData() {
        content_viewPager.setAdapter(new MyContentAdapter());
        PicPagerIndicator picPagerIndicator=new PicPagerIndicator(this);
        picPagerIndicator.setViewPager(content_viewPager);
        picPagerIndicator.setData(new int[]{R.id.discover_muisc, R.id.local_music, R.id.search_music});
        picPagerIndicator.apply();
        picPagerIndicator.selectTab(0);
        content_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if ((1-positionOffset*2)<=0){
                    title_text.setText(title[(position + 1) % 3]);
                    title_text.setAlpha(positionOffset*2-1);
                }else{
                    title_text.setText(title[position % 3]);
                    title_text.setAlpha((1 - positionOffset * 2));
                    title_text.setPadding(0,0,position,0);
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

    class MyContentAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return basePager.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(basePager[position].rootView);
            return basePager[position].rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((DiscoverPager)basePager[0]).updateData();
    }
}
