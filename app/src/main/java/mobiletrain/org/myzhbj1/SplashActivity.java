package mobiletrain.org.myzhbj1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import mobiletrain.org.myzhbj1.utils.PrefUtils;

/**
 * 闪屏页
 */
public class SplashActivity extends AppCompatActivity {
    private RelativeLayout rlRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        //开启动画
        startAnim();
    }

    /**
     * 开启动画
     */
    private void startAnim() {
        //动画集合
        AnimationSet set = new AnimationSet(false);
        //旋转动画
        RotateAnimation rotate = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1000);//动画保持时间
        rotate.setFillAfter(true);//保持动画状态
        //缩放动画
        ScaleAnimation scale = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(1000);//动画持续时间
        scale.setFillAfter(true);//保持动画状态
        //渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        alpha.setDuration(2000);//动画时间
        alpha.setFillAfter(true);//保持动画状态
        //将所有动画添加到集合中。
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        //设置动画监听，当动画执行完后跳转到下一个activity
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画开始
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束
                jumpNextPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //动画重复
            }
        });
        //将动画设置给某个控件，让控件执行动画。
        rlRoot.startAnimation(set);
    }

    /**
     * 跳转下一个页面
     */
    private void jumpNextPage() {
        //判断之前有没有显示过新手引导
        boolean userGuide = PrefUtils.getBoolean(this, "is_user_guide_showed",false);
        if(!userGuide){
            //跳转到新手引导页
            startActivity(new Intent(SplashActivity.this,GuideActivity.class));
        }else{
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
        }
        finish();
    }
}
