package mobiletrain.org.myzhbj1.base;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import mobiletrain.org.myzhbj1.domain.NewsData;

/**
 * Created by Administrator on 2016/5/20.
 * 页签详情页
 */
public class TabDetailPager extends BaseMenuDetailPager {
    NewsData.NewsTabData mTabData;
    private TextView tvText;
    public TabDetailPager(Activity activity,NewsData.NewsTabData newsTabData){
        super(activity);
        mTabData = newsTabData;
    }
    @Override
    protected View initViews() {
        tvText = new TextView(mActivity);
        tvText.setText("页签详情页");
        tvText.setTextColor(Color.RED);
        tvText.setTextSize(25);
        tvText.setGravity(Gravity.CENTER);
        return tvText;
    }

    @Override
    public void initData() {
        tvText.setText(mTabData.title);
    }
}
