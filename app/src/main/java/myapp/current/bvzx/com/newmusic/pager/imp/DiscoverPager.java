package myapp.current.bvzx.com.newmusic.pager.imp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.viewpagerindicator.TitlePageIndicator;

import java.io.IOException;
import java.util.ArrayList;

import myapp.current.bvzx.com.newmusic.R;
import myapp.current.bvzx.com.newmusic.activity.MainActivity;
import myapp.current.bvzx.com.newmusic.bean.MusicSet;
import myapp.current.bvzx.com.newmusic.pager.BasePager;
import myapp.current.bvzx.com.newmusic.service.MusicService;
import myapp.current.bvzx.com.newmusic.utils.MusicVistior;
import myapp.current.bvzx.com.newmusic.utils.SPUtils;
import myapp.current.bvzx.com.newmusic.view.TitieIndicator;

/**
 * Created by wuhao on 2016/1/5.
 */
public class DiscoverPager extends BasePager {

    String [] title;

    ArrayList<MusicSet> musicSets;
    private MusicService.MyBinder binder;
    private ViewPager viewPager;
    private AsyncTask<String, Void, MusicSet> execute;
    private DiscoverAdapt da;

    private TextView tip;

    public DiscoverPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        return View.inflate(mActivity, R.layout.pager_discover,null);
    }

    @Override
    public void initData(final View rootView) {
        musicSets=new ArrayList<>();
        title=handleStarName(SPUtils.getFavouriteStar(mActivity));
        viewPager = (ViewPager) rootView.findViewById(R.id.discover_viewpager);
        tip = (TextView) rootView.findViewById(R.id.tip);
        execute = new AsyncTask<String,Void,MusicSet>() {
            @Override
            protected MusicSet doInBackground(String[] params) {
                for (String a : title) {
                    MusicSet musicSet = MusicVistior.getMusicSet(a, 11, 1);
                    musicSets.add(musicSet);
                }
                return null;
            }

            @Override
            protected void onPostExecute(MusicSet o) {
                da = new DiscoverAdapt();
                viewPager.setAdapter(da);
                tip.setVisibility(View.GONE);
                da.notifyDataSetChanged();
                TitieIndicator titieIndicator=new TitieIndicator(mActivity,(LinearLayout)(mActivity.findViewById(R.id.ll)));
                titieIndicator.setViewPager(viewPager,((MainActivity)mActivity).content_viewPager);
                titieIndicator.apply();
            }
        }.execute();
    }

    class DiscoverAdapt extends PagerAdapter{
        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container,  final int pos) {
            View v=View.inflate(mActivity, R.layout.music_viewpager, null);
            final GridView gv= (GridView) v.findViewById(R.id.discover_grid);
            final MyGridViewAdapter adapter=new MyGridViewAdapter(pos);
            gv.setAdapter(new MyGridViewAdapter(pos));
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    if (position==gv.getCount()-1){
                        new AsyncTask<String,Void,MusicSet>() {
                            @Override
                            protected MusicSet doInBackground(String[] params) {
                                    MusicSet.MusicData[] a=musicSets.get(pos).data.data.list;
                                    MusicSet musicSet= MusicVistior.getMusicSet(title[pos], 11, a.length/11+1);
                                    MusicSet.MusicData[] b=musicSet.data.data.list;
                                    MusicSet.MusicData[] c= new MusicSet.MusicData[a.length+b.length];
                                    System.arraycopy(a, 0, c, 0, a.length);
                                    System.arraycopy(b, 0, c, a.length, b.length);
                                    musicSets.get(pos).data.data.list=c;
                                return null;
                            }

                            @Override
                            protected void onPostExecute(MusicSet o) {
                               // adapter.notifyDataSetChanged();
                                Parcelable state=gv.onSaveInstanceState();
                                ListAdapter listAdapter =gv.getAdapter();
                                listAdapter=null;
                                gv.setAdapter(new MyGridViewAdapter(pos));
                                gv.onRestoreInstanceState(state);
                            }
                        }.execute();
                    }else{
                        if (binder==null){
                            mActivity.bindService(new Intent(mActivity, MusicService.class), new ServiceConnection() {
                                @Override
                                public void onServiceConnected(ComponentName name, IBinder service) {
                                    if (binder==null){
                                        binder = (MusicService.MyBinder) service;
                                        binder.initMusic(mActivity,musicSets.get(pos).data.data.list[position].songUrl);
                                        binder.setMusicBar(musicSets.get(pos).data.data.list[position]);
                                    }
                                }

                                @Override
                                public void onServiceDisconnected(ComponentName name) {
                                    binder=null;
                                }
                            }, Context.BIND_AUTO_CREATE);
                        }else{
                            try {
                                binder.replay(musicSets.get(pos).data.data.list[position].songUrl);
                                binder.setMusicBar(musicSets.get(pos).data.data.list[position]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }


    class MyGridViewAdapter extends BaseAdapter{

        int pos;

        public MyGridViewAdapter(int i){
            pos=i;
        }


        @Override
        public int getCount() {
            return musicSets.get(pos).data.data.list.length+1;
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
            if (convertView==null){
                convertView= View.inflate(mActivity,R.layout.music_gridview_item,null);
            }
            if (position==getCount()-1){
                TextView tv = (TextView) convertView.findViewById(R.id.gv_text);
                tv.setText("加载更多");
            }else{
                ImageView iv = (ImageView) convertView.findViewById(R.id.gv_img);
                BitmapUtils bitmapUtils=new BitmapUtils(mActivity);
                bitmapUtils.display(iv,musicSets.get(pos).data.data.list[position].albumPic);
                TextView tv = (TextView) convertView.findViewById(R.id.gv_text);
                tv.setText(musicSets.get(pos).data.data.list[position].songName);
            }
            return convertView;
        }
    }

    public String[] handleStarName(String data){
        return data.subSequence(0, data.length()-1).toString().split(",");
    }

    public void updateData(){
        title=handleStarName(SPUtils.getFavouriteStar(mActivity));
        viewPager.setAdapter(null);
        tip.setVisibility(View.VISIBLE);
        new AsyncTask<String,Void,MusicSet>() {
            @Override
            protected MusicSet doInBackground(String[] params) {
                musicSets=new ArrayList<MusicSet>();
                for (String a:title){
                    MusicSet musicSet= MusicVistior.getMusicSet(a, 11, 1);
                    musicSets.add(musicSet);
                }
                return null;
            }

            @Override
            protected void onPostExecute(MusicSet o) {
                viewPager.setAdapter(new DiscoverAdapt());
                tip.setVisibility(View.GONE);
            }
        }.execute();

    }

}
