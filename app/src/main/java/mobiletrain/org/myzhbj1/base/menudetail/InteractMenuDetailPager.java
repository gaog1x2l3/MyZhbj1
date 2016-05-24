package mobiletrain.org.myzhbj1.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import mobiletrain.org.myzhbj1.base.BaseMenuDetailPager;

/**
 * Created by Administrator on 2016/5/20.
 * 菜单详情页--互动
 */
public class InteractMenuDetailPager extends BaseMenuDetailPager {
    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    protected View initViews() {
        TextView text = new TextView(mActivity);
        text.setText("菜单详情页-互动");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);
        return text;
    }
}
