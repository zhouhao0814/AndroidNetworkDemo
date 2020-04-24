package com.example.testdemo.Utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testdemo.NetAdapter;
import com.example.testdemo.R;
import com.example.testdemo.domain.MomentItem;


import java.util.List;

/**
 * 继承通用Adapter且使用通用Holder的适配器
 */
public class CommonAdapterWithCommonHolder extends CommonAdapter<NetAdapter> {


    public CommonAdapterWithCommonHolder(Context context, List<NetAdapter> list) {
        super(context, list, R.layout.item_moment);
    }

    /**
     * 复写抽象方法
     * @param viewHolder 一个ViewHolder
//     * @param bean Bean对象
     */
//    @Override
//    public void setViewContent(CommonViewHolder viewHolder, MomentItem netstream) {
//        viewHolder.setImageResource(R.id.user_avatar,netstream.getImageId())
//                .setText(R.id.netstream_name,netstream.getName());
//
//
//    }
    
    @Override
    public void setViewContent(CommonViewHolder viewHolder, NetAdapter netAdapter) {
        viewHolder.setImageResource(R.id.user_avatar, netAdapter.getAvatar())
                .setText(R.id.moment_title, netAdapter.getMomentTitle())
                .setText(R.id.user_info, netAdapter.getUserInfo())
                .setText(R.id.user_name, netAdapter.getUserName());
    
    }
}
//    TextView momentTitle = itemView.findViewById(R.id.moment_title);
//    ImageView avatar = itemView.findViewById(R.id.user_avatar);
//    TextView userInfo = itemView.findViewById(R.id.user_info);
//    TextView userName = itemView.findViewById(R.id.user_name);
