package mobiletrain.org.myzhbj1.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import mobiletrain.org.myzhbj1.R;
import mobiletrain.org.myzhbj1.domain.NewsData;
import mobiletrain.org.myzhbj1.domain.TabData;
import mobiletrain.org.myzhbj1.global.GlobalContants;
import mobiletrain.org.myzhbj1.view.RefreshListView;
import mobiletrain.org.myzhbj1.view.StringRequest;

/**
 * Created by Administrator on 2016/5/20.
 * 页签详情页
 */
public class TabDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {
    NewsData.NewsTabData mTabData;
    private String mUrl;
    private TabData mTabDetailData;
    private ViewPager mViewPager;
    private TextView tvTitle;//头条新闻的标题
    private ArrayList<TabData.TopNewsData> mTopNewsList;//头条新闻数据集合
    private CirclePageIndicator mIndicator;//头条新闻位置指示器
    private RefreshListView lvList;//新闻列表
    private ArrayList<TabData.TabNewsData> mNewsList;//新闻数据集合
    private NewsAdapter mNewsAdapter;
    private RequestQueue queue;

    public TabDetailPager(Activity activity,NewsData.NewsTabData newsTabData){
        super(activity);
        mTabData = newsTabData;
        mUrl = GlobalContants.SERVEL_URL+mTabData.url;
        queue = Volley.newRequestQueue(mActivity);
    }
    @Override
    protected View initViews() {
       View view = View.inflate(mActivity, R.layout.tab_detail_pager,null);
        //加载头布局
        View headerView = View.inflate(mActivity,R.layout.list_header_topnews,null);
        mViewPager = (ViewPager) headerView.findViewById(R.id.vp_news);
        tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        mIndicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);
        lvList = (RefreshListView) view.findViewById(R.id.lv_list);
        //将头条新闻以头布局的形式加给listview
        lvList.addHeaderView(headerView);
        return view;
    }

    @Override
    public void initData() {
      getDataFromServer();
    }

    /**
     * 从网络中下载数据。并解析。
     * 使用volley框架下载数据。
     */
    public void getDataFromServer() {
        //网络下载json字符串
//        JsonObjectRequest request = new JsonObjectRequest(mUrl, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                System.out.println("success-----"+jsonObject.toString());
//                String result = jsonObject.toString();
//                //解析json字符串
//                parseData(result);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                System.out.println("下载数据失败:"+volleyError.getMessage());
//            }
//        });
        StringRequest request = new StringRequest(mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                System.out.println("success-----"+s);
                parseData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("下载数据失败:"+volleyError.getMessage());
            }
        });

        queue.add(request);

    }

    private void parseData(String result) {
        Gson gson = new Gson();
        mTabDetailData = gson.fromJson(result,TabData.class);
        System.out.println("页签详情解析:"+mTabDetailData);
        mTopNewsList = mTabDetailData.data.topnews;
        mNewsList = mTabDetailData.data.news;
        if(mTopNewsList!=null){
            //创建适配器
            mViewPager.setAdapter(new TopNewsAdapter(queue));
            mIndicator.setViewPager(mViewPager);
            mIndicator.setSnap(true);//支持快照显示
            mIndicator.setOnPageChangeListener(this);
            mIndicator.onPageSelected(0);//让指示层重新定位到第一个点
            byte[] b = mTopNewsList.get(0).title.getBytes();
            String text = null;
            try {
                text = new String(b,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("编码转换发生了异常");
            }

            System.out.println("标题---"+text);
            tvTitle.setText(text);
        }
        if(mNewsList!=null){
            //创建适配器，设置适配器。
            mNewsAdapter = new NewsAdapter(queue);
            lvList.setAdapter(mNewsAdapter);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabData.TopNewsData topNewsData = mTopNewsList.get(position);
        tvTitle.setText(topNewsData.title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 头条新闻适配器
     */
    class TopNewsAdapter extends PagerAdapter{
        private RequestQueue queue;
        private ImageLoader imageLoader;
        public TopNewsAdapter(RequestQueue queue){
            this.queue = queue;
            imageLoader = new ImageLoader(queue,new BitmapCache());
        }
        @Override
        public int getCount() {
            return mTabDetailData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //创建一个控件来显示图片
            ImageView image = new ImageView(mActivity);
            image.setScaleType(ImageView.ScaleType.FIT_XY);//基于控件大小填充图片
            TabData.TopNewsData topNewsData = mTopNewsList.get(position);
            //获取图片地址
            String imageurl =  topNewsData.topimage;
            //下载图片
            ImageLoader.ImageListener listener = ImageLoader.getImageListener
                    (image,R.mipmap.topnews_item_default,R.mipmap.topnews_item_default);
            imageLoader.get(imageurl,listener);

            //添加控件到容器中。
            container.addView(image);
            System.out.println("instantiateItem....."+position);
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
    //缓存类
    class BitmapCache implements ImageLoader.ImageCache{
        private LruCache<String,Bitmap> mCache;

        public BitmapCache() {
            long maxSize = Runtime.getRuntime().maxMemory();
            mCache = new LruCache<String,Bitmap>((int) (maxSize/8)){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes()*value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String s) {
            return mCache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            mCache.put(s, bitmap);
        }
    }
    class NewsAdapter extends BaseAdapter{
        private RequestQueue queue;
        private ImageLoader imageLoader;
        public NewsAdapter(RequestQueue queue){
            this.queue = queue;
            imageLoader = new ImageLoader(queue,new BitmapCache());
        }
        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                convertView = View.inflate(mActivity,R.layout.list_news_item,null);
                holder = new ViewHolder();
                holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();

            }
            TabData.TabNewsData item = (TabData.TabNewsData) getItem(position);
            holder.tvTitle.setText(item.title);
            holder.tvDate.setText(item.pubdate);
            System.out.println("图片网址:"+item.listimage);
            //显示图片。
            ImageLoader.ImageListener listener = ImageLoader.getImageListener
                    (holder.ivPic,R.mipmap.pic_item_list_default,R.mipmap.ic_launcher);
            imageLoader.get(item.listimage,listener);
            return convertView;
        }
         class ViewHolder{
            public TextView tvTitle;
            public TextView tvDate;
            public ImageView ivPic;
        }
    }
}
