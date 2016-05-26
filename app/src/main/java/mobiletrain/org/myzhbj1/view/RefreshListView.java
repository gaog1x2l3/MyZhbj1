package mobiletrain.org.myzhbj1.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import mobiletrain.org.myzhbj1.R;

/**
 * 下拉刷新的ListView
 * Created by Administrator on 2016/5/25.
 */
public class RefreshListView extends ListView {
    private View mHeaderView;
    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    /**
     * 初始化头布局
     */
    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.refresh_header,null);
        this.addHeaderView(mHeaderView);
        //让自定义控件去测试宽高，执行onMeasure fangfa
        mHeaderView.measure(0,0);
        int mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);//隐藏头布局
    }
}
