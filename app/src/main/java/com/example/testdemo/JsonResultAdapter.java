package com.example.testdemo;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdemo.domain.GetTextItem;

import java.util.ArrayList;
import java.util.List;

/**
 * author:tdz
 * email:tdz@geniatech.com
 * created:2020/4/23 15:44
 */
public class JsonResultAdapter extends RecyclerView.Adapter<JsonResultAdapter.InnerAdapter> {
    private List<GetTextItem.DataBean> mList = new ArrayList();
    @NonNull
    @Override
    public InnerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moment, parent, false);
        return new InnerAdapter(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull InnerAdapter holder, int position) {
        View itemView = holder.itemView;
        TextView momentTitle = itemView.findViewById(R.id.moment_title);
        ImageView avatar = itemView.findViewById(R.id.user_avatar);
        TextView userInfo = itemView.findViewById(R.id.user_info);
        TextView userName = itemView.findViewById(R.id.user_name);
        GetTextItem.DataBean contentBean = mList.get(position);
        String subTitle = contentBean.getTitle();
        if (!TextUtils.isEmpty(subTitle)) {
            momentTitle.setText(subTitle);
        }else {
            momentTitle.setText(contentBean.getCommentCount());
        }
        userName.setText(contentBean.getUserName());
    
    
    }
    
    @Override
    public int getItemCount() {
        return mList.size();
    }
    public class InnerAdapter extends RecyclerView.ViewHolder{
        public InnerAdapter(@NonNull View itemView) {
            super(itemView);
        }
    }
}
