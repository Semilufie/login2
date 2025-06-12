package com.example.login.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.login.ProductDetailActivity;
import com.example.login.R;
import com.example.login.adapter.leftListAdapter;
import com.example.login.adapter.rightListAdapter;
import com.example.login.entity.DataService;
import com.example.login.entity.ProductInfo;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    // 根布局
    private View rootView;
    private RecyclerView leftRecyclerView;
    private RecyclerView rightRecyclerView;
    // 适配器
    private leftListAdapter mLeftListAdapter;
    private rightListAdapter mRightListAdapter;
    // 左侧列表，存放左侧列表数据
    private List<String> leftDataList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

//        初始化控件
        leftRecyclerView = rootView.findViewById(R.id.leftRecyclerView);
        rightRecyclerView = rootView.findViewById(R.id.rightRecyclerView);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        leftDataList.add("Currently in theatre");
        leftDataList.add("Coming soon.");
        leftDataList.add("Douban high score");
        leftDataList.add("Documentary film");
        leftDataList.add("Top-rated US drama");
        leftDataList.add("Classic Hong Kong film");

        mLeftListAdapter = new leftListAdapter(leftDataList);
        leftRecyclerView.setAdapter(mLeftListAdapter);

        mRightListAdapter = new rightListAdapter();
        rightRecyclerView.setAdapter(mRightListAdapter);
        // 默认加载第一个列表
        mRightListAdapter.setListData(DataService.getListData(0));

        // recyclerView点击事件
        mRightListAdapter.setOnItemClickListener(new rightListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(ProductInfo productInfo, int position) {
                // 跳转传值
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                // intent传对象的时候，实体类一定要implements Serializable
                intent.putExtra("productInfo", productInfo);
                startActivity(intent);
            }
        });

        // recyclerView点击事件
        mLeftListAdapter.setLeftListOnClickItemListener(new leftListAdapter.LeftListOnClickItemListener() {

            @Override
            public void onItemClick(int position) {
                mLeftListAdapter.setCurrentIndex(position);
                // 点击左侧列表，右侧列表数据更新
                mRightListAdapter.setListData(DataService.getListData(position));
            }
        });
    }
}