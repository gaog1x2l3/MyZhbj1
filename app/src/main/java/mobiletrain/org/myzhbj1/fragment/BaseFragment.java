package mobiletrain.org.myzhbj1.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * fragment基类
 * Created by Administrator on 2016/5/19.
 */
public  abstract class BaseFragment extends Fragment {
    public Activity mActivity;
    //fragment创建

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();//获得Fragment所在的activity.
    }

    //处理fragment的布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initViews();
    }
    //依附的activity创建完成
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    //初始化数据，可以不实现
    public void initData() {
    }

    //子类必须实现初始化布局的方法,因为每个fragment布局是不一样的
    protected abstract View initViews();
}
