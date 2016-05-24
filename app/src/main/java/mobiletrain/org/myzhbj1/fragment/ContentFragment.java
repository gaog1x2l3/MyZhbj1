package mobiletrain.org.myzhbj1.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.ArrayList;

import mobiletrain.org.myzhbj1.R;
import mobiletrain.org.myzhbj1.base.BasePager;
import mobiletrain.org.myzhbj1.base.impl.HomePager;
import mobiletrain.org.myzhbj1.base.impl.NewsCenterPager;
import mobiletrain.org.myzhbj1.base.impl.SettingPager;
import mobiletrain.org.myzhbj1.base.impl.SmartServicePager;

/**
 * Created by Administrator on 2016/5/19.
 * 主页内容
 */
public class ContentFragment extends BaseFragment {
    private ArrayList<BasePager> mPagerList;//放5个BasePager
    private RadioGroup rgGroup;
    private ViewPager mViewPager;

    @Override
    protected View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_content);
        return view;
    }

    @Override
    public void initData() {
        rgGroup.check(R.id.rb_home);//默认勾选首页
        //初始化5个子页面
        mPagerList = new ArrayList<BasePager>();
        mPagerList.add(new HomePager(mActivity));
        mPagerList.add(new NewsCenterPager(mActivity));
        mPagerList.add(new SmartServicePager(mActivity));
        mPagerList.add(new SettingPager(mActivity));
        mViewPager.setAdapter(new ContentAdapter());
        //监听RadioGroup的选择事件
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        mViewPager.setCurrentItem(0, false);//去掉切换页面的动画
                        break;
                    case R.id.rb_news:
                        mViewPager.setCurrentItem(1,false);//设置当前页面
                        break;
                    case R.id.rb_smart:
                        mViewPager.setCurrentItem(2,false);//设置当前页面
                        break;
                    case R.id.rb_gov:
                        mViewPager.setCurrentItem(3,false);//设置当前页面
                        break;
                    case R.id.rb_setting:
                        mViewPager.setCurrentItem(4,false);//设置当前页面
                        break;
                    default:
                        break;
                }
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagerList.get(position).initData();//获取当前被选中的页面，初始化该页面数据
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPagerList.get(0).initData();//初始化首页数据
    }

    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 获取新闻中心页面
     */
    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) mPagerList.get(1);
    }
}
