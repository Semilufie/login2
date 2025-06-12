package com.example.login.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.login.AboutApp;
import com.example.login.Login;
import com.example.login.R;
import com.example.login.UpdatePassword;
import com.example.login.entity.UserInfo;

import org.jetbrains.annotations.Nullable;

public class MeFragment extends Fragment {
    private View rootView;
    private TextView tv_username;
    private TextView tv_nickname;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_me, container, false);

        //初始化控件
        tv_username = rootView.findViewById(R.id.tv_me_nickname);
        tv_nickname = rootView.findViewById(R.id.tv_me_introduce);

        //退出登录
        rootView.findViewById(R.id.tv_me_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("温馨提示")
                        .setMessage("确定要退出登录吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //退出登录的逻辑
                                getActivity().finish();
                                //打开登陆页面
                                Intent intent = new Intent(getActivity(), Login.class);
                                startActivity(intent);

                            }
                        })
                        .show();
            }
        });

        // 修改密码
        rootView.findViewById(R.id.tv_me_change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdatePassword.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv_me_about_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutApp.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //设置用户数据
        UserInfo userinfo = UserInfo.getsUserInfo();
        if (null != userinfo) {
            tv_username.setText(userinfo.getUsername());
            tv_nickname.setText(userinfo.getNickname());
        }
    }
}