package com.example.login.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.entity.CarInfo;

import java.util.ArrayList;
import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.MyHolder> {


    private List<CarInfo> mCarInfoList = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setmCarInfoList(List<CarInfo> list) {
        this.mCarInfoList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item, null);
        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        //绑定数据
        CarInfo carInfo = mCarInfoList.get(position);
        holder.product_img.setImageResource(carInfo.getProduct_img());
        holder.product_title.setText(carInfo.getProduct_title());
        holder.product_price.setText(carInfo.getProduct_price() + "");
        holder.product_count.setText(carInfo.getProduct_count() + "");

        // 设置点击事件
        holder.product_plus.setOnClickListener(new View.OnClickListener() {
            // 点击增加
            @Override
            public void onClick(View v) {
                if (null != monItemClickListener) {
                    monItemClickListener.onPlusOnClick(carInfo, position);
                }
            }
        });

        holder.product_subtract.setOnClickListener(new View.OnClickListener() {
            // 点击减少
            @Override
            public void onClick(View v) {
                if (null != monItemClickListener) {
                    monItemClickListener.onSubTractOnClick(carInfo, position);
                }
            }
        });

        holder.product_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monItemClickListener != null) {
                    monItemClickListener.delOnClick(carInfo, position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mCarInfoList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        ImageView product_img;
        TextView product_title;
        TextView product_price;
        TextView product_count;
        TextView product_plus;
        TextView product_subtract;
        TextView product_del;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            product_img = itemView.findViewById(R.id.car_image);
            product_title = itemView.findViewById(R.id.car_name);
            product_price = itemView.findViewById(R.id.car_price);
            product_count = itemView.findViewById(R.id.product_count);
            product_plus = itemView.findViewById(R.id.btn_plus);
            product_subtract = itemView.findViewById(R.id.btn_subtract);
            product_del = itemView.findViewById(R.id.delete_long);

        }
    }

    private onItemClickListener monItemClickListener;

    public void setMonItemClickListener(onItemClickListener monItemClickListener) {
        this.monItemClickListener = monItemClickListener;
    }

    public interface onItemClickListener {
        void onPlusOnClick(CarInfo carInfo, int position);

        void onSubTractOnClick(CarInfo carInfo, int position);

        void delOnClick(CarInfo carInfo, int position);

    }

}
