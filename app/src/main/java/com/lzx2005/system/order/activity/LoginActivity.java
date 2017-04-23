package com.lzx2005.system.order.activity;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
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
            String host = getResources().getString(R.string.server_host);
            String url = host + "/user/login?username="+username.getText()+"&password="+password.getText();
            LoginTask loginTask = new LoginTask(url, handler);
            new Thread(loginTask).start();
        });

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.e("lzx2005", val);
            //alert(val);
            SharedPreferences loginInfo = getSharedPreferences("loginInfo", 0);

            JSONObject jsonObject = JSONObject.parseObject(val);
            if(jsonObject.getInteger("code")==0){
                String token = jsonObject.getString("data");
                if(TextUtils.isEmpty(token)){
                    alert("无法获取服务器返回的登录验证信息");
                }else{
                    alert("登录成功！");
                    loginInfo.edit().putString("token",token).apply();
                    //跳转
                }
            }else{
                alert(jsonObject.getString("msg"));
            }
        }
    };


    private void alert(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }
}
