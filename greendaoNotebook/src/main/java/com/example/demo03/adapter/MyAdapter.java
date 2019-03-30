package com.example.demo03.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demo03.R;
import com.example.demo03.bean.Diary;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/1/23 on 下午9:04
 * 描述:
 * 作者:yangliang
 */
public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<Diary> dataList;
    private LayoutInflater mLayoutInflater;

    public MyAdapter(Context context, List<Diary> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_view_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //数据映射
        Diary diary = dataList.get(position);
        holder.tvTitle.setText(diary.getTitle());
        holder.tvContent.setText(diary.getContent());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
