package anhhv.dev.lunar_calendar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import anhhv.dev.lunar_calendar.R;
import anhhv.dev.lunar_calendar.mode.MonthCalender;

public class MonthAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<MonthCalender> lstMonth;

    public MonthAdapter(Context context, ArrayList<MonthCalender> websiteValues) {
        this.context = context;
        this.lstMonth = websiteValues;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = inflater.inflate(R.layout.item_month, null);
            TextView textDuong = gridView.findViewById(R.id.tvDuong);
            if (position == 0 || position == 7 || position == 14 || position == 21 || position == 28 || position == 35 || position == 42) {
                textDuong.setTextColor(Color.parseColor("#FFFF5722"));
            }

            String duongTMG = lstMonth.get(position).get_MonthDuong();

            ImageView ivCard = gridView.findViewById(R.id.ivCard);
            if (duongTMG.contains("n")) {
                duongTMG = duongTMG.substring(1);
                ivCard.setBackgroundResource(R.color.red);
            }
            textDuong.setText(duongTMG);
            TextView textAm = gridView.findViewById(R.id.tvAm);
            textAm.setText(lstMonth.get(position).get_monthAm());
        } else {
            gridView = convertView;
        }
        return gridView;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lstMonth.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
}
