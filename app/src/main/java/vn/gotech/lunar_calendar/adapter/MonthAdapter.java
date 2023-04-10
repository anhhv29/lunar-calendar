package vn.gotech.lunar_calendar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.gotech.lunar_calendar.MonthCalender;
import vn.gotech.lunar_calendar.R;


public class MonthAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<MonthCalender> lstMonth;



    public MonthAdapter(Context context, ArrayList<MonthCalender> websiteValues) {

        this.context = context;
        this.lstMonth = websiteValues;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if(convertView==null)
        {
            gridView=new View(context);
            gridView=inflater.inflate(R.layout.item_month, null);

            TextView textDuong=(TextView) gridView.findViewById(R.id.tvDuong);


            if(position==0||position==7||position==14||position==21||position==28||position==35)
            {
                textDuong.setTextColor(Color.parseColor("#FF4500"));
            }

            String duongTMG=lstMonth.get(position).get_MonthDuong();

            ImageView ivCard=(ImageView)gridView.findViewById(R.id.ivCard);
            if(duongTMG.contains("n"))
            {
                duongTMG=duongTMG.substring(1);

                ivCard.setBackgroundResource(R.drawable.oringer_card);
            }else
            {
                ivCard.setBackgroundResource(R.drawable.light_card);
            }
            textDuong.setText(duongTMG);



            TextView textAm=(TextView) gridView.findViewById(R.id.tvAm);
            textAm.setText(lstMonth.get(position).get_monthAm());


        }
        else
        {
            gridView = (View) convertView;
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
