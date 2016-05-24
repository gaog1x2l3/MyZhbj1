package mobiletrain.org.myzhbj1.base.impl;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import mobiletrain.org.myzhbj1.MainActivity;
import mobiletrain.org.myzhbj1.base.BaseMenuDetailPager;
import mobiletrain.org.myzhbj1.base.BasePager;
import mobiletrain.org.myzhbj1.base.menudetail.InteractMenuDetailPager;
import mobiletrain.org.myzhbj1.base.menudetail.NewsMenuDetailPager;
import mobiletrain.org.myzhbj1.base.menudetail.PhotoMenuDetailPager;
import mobiletrain.org.myzhbj1.base.menudetail.TopicMenuDetailPager;
import mobiletrain.org.myzhbj1.domain.NewsData;
import mobiletrain.org.myzhbj1.fragment.LeftMenuFragment;
import mobiletrain.org.myzhbj1.global.GlobalContants;
import mobiletrain.org.myzhbj1.utils.OkHttpClientManager;

/**
 * Created by Administrator on 2016/5/20.
 * 新闻中心
 */
public class NewsCenterPager extends BasePager {
    private ArrayList<BaseMenuDetailPager> mPagers;//4个菜单详情页的集合
    private NewsData mNewsData;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           Object obj =  msg.obj;
            System.out.println(obj+"--obj");
            if(obj!=null){
                String json = (String) obj.toString();
                parseData(json);
            }
        }
    };

    public NewsCenterPager(Activity activity) {
        super(activity);

    }

    @Override
    public void initData() {
        System.out.println("初始化新闻中心数据.....");
        tvTitle.setText("新闻");
        setSlidingMenuEnable(true);//打开侧边栏
        getDataFromServer();
    }

    /**
     * 从服务器获取数据。
     * 使用okHttp框架
     */

    private void getDataFromServer() {
        new Thread(){
            @Override
            public void run() {
                try {
                  String json =  OkHttpClientManager.getAsString(GlobalContants.CATEGORIES_URL);
                    System.out.println(json+"======");
                    Message msg = handler.obtainMessage();
                    msg.obj = json;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    /**
     * 解析网络数据
     */
    protected  void parseData(String result){
        Gson gson = new Gson();
        mNewsData = gson.fromJson(result,NewsData.class);
        System.out.println("解析结果:"+mNewsData);
        //刷新侧边栏的数据
        MainActivity mainUi = (MainActivity) mActivity;
        LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
        leftMenuFragment.setmenuData(mNewsData);
        //准备4个菜单详情页
        mPagers = new ArrayList<BaseMenuDetailPager>();
        mPagers.add(new NewsMenuDetailPager(mActivity,mNewsData.data.get(0).children));
        mPagers.add(new TopicMenuDetailPager(mActivity));
        mPagers.add(new PhotoMenuDetailPager(mActivity));
        mPagers.add(new InteractMenuDetailPager(mActivity));
        setCurrentMenuDetailPager(0);//设置菜单详情页-新闻为默认当前页
    }
    /**
     * 设置当前菜单详情页
     */
    public void setCurrentMenuDetailPager(int position){
        BaseMenuDetailPager pager = mPagers.get(position);//获取当前要显示的菜单详情页
        flContent.removeAllViews();//清除之前的布局
        flContent.addView(pager.mRootView);//将菜单详情页的布局设置给帧布局
        //设置当前页的标题
        NewsData.NewsMenuData menuData = mNewsData.data.get(position);
        tvTitle.setText(menuData.title);
        pager.initData();//初始化当前页面的数据。
    }
}
