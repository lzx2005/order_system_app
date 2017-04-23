package com.lzx2005.system.order.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzx2005.system.order.R;
import com.lzx2005.system.order.http.task.LoginTask;


public class LoginActivity extends AppCompatActivity {
    TextView username;
    TextView password;
    Button loginSubmit;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        loginSubmit = (Button) findViewById(R.id.sign_in_button);
        linearLayout = (LinearLayout)findViewById(R.id.username_login_form);

        //loginSubmit.setEnabled(false);
        loginSubmit.setOnClickListener(v ->{
            if(TextUtils.isEmpty(username.getText())){
                alert("请输入用户名");
                //loginSubmit.setEnabled(false);
                return;
            }
            if(TextUtils.isEmpty(password.getText())){
                alert("请输入密码");
                //loginSubmit.setEnabled(false);
                return;
            }
            //alert(username.getText()+","+password.getText());
            LoginTask loginTask = new LoginTask("http://www.baidu.com", handler);
            new Thread(loginTask).start();
        });

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("lzx2005", val);


            WebView webView = new WebView(getApplicationContext());
            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            webView.setLayoutParams(layoutParams);
            linearLayout.addView(webView);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });



            webView.loadUrl("https://www.baidu.com");
            //webView.loadData(val, );
        }
    };


    private void alert(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }
}
