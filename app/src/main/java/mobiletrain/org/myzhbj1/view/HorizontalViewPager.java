package mobiletrain.org.myzhbj1.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/5/25.
 */
public class HorizontalViewPager extends ViewPager {
    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //alt+inert---构造方法快捷键
    public HorizontalViewPager(Context context) {
        super(context);
    }

    /**
     * 事件分发，请求父控件及祖宗控件是否拦截事件
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentItem()!=0){
            //用getParent去请求,不拦截
            getParent().requestDisallowInterceptTouchEvent(true);
        }else{
            //如果是第一个页面，需要显示侧边栏，请求父控件拦截
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(ev);
    }
}
