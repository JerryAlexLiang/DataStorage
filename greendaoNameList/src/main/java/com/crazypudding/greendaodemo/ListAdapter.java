package com.crazypudding.greendaodemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 * Created by Crazypudding on 2017/1/3.
 */

public class ListAdapter extends ArrayAdapter<Company> {

    private List<Company> mData;
    private int resourceId;

    //Set up ViewHolder
    private static class ViewHolder {

        TextView idTextView;
        TextView nameTextView;
        TextView addressTextView;
    }

    public ListAdapter(Context context, int resource, List<Company> objects) {
        super(context, resource, objects);
        resourceId = resource;
        mData = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            holder = new ViewHolder();
            holder.idTextView = (TextView) convertView.findViewById(R.id.row_id_tv);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.name_tv);
            holder.addressTextView = (TextView) convertView.findViewById(R.id.address_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Company employee = mData.get(position);

        holder.idTextView.setText(String.format(Locale.US, "%d", employee.getId()));
        holder.nameTextView.setText(employee.getName());
        holder.addressTextView.setText(String.valueOf(employee.getSalary()));

        return convertView;
    }
}
