package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.db.UserDbHelper;
import com.example.login.entity.UserInfo;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private CheckBox remember;
    private boolean isRemember;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 微信登陆功能
        findViewById(R.id.weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "WeChat login is temporarily unavailable", Toast.LENGTH_SHORT).show();
            }
        });
        // QQ登陆功能
        findViewById(R.id.qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "QQ login is temporarily unavailable", Toast.LENGTH_SHORT).show();
            }
        });

        // 绑定控件
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        remember = findViewById(R.id.check_remember);

        // 获取SharedPreferences对象
        sp = getSharedPreferences("userInfo", MODE_PRIVATE);

        // 看看是否记住密码
        isRemember = sp.getBoolean("isRemember", false);
        if (isRemember) {
            // 将账号和密码设置到文本框中
            username.setText(sp.getString("username", null));
            password.setText(sp.getString("password", null));
            // 设置记住密码选项为选中状态
            remember.setChecked(true);
        }

//        点击注册
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                跳转注册界面
                Intent intent = new Intent(Login.this, register.class);
                startActivity(intent);
            }
        });

        // 监听登录按钮
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();
                if (usernameStr.isEmpty() && passwordStr.isEmpty()) {
                    Toast.makeText(Login.this, "Username and password cannot be empty, please re-enter!", Toast.LENGTH_SHORT).show();
                } else {
                    UserInfo login = UserDbHelper.getInstance(Login.this).login(usernameStr);

                    if (login != null) {
                        if (usernameStr.equals(login.getUsername()) && passwordStr.equals(login.getPassword())) {
                            // 如果登录成功
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("isRemember", isRemember);
                            editor.putString("username", usernameStr);
                            editor.putString("password", passwordStr);
                            // 提交信息
                            editor.commit();

                            // 保存用户名和密码
                            UserInfo.setsUserInfo(login);

                            // 登陆成功
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Username or password is wrong, please re-enter!", Toast.LENGTH_SHORT).show();
                            username.setText("");
                            password.setText("");
                        }
                    } else {
                        Toast.makeText(Login.this, "User name does not exist, please re-enter!！", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                    }
                }
            }
        });

        // 给记住密码绑定监听
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRemember = isChecked;
            }
        });

    }
}