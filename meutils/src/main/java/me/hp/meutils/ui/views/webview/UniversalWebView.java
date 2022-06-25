package me.hp.meutils.ui.views.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.hp.meutils.ui.views.webview.JSInterface;
import me.hp.meutils.utils.StringUtils;

/**
 * @author: hepingdev
 * @created: 2020/6/20.
 * @desc: 满足常见需求的WebView
 */
public class UniversalWebView extends WebView {

    public UniversalWebView(@NonNull Context context) {
        this(context, null);
    }

    //第三个参数不能传递0，否则会导致H5软键盘弹出会出现问题
    public UniversalWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, Resources.getSystem().getIdentifier("webViewStyle", "attr", "android"));
    }

    public UniversalWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebView();
    }

    /**
     * 以下也是在项目开发过程中比较常用的设置，比如H5交互，H5开发调试，相关权限等
     */
    private void initWebView() {
        //对WebView进行配置和管理
        //如果页面中要与js交互，则webView必须设置支持js
        getSettings().setJavaScriptEnabled(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新的窗口
        //设置自适应屏幕，两者合用
        getSettings().setUseWideViewPort(true);//将图片调整到适合webView的大小
        getSettings().setLoadWithOverviewMode(true);//缩放至屏幕的大小
        //缩放操作
        getSettings().setSupportZoom(false);//支持缩放,默认true，下面两项的前提
        getSettings().setAllowContentAccess(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        getSettings().setLoadsImagesAutomatically(true);//支持自动加载图片
//        webSettings.setBlockNetworkImage(true);//千万不要设置！！！，会拦截图片，不会进入资源回调方法
        getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        //关闭多媒体自动播放
        getSettings().setMediaPlaybackRequiresUserGesture(false);
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        getSettings().setAllowFileAccess(true);//可以访问文件
        getSettings().setAllowFileAccessFromFileURLs(true);
        getSettings().setAllowUniversalAccessFromFileURLs(true);
        //允许调试加载到此应用程序的任何WebView中的Web内容（HTML / CSS / JavaScript）。
        //可以启用此标志，以便于调试在WebViews中运行的Web布局和JavaScript代码。
        WebView.setWebContentsDebuggingEnabled(true);

        /**
         * 可自己设置自己的类，否则默认
         */
        setWebChromeClient(new DefaultWebChromeClient());
        setWebViewClient(new DefaultWebViewClient());
    }

    /**
     * 设置给H5调用的原生类 H5交互使用
     * @param object        原生类对象
     * @param interfaceName H5调用原生类对象名称，比如设置成android, 那么H5调用：window.android.xxx();
     */
    @SuppressLint("JavascriptInterface")
    public <C extends JSInterface> void setJavascriptInterface(C object, String interfaceName) {
        addJavascriptInterface(object, interfaceName);
    }

    /**
     * 设置用户代理
     *
     * @param userAgentString
     */
    public void setUserAgentString(String userAgentString) {
        if (StringUtils.isEmpty(userAgentString)) return;
        getSettings().setUserAgentString(userAgentString);
    }


    /**
     * 辅助WebView处理JavaScript的对话框、网站图标、网站title、加载进度
     */
    public static class DefaultWebChromeClient extends WebChromeClient {
        /**
         * H5可以直接打开原生摄像头进行使用，但是需要打开权限。
         * @param request
         */
        @Override
        public void onPermissionRequest(PermissionRequest request) {
            //super默认禁止，取消调用
//            super.onPermissionRequest(request);
            request.grant(request.getResources());
            request.getOrigin();
        }

        /**
         * 网页加载进度，这里没有做处理
         * @param view
         * @param newProgress
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }


    /**
     * 处理WebView各种通知、请求事件
     */
    public static class DefaultWebViewClient extends WebViewClient {

        /**
         * 监听webview加载的地址
         *  return true : 进行地址加载拦截，自己处理地址的加载
         *  return false: 不做拦截，webview自己处理
         * 1. 比如微信H5网页支付流程，支付宝网页支付流程都会用到此方法，拦截到地址我们就可以根据地址判断走何种逻辑
         * @param view
         * @param url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }


        /**
         * 监听H5页面的网络请求，比如加载一张网络图片，或者请求一个接口
         * 我们可以通过这个接口对H5的请求自定义处理，比如拿本地图片给H5，或者本地文件啥的，以流的形式返回数据
         * 注释是一个项目案例，我监听到请求的地址带有特定的标识，标识H5需要加载存储卡的数据，那么原生端需要返回这个数据给H5
         * @param view
         * @param request
         * @return
         */
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
//            FileInputStream inputStream;
//            String url = request.getUrl().toString();
//            if (url.contains(LiveModule.WordModule.key)) {
//                LogUtils.d(TAG, "internal shouldInterceptRequest##url:" + url);
//                String path = url.replace(LiveModule.WordModule.key, "");
//                try {
//                    /*重新构造WebResourceResponse  将数据以流的方式传入*/
//                    File destFile = new File(path.trim());
//                    if (!destFile.exists()) {
//                        try {
//                            //可能是中文导致
//                            path = URLDecoder.decode(path, "UTF-8"); //因为Linux编码为utf-8,这样以后可以得到正确的路径
//                            destFile = new File(path.trim());
//                            if (!destFile.exists()) {
//                                return super.shouldInterceptRequest(view, request);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    inputStream = new FileInputStream(destFile);
//                    String type;
//                    if (path.endsWith("mp4")) {
//                        type = "video/mp4";
//                    } else if (path.endsWith("mp3")) {
//                        type = "audio/mp3";
//                    } else {
//                        type = "image/*";
//                    }
//                    //WebView同源策略导致游戏拿不到本地资源，需要在请求头里面设置，如下
//                    WebResourceResponse response = new WebResourceResponse(type, "UTF-8", inputStream);
//                    response.setStatusCodeAndReasonPhrase(200, "Android Definite.");
//                    //设置头
//                    Map<String, String> headers = new HashMap<>();
//                    headers.put("content-length", destFile.length() + "");
//                    headers.put("content-type", type);
//                    headers.put("Access-Control-Allow-Origin", "*");
//                    response.setResponseHeaders(headers);
//                    /*返回WebResourceResponse*/
//                    return response;
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
        }

        /**
         * SSL证书错误的话，我们给他通行，不做处理
         * @param view
         * @param handler
         * @param error
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }

        /**
         * 页面开始加载监听，这里没有做处理
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        /**
         * 页面结束加载监听，这里没有做处理
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }


//    /**
//     * 调用JS的方法
//     *
//     * @param jsonObject
//     */
//    public void toJs(String jsonObject) {
//        String params = "javascript:onNativeMessage('" + jsonObject + "')";
//        evaluateJavascript(params, new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String value) {
//                Logger.d(TAG, "onReceiveValue: " + value);
//            }
//        });
//    }
}
