package com.example.login.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.adapter.OrderListAdapter;
import com.example.login.db.OrderDbHelper;
import com.example.login.entity.OrderInfo;
import com.example.login.entity.UserInfo;

import java.util.List;


public class OrderFragment extends Fragment {

    private View rootview;

    private RecyclerView recyclerView;
    private OrderListAdapter mOrderListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_order, container, false);

        //初始化控件
        recyclerView = rootview.findViewById(R.id.recycler_order);


        return rootview;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 初始化mOrderListAdapter
        mOrderListAdapter = new OrderListAdapter();
        // 初始化OrderListAdapter
        recyclerView.setAdapter(mOrderListAdapter);
        // 长按点击事件
        mOrderListAdapter.setmOnItemClickListener(new OrderListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(OrderInfo orderInfo, int position) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("温馨提示")
                        .setMessage("确定要删除订单吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int row = OrderDbHelper.getInstance(getActivity()).delete(orderInfo.getOrder_id() + "");
                                if (row > 0) {
                                    loadData();
                                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .show();
            }
        });
        loadData();
    }

    // 加载数据
    public void loadData() {
        // 获取数据
        UserInfo userInfo = UserInfo.getsUserInfo();
        if (userInfo != null) {
            List<OrderInfo> orderInfos = OrderDbHelper.getInstance(getContext()).queryOrderListData(userInfo.getUsername());
            mOrderListAdapter.setListData(orderInfos);
        }
    }
}