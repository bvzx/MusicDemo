package myapp.current.bvzx.com.newmusic.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import myapp.current.bvzx.com.newmusic.R;
import myapp.current.bvzx.com.newmusic.utils.SPUtils;

/**
 * Created by wuhao on 2016/1/6.
 */
public class FavoriteStarActivity extends AppCompatActivity {


    private String[] singers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_star_list);
        String  singer= SPUtils.getFavouriteStar(this);
        singers = singer.substring(0,singer.length()-1).split(",");
        ListView v= (ListView) findViewById(R.id.favorite_star_list);
        final BaseAdapter baseAdapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return singers.length;
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
                View v=View.inflate(FavoriteStarActivity.this,R.layout.favorite_star_list_item,null);
                TextView tv= (TextView) v.findViewById(R.id.singer_name);
                tv.setText(singers[position]);
                return v;
            }
        };
        v.setAdapter(baseAdapter);
        Button add= (Button)findViewById(R.id.action_bar_add);
        Button del= (Button)findViewById(R.id.action_bar_del);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FavoriteStarActivity.this);
                final View appearview = View.inflate(FavoriteStarActivity.this, R.layout.add_sing, null);
                alertDialog.setNegativeButton("取消", null);
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String singname = SPUtils.getFavouriteStar(FavoriteStarActivity.this);
                        EditText et = (EditText) appearview.findViewById(R.id.singe_edit);
                        String name = et.getText().toString();
                        singname = singname + name + ",";
                        SPUtils.setFavouriteStar(FavoriteStarActivity.this, singname);
                        String  singer= SPUtils.getFavouriteStar(FavoriteStarActivity.this);
                        singers = singer.substring(0,singer.length()-1).split(",");
                        baseAdapter.notifyDataSetInvalidated();
                    }
                });
                AlertDialog dialog=alertDialog.create();
                dialog.setView(appearview);
                dialog.show();
            }
        });
    }

}
