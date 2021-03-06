package com.cypal.ming.cypal.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.base.BaseActivity;
import com.cypal.ming.cypal.dialog.SharePopupWindowNoCode;
import com.cypal.ming.cypal.utils.Tools;
import com.githang.statusbar.StatusBarCompat;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * @author WebviewActivity
 */
public class WebviewActivity extends BaseActivity {

    @ViewInject(R.id.webview)
    private WebView mWebView;
    @ViewInject(R.id.pb_progressbar)
    private android.widget.ProgressBar ProgressBar;

    private String url;
    private SharePopupWindowNoCode showShareWindow;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // mSlideBackLayout.isComingToFinish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_webview );
        StatusBarCompat.setStatusBarColor( this, getResources().getColor( R.color.white ) );
        ViewUtils.inject( this );
        Tools.webacts.add( this );
        Tools.acts.add( this );
        url = getIntent().getStringExtra( "link_url" );
        System.out.print( "link_url:" + url );
        initTitle();
        title.setText( getIntent().getStringExtra( "link_name" ) );
        init();
        showShareWindow = new SharePopupWindowNoCode( this );
    }

    @Override
    protected void onResume() {
        mWebView.loadUrl( "javascript:onResume()" );
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void init() {
        try {
            mWebView.requestFocus();
            mWebView.setFocusableInTouchMode( true );// 设置可触摸
            mWebView.setWebViewClient( new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl( url );
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished( view, url );
                }
            } );
            mWebView.setWebChromeClient( new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        ProgressBar.setVisibility( View.GONE );
                    } else {
                        if (View.GONE == ProgressBar.getVisibility()) {
                            ProgressBar.setVisibility( View.VISIBLE );
                        }
                        ProgressBar.setProgress( newProgress );
                    }
                    super.onProgressChanged( view, newProgress );
                }
            } );
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled( true );
            webSettings.setJavaScriptCanOpenWindowsAutomatically( true );
            webSettings.setAllowFileAccess( true );// 设置允许访问文件数据
            webSettings.setSupportZoom( true );
            webSettings.setBuiltInZoomControls( true );
            webSettings.setJavaScriptCanOpenWindowsAutomatically( true );
            webSettings.setDomStorageEnabled( true );
            webSettings.setDatabaseEnabled( true );
            webSettings.setDefaultTextEncodingName( "utf-8" );
            // 开启 DOM storage API 功能
            webSettings.setDomStorageEnabled( true );
            // 开启 database storage API 功能
            webSettings.setDatabaseEnabled( true );
            String appCachePath = getApplicationContext().getCacheDir()
                    .getAbsolutePath();
            // String cacheDirPath =
            // getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
            // 设置 Application Caches 缓存目录
            webSettings.setAppCachePath( appCachePath );
            // 开启 Application Caches 功能
            webSettings.setAppCacheEnabled( true );
            webSettings.setDomStorageEnabled( true );
            webSettings.setAppCachePath( appCachePath );
            webSettings.setAllowFileAccess( true );
            webSettings.setAppCacheEnabled( true );
            webSettings.setCacheMode( WebSettings.LOAD_NO_CACHE );
            showWebView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    private void showWebView() { // webView与js交互代码
        // 设置本地调用对象及其接口
        mWebView.addJavascriptInterface( new JavaScriptObject( this ), "native" );
        mWebView.loadUrl( url );
    }


    public class JavaScriptObject {
        Context mContxt;

        public JavaScriptObject(Context mContxt) {
            this.mContxt = mContxt;
        }

        // 绑定事件
        @JavascriptInterface
        public void transfer(int type, String json) {
//            Tools.showToast(WebviewActivity.this, "type=" + type + "json=" +
//                    json);
            System.out.println( "type=" + type + "json=" + json );
            switch (type) {
                case 1:
                    onShowShareWindow( json );
                    break;

            }

        }
    }

    private void onShowShareWindow(String url) {
//        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
//        lp.alpha = 0.6f;
//        this.getWindow().setAttributes(lp);
        showShareWindow.showShareWindow();
        // 显示窗口 (设置layout在PopupWindow中显示的位置)
        showShareWindow.showAtLocation( ProgressBar, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0 );
        showShareWindow.initCommonShareParams( url );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult( requestCode, resultCode, data );

    }

    @Override
    public void onResponse(String response, String url) {
        // TODO Auto-generated method stub
        dismissLoading();

    }


}
