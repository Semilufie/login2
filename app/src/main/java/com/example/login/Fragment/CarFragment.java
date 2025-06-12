package com.example.login.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.adapter.CarListAdapter;
import com.example.login.db.CarDbHelper;
import com.example.login.db.OrderDbHelper;
import com.example.login.entity.CarInfo;
import com.example.login.entity.UserInfo;

import androidx.annotation.Nullable;

import java.util.List;


public class CarFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private CarListAdapter mCarListAdapter;
    private TextView tv_total;
    private Button btn_account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_car, container, false);

        // 初始化控件
        recyclerView = rootView.findViewById(R.id.recyclerView_car);
        tv_total = rootView.findViewById(R.id.tv_total);
        btn_account = rootView.findViewById(R.id.btn_account);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 初始化mCarListAdapter
        mCarListAdapter = new CarListAdapter();
        // 设置适配器
        recyclerView.setAdapter(mCarListAdapter);

        // 点击事件
        mCarListAdapter.setMonItemClickListener(new CarListAdapter.onItemClickListener() {

            @Override
            public void onPlusOnClick(CarInfo carInfo, int position) {
                // 增加
                CarDbHelper.getInstance(getActivity()).updateProduct(carInfo.getCar_id(), carInfo);
                loadData();

            }

            @Override
            public void onSubTractOnClick(CarInfo carInfo, int position) {
                // 减少
                CarDbHelper.getInstance(getActivity()).SubStartUpdateProduct(carInfo.getCar_id(), carInfo);
                loadData();

            }

            @Override
            public void delOnClick(CarInfo carInfo, int position) {
                // 删除

                new AlertDialog.Builder(getActivity())
                        .setTitle("Spanish jasmine")
                        .setMessage("Whether to delete the product")
                        .setPositiveButton("Make certain", (dialog, which) -> {
                            // 删除
                            CarDbHelper.getInstance(getActivity()).delete(carInfo.getCar_id() + "");
                            loadData();
                            Toast.makeText(getActivity(), "Deleted successfully", Toast.LENGTH_LONG).show();
                        })
                        .setNegativeButton("Cancellations", null)
                        .show();
            }
        });

        //  结算购物车
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //批量将购物车里的数据生成订单
                UserInfo userinfo = UserInfo.getsUserInfo();
                if (null != userinfo) {
                    List<CarInfo> carlist = CarDbHelper.getInstance(getActivity()).queryCarList(userinfo.getUsername());
                    if (carlist.isEmpty()) {
                        Toast.makeText(getActivity(), "You have not selected a product", Toast.LENGTH_SHORT).show();
                    } else {

                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pay_dialog_layout, null);

                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText et_mobile = view.findViewById(R.id.dio_mobile);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText et_address = view.findViewById(R.id.dio_address);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView total = view.findViewById(R.id.dio_total);

                        //设置总价格
                        total.setText(tv_total.getText().toString());

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Payment Warnings")
                                .setView(view)
                                .setNegativeButton("Cancellations", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setPositiveButton("Recognise", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String address = et_address.getText().toString();
                                        String mobile = et_mobile.getText().toString();
                                        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(mobile)) {
                                            Toast.makeText(getActivity(), "Please fill in the complete information", Toast.LENGTH_SHORT).show();
                                        } else {
                                            OrderDbHelper.getInstance(getActivity()).insertByAll(carlist, address, mobile);
                                            //清空购物车
                                            for (int i = 0; i < carlist.size(); i++) {
                                                CarDbHelper.getInstance(getActivity()).delete(carlist.get(i).getCar_id() + "");
                                            }

                                            //重新加载数据
                                            loadData();

                                            Toast.makeText(getActivity(), "Payment successful", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                })

                                .show();


//
                    }
                }
            }
        });

        // 初始化页面，加载数据
        loadData();
    }

    // 加载合计数据
    @SuppressLint("SetTextI18n")
    private void setTotalData(List<CarInfo> list) {
        // 获取数据
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += list.get(i).getProduct_price() * list.get(i).getProduct_count();
        }
        // 设置数据
        tv_total.setText(total + ".00");
    }

    public void loadData() {
        if (UserInfo.getsUserInfo() != null) {
            // 获取数据
            List<CarInfo> carList = CarDbHelper.getInstance(getActivity()).queryCarList(UserInfo.getsUserInfo().getUsername());
            // 设置数据
            mCarListAdapter.setmCarInfoList(carList);
            // 计算总的价格
            setTotalData(carList);
        }
    }

}