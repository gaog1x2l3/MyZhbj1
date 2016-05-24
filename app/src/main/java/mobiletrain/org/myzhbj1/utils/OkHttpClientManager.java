package mobiletrain.org.myzhbj1.utils;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by Administrator on 2016/5/20.
 * okHttp封装类
 */
public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private static final String TAG = "OkHttpClientManager";
    private OkHttpClientManager(){
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());

    }
    public static OkHttpClientManager getmInstance(){
        if(mInstance==null){
            synchronized (OkHttpClientManager.class){
                if(mInstance==null){
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 同步的Get请求
     * @param url
     * @return Response
     * @throws IOException
     */
    private Response _getAsyn(String url) throws IOException {
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    /**
     * 同步的Get请求
     * @param url
     * @return 字符串
     * @throws IOException
     */
    private String _getAsString(String url) throws IOException {
        Response execute = _getAsyn(url);
        return execute.body().string();
    }

    /***对外公布的方法****/
    public static Response getAsyn(String url) throws IOException {
        return getmInstance()._getAsyn(url);
    }
    public static String getAsString(String url) throws IOException {
        System.out.println(Thread.currentThread().getName()+"-------");
        return getmInstance()._getAsString(url);
    }
}
