package myapp.current.bvzx.com.newmusic.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.io.IOException;

import myapp.current.bvzx.com.newmusic.R;
import myapp.current.bvzx.com.newmusic.bean.MusicSet;


/**
 * Created by wuhao on 2016/1/4.
 */
public class MusicService extends Service {


    MediaPlayer mp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public  class MyBinder extends Binder{

        BitmapUtils bitmapUtils;

        Activity m;

        CheckBox checkBox;

        public void initMusic(Activity mActivity,String url)  {
               try {
                   m=mActivity;
                   if (mp==null){
                       mp=new MediaPlayer();
                   }
                   replay(url);
                   setMusicBarStatus(true);
                   checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                       @Override
                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                           if (isChecked) {
                               startMusic();
                           } else {
                               pauseMusic();
                           }
                       }
                   });

               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

        public void startMusic(){
            mp.start();
            setMusicBarStatus(true);
        }

        public void pauseMusic(){
            mp.pause();
            setMusicBarStatus(false);
        }

        public void stopMusic(){
            mp.stop();
            setMusicBarStatus(false);
        }

        public void replay(String url) throws IOException {
            Uri uri=new Uri.Builder().build();
            mp.reset();
            mp.setDataSource(m, uri.parse(url));
            mp.prepare();
            mp.start();
        }

        public void setMusicBar(MusicSet.MusicData musicData){
            RelativeLayout linearLayout= (RelativeLayout) m.findViewById(R.id.musicbar);
            ImageView imageView= (ImageView) linearLayout.findViewById(R.id.nact_icn_music);
            if (bitmapUtils==null){
                bitmapUtils=new BitmapUtils(m);
            }
            bitmapUtils.display(imageView,musicData.albumPic);
            TextView tv= (TextView) linearLayout.findViewById(R.id.music_title);
            tv.setText(musicData.songName);
            TextView tv2= (TextView) linearLayout.findViewById(R.id.music_detail);
            tv2.setText(musicData.albumName);
            setMusicBarStatus(true);
        }
        public void setMusicBarStatus(boolean isplay){
            RelativeLayout linearLayout= (RelativeLayout) m.findViewById(R.id.musicbar);
            if (checkBox==null){
                checkBox= (CheckBox) linearLayout.findViewById(R.id.playbar_btn_play);
            }
            checkBox.setChecked(isplay);
        }
    }

}
