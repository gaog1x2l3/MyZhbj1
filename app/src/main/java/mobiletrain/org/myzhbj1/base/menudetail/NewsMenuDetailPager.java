package mobiletrain.org.myzhbj1.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import mobiletrain.org.myzhbj1.MainActivity;
import mobiletrain.org.myzhbj1.R;
import mobiletrain.org.myzhbj1.base.BaseMenuDetailPager;
import mobiletrain.org.myzhbj1.base.TabDetailPager;
import mobiletrain.org.myzhbj1.domain.NewsData;

/**
 * Created by Administrator on 2016/5/20.
 * 菜单详情页---新闻
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener,View.OnClickListener {
    private ViewPager mViewPager;
    private ArrayList<TabDetailPager> mPagerList;
    private ArrayList<NewsData.NewsTabData> mNewsTabData;//页签网络数据
    private TabPageIndicator mIndicator;
    private ImageButton image_next;
    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsTabData> children) {
        super(activity);
        mNewsTabData = children;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);
        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        image_next = (ImageButton) view.findViewById(R.id.btn_next);
        image_next.setOnClickListener(this);
        //mIndicator.setOnPageChangeListener(this);注意：当ViewPager和Indicator绑定时，滑动监听需要设置
        //给Indicator而不是ViewPager
        mIndicator.setOnPageChangeListener(this);
        return view;
    }

    @Override
    public void initData() {
        mPagerList = new ArrayList<TabDetailPager>();
        //初始化页签数据
        for (int i = 0; i < mNewsTabData.size(); i++) {
            TabDetailPager pager = new TabDetailPager(mActivity, mNewsTabData.get(i));
            mPagerList.add(pager);
        }
        mViewPager.setAdapter(new MenuDetailAdapter());
        //将ViewPager和mIndicator关联起来，必须在viewpager设置完后adapter才才能调用
        mIndicator.setViewPager(mViewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        System.out.println("onPageSelectde......"+position);;
        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        if(position==0){
            //只有在第一个页面(北京),侧边栏才允许出来
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 跳转到下一个页面
     * @param v
     */
    @Override
    public void onClick(View v) {
        System.out.println("我点击了");
        int currentItem = mViewPager.getCurrentItem();
        mViewPager.setCurrentItem(++currentItem);
    }

    class MenuDetailAdapter extends PagerAdapter {
        /**
         * 重写此方法，返回页面标题，用于viewpagerIndicator的页签显示
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {

            return mNewsTabData.get(position).title;
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
