package myapp.current.bvzx.com.newmusic.pager.imp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import myapp.current.bvzx.com.newmusic.R;
import myapp.current.bvzx.com.newmusic.activity.FavoriteStarActivity;
import myapp.current.bvzx.com.newmusic.pager.BasePager;

/**
 * Created by wuhao on 2016/1/5.
 */
public class LocalPager extends BasePager {


    int[] drawable = new int[]{R.drawable.lay_icn_mobile, R.drawable.lay_icn_time, R.drawable.lay_icn_artist, R.drawable.lay_icn_dld};

    String[] text = new String[]{"本地音乐", "最近播放", "喜爱明星", "下载管理"};

    public LocalPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        return View.inflate(mActivity, R.layout.pager_local, null);
    }

    @Override
    public void initData(View rootView) {
        ListView lv = (ListView) rootView.findViewById(R.id.local_list);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(mActivity, R.layout.local_listview_item, null);
                }
                ImageView iv = (ImageView) convertView.findViewById(R.id.lay_icn);
                iv.setBackgroundResource(drawable[position]);
                TextView tv = (TextView) convertView.findViewById(R.id.lay_icn_text);
                tv.setText(text[position]);
                return convertView;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 2:
                        mActivity.startActivityForResult(new Intent(mActivity, FavoriteStarActivity.class),0);
                }
            }
        });
    }




}
