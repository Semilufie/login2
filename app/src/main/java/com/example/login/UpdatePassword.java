package com.example.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.Fragment.MeFragment;
import com.example.login.db.UserDbHelper;
import com.example.login.entity.UserInfo;

public class UpdatePassword extends AppCompatActivity {
    private EditText updatePassword;
    private EditText updatePasswordAgain;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 初始化控件
        updatePassword = findViewById(R.id.up_password);
        updatePasswordAgain = findViewById(R.id.resume_password);
        Button updatePasswordButton = findViewById(R.id.btn_update);
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = updatePassword.getText().toString();
                String passwordAgain = updatePasswordAgain.getText().toString();
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordAgain)) {
                    Toast.makeText(UpdatePassword.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(passwordAgain)) {
                    Toast.makeText(UpdatePassword.this, "Two passwords don't match", Toast.LENGTH_SHORT).show();
                } else {
                    UserInfo userInfo = UserInfo.getsUserInfo();
                    if (userInfo != null) {
                        UserDbHelper userDbHelper = UserDbHelper.getInstance(UpdatePassword.this);
                        int result = userDbHelper.updatePwd(userInfo.getUsername(), password);
                        if (result > 0) {
                            Toast.makeText(UpdatePassword.this, "Modified successfully, you have been logged out.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdatePassword.this, Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UpdatePassword.this, "Modification Failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        // 返回按钮
        findViewById(R.id.UpdateBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}