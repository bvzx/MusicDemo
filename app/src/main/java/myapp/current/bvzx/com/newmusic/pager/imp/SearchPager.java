package myapp.current.bvzx.com.newmusic.pager.imp;

import android.app.Activity;
import android.view.View;

import myapp.current.bvzx.com.newmusic.R;
import myapp.current.bvzx.com.newmusic.pager.BasePager;

/**
 * Created by wuhao on 2016/1/5.
 */
public class SearchPager extends BasePager {

    public SearchPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        return View.inflate(mActivity, R.layout.pager_search,null);
    }

    @Override
    public void initData(View rootView) {

    }

}
