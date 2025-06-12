package com.example.login.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;

import java.util.ArrayList;
import java.util.List;
;

public class leftListAdapter extends RecyclerView.Adapter<leftListAdapter.MyHolder> {

    private List<String> dataList = new ArrayList<>();
    private int currentIndex = 0;

    public leftListAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    private LeftListOnClickItemListener mLeftListOnClickItemListener;


    public void setLeftListOnClickItemListener(LeftListOnClickItemListener mLeftListOnClickItemListener) {
        this.mLeftListOnClickItemListener = mLeftListOnClickItemListener;
    }

    public interface LeftListOnClickItemListener {
        void onItemClick(int position);
    }

    public void setCurrentIndex(int position){
        this.currentIndex = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_list_item, null);
        return new MyHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        // 绑定数据
        String name = dataList.get(position);
        holder.tv_name.setText(name);

        // 分类的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mLeftListOnClickItemListener) {
                    mLeftListOnClickItemListener.onItemClick(position);
                }
            }
        });

        // 设置当前选中的item
        if (position == currentIndex) {
            holder.itemView.setBackgroundResource(R.drawable.type_selector_bg);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.type_selector_normoal_bg);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.name);
        }

    }
}
