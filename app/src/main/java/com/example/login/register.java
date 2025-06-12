package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.db.UserDbHelper;

public class register extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private EditText et_password_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);


//初始化控件
        et_username = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        et_password_confirm = findViewById(R.id.et_password2);

//        点击返回按钮
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                返回登录界面
                finish();
            }
        });

//        点击注册
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                获取输入框的值
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String password_confirm = et_password_confirm.getText().toString();
//                判断输入框是否为空
                if (username.isEmpty() && password.isEmpty() && password_confirm.isEmpty()) {
                    Toast.makeText(register.this, "Please enter your username or password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!password.equals(password_confirm)) {
                    Toast.makeText(register.this, "Two passwords don't match", Toast.LENGTH_SHORT).show();
                    et_password.setText("");
                    et_password_confirm.setText("");
                    return;
                } else {

                    int result = UserDbHelper.getInstance(register.this).register(username, password, "Currently unavailable---");
                    if (result > 0) {
                        Toast.makeText(register.this, "Successful registration", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}