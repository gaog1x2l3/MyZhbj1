package mobiletrain.org.myzhbj1.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2016/5/19.
 * 菜单详情页基类
 */
public  abstract class BaseMenuDetailPager {
    public Activity mActivity;
    public View mRootView;//根布局对象
    public BaseMenuDetailPager(Activity activity){
        mActivity = activity;
        mRootView = initViews();
    }

    /**
     * 初始化界面
     * @return
     */
    protected abstract View initViews();
    /**
     * 初始化数据
     */
    public void initData(){

    }
}
