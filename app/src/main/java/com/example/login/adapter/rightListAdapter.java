package com.example.login.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.entity.ProductInfo;
import com.example.login.R;

import java.util.ArrayList;
import java.util.List;

public class rightListAdapter extends RecyclerView.Adapter<rightListAdapter.MyHolder> {

    private List<ProductInfo> mProductInfos = new ArrayList<>();

    public void setListData(List<ProductInfo> list) {
        this.mProductInfos = list;
        // 一定要刷新
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_list_item, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        // 绑定数据
        ProductInfo productInfo = mProductInfos.get(position);
        holder.productImg.setImageResource(productInfo.getProductImg());
        holder.productName.setText(productInfo.getProductName());
        holder.grade.setText(productInfo.getGrade() + "");
        holder.actor.setText(productInfo.getActor());
        holder.type.setText(productInfo.getType());
        holder.dataTime.setText(productInfo.getDataTime());
        holder.location.setText(productInfo.getLocation());

        // 点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(productInfo, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProductInfos.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        ImageView productImg;
        TextView productName;
        TextView grade;
        TextView actor;
        TextView type;
        TextView dataTime;
        TextView location;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.productImg);
            productName = itemView.findViewById(R.id.productName);
            grade = itemView.findViewById(R.id.grade);
            actor = itemView.findViewById(R.id.actor);
            type = itemView.findViewById(R.id.type);
            dataTime = itemView.findViewById(R.id.dataTime);
            location = itemView.findViewById(R.id.location);

        }
    }

    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener OnItemClickListener) {
        this.mOnItemClickListener = OnItemClickListener;
    }

    public interface onItemClickListener {
        void onItemClick(ProductInfo productInfo, int position);
    }
}
