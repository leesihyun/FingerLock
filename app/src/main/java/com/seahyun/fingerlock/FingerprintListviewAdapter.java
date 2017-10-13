package com.seahyun.fingerlock;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DAHEE on 2017-09-30.
 */

public class FingerprintListviewAdapter extends BaseAdapter {
    Context context;
    ArrayList<FingerprintListviewItem> list_itemArrayList;
    ViewHolder viewholder;
    PackageManager pm;

    public FingerprintListviewAdapter(Context context, ArrayList<FingerprintListviewItem> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fingerprint_item, null);

            viewholder = new ViewHolder();
            viewholder.textView = (TextView) convertView.findViewById(R.id.textView);
            viewholder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewholder);
        } else{
            viewholder = (ViewHolder)convertView.getTag();
        }
        viewholder.textView.setText(list_itemArrayList.get(position).getFingerprint_name());

        PackageManager pm = context.getPackageManager();
        Drawable app_icon = null;
        try {
            app_icon = pm.getApplicationIcon(list_itemArrayList.get(position).getApp_name());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        viewholder.imageView.setImageDrawable(app_icon);
        return convertView;
    }

    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
