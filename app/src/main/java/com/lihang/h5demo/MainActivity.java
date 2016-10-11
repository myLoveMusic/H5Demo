package com.lihang.h5demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private Button btn;

    private WebAppInterface appInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.wv);
        mWebView.loadUrl("file:///android_asset/index.html");
        mWebView.getSettings().setJavaScriptEnabled(true);
        appInterface = new WebAppInterface(this);
        //添加通讯接口
        mWebView.addJavascriptInterface(appInterface, "app");

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appInterface.showName("你好");
            }
        });
    }

    //java与js的交互类
    class WebAppInterface{
        private Context context;

        public WebAppInterface(Context context) {
            this.context = context;
        }

        //js调用java
        @JavascriptInterface
        public void sayHello(String name){
            Toast.makeText(context,"name = "+name,Toast.LENGTH_LONG).show();
        }

        //java调用js
        public void showName(String name){
            mWebView.loadUrl("javascript:showName('"+name+"')");
        }
    }

}
