package com.qianyuan.casedetail.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyuan.casedetail.R;
import com.qianyuan.casedetail.bean.HandleDetailBean;
import com.qianyuan.casedetail.utils.LogUtil;

import java.util.List;

/**
 * Created by sun on 2017/4/24.
 */

public class HandleDetailListAdapter extends BaseAdapter {
    Activity activity;
    List<HandleDetailBean.DATABean> list;
    private String username;

    public HandleDetailListAdapter(Activity activity, List<HandleDetailBean.DATABean> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = activity.getLayoutInflater().inflate(R.layout.item_handle_detail, null);
            holder.title1 = (TextView) convertView.findViewById(R.id.tv_time);
            holder.title2 = (TextView) convertView.findViewById(R.id.tv_department);
            holder.title3 = (TextView) convertView.findViewById(R.id.tv_describe);
            holder.view1 = convertView.findViewById(R.id.view_up);
            holder.view2 = convertView.findViewById(R.id.view_down);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.view1.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        holder.view2.setVisibility(View.VISIBLE);
        holder.title1.setText(list.get(position).getDATETIME());
        holder.title2.setText((String) list.get(position).getDEPARTNAME());
        int statetype = list.get(position).getSTATETYPE();
        String memo = (String) list.get(position).getMEMO();
        String state = list.get(position).getSTATE();
        username = list.get(position).getUSERNAME();
        if (statetype == 0) {
            holder.title3.setText(username+"派发" + state);
        }
        if (statetype == 1) {
            holder.title3.setText(username+"已" + state);
        }
        return convertView;
    }

    class ViewHolder {
        TextView title1;
        TextView title2;
        TextView title3;
        View view1;
        View view2;
    }
}
