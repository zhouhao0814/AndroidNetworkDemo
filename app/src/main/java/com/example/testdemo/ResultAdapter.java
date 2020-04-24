package com.example.testdemo;

import android.content.ClipData;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testdemo.domain.GetTextItem;
import com.example.testdemo.domain.MomentItem;

import java.util.ArrayList;
import java.util.List;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.InnerAdapter> {
    private List<MomentItem.DataBean.ContentBean> mData = new ArrayList<>();
    @NonNull
    @Override
    public ResultAdapter.InnerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moment, parent, false);
        return new InnerAdapter(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.InnerAdapter holder, int position) {
        View itemView = holder.itemView;
        TextView momentTitle = itemView.findViewById(R.id.moment_title);
        ImageView avatar = itemView.findViewById(R.id.user_avatar);
        TextView userInfo = itemView.findViewById(R.id.user_info);
        TextView userName = itemView.findViewById(R.id.user_name);
        MomentItem.DataBean.ContentBean contentBean = mData.get(position);
        String subTitle = contentBean.getSubTitle();
        if (!TextUtils.isEmpty(subTitle)) {
            momentTitle.setText(subTitle);
        }else {
            momentTitle.setText(contentBean.getContent());
        }
        userName.setText(contentBean.getUserName());
        userInfo.setText(contentBean.getPosition() + "@" + contentBean.getCompany());
        Glide.with(itemView.getContext()).load(contentBean.getUserAvatar()).into(avatar);
        
    }
    
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
    public void setData(MomentItem result) {
        mData.clear();
        mData.addAll(result.getData().getContent());
        notifyDataSetChanged();
    }
    public class InnerAdapter extends RecyclerView.ViewHolder {
        public InnerAdapter(@NonNull View itemView) {
            super(itemView);
        }
    }
}
