package myapp.current.bvzx.com.newmusic.pager;

import android.app.Activity;
import android.view.View;

/**
 * Created by wuhao on 2016/1/5.
 */
public abstract class BasePager {

    public Activity mActivity;

    public View rootView;

    public BasePager(Activity activity){
        mActivity=activity;
        rootView=initView();
        initData(rootView);
    }

    public abstract View initView();

    public abstract void  initData(View rootView);

}
