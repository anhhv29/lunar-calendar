package vn.gotech.lunar_calendar.frame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

import vn.gotech.lunar_calendar.LunarYearTools;
import vn.gotech.lunar_calendar.R;
import vn.gotech.lunar_calendar.adapter.MonthAdapter;
import vn.gotech.lunar_calendar.mode.MonthCalender;

public class MyFrameMonth extends Fragment {
    MonthAdapter monthAdapter;
    Context context;
    ArrayList<MonthCalender> lst;
    GridView gridView;
    Calendar c;
    Integer ngayhomnay;
    Integer thangcv;
    Integer namcv;
    Integer ViTri;
    TextView txtTitle;
    ImageView ibToDayMonth;
    LunarYearTools amDuong = new LunarYearTools();

    public MyFrameMonth() {
        super();
    }

    @SuppressLint("ValidFragment")
    public MyFrameMonth(Integer ViTri) {
        super();

        try {

            this.ViTri = ViTri;

        } catch (Exception ex) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_month_layout, container, false);

        try {
            context = container.getContext();
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            ibToDayMonth = (ImageView) view.findViewById(R.id.ibToDayMonth);
            gridView = (GridView) view.findViewById(R.id.gvMonth);

        } catch (Exception ex) {

        }
        return view;
    }

    @Override
    public void onResume() {
        try {
            c = Calendar.getInstance();
            int slNgay;
            // thangduong=c.get(Calendar.MONTH);
            ngayhomnay = 0;
            if (ViTri == 36) {
                ngayhomnay = c.get(Calendar.DAY_OF_MONTH);
            }
            if (ViTri == 36) {
                ibToDayMonth.setImageResource(R.drawable.calendar_month);
                c.add(Calendar.MONTH, 1);
            } else {
                ibToDayMonth.setImageResource(R.drawable.homnay);
                int tam = ViTri - 36;
                c.add(Calendar.MONTH, tam + 1);
            }

            thangcv = c.get(Calendar.MONTH);
            namcv = c.get(Calendar.YEAR);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.DAY_OF_MONTH, -1);
            slNgay = c.get(Calendar.DAY_OF_MONTH);

            if (thangcv == 0) {
                thangcv = 12;
            }
            if (thangcv < 10) {
                txtTitle.setText("0" + thangcv + " - " + namcv);
            } else {
                txtTitle.setText("" + thangcv + " - " + namcv);
            }
            lst = new ArrayList<MonthCalender>();
            c.set(Calendar.DAY_OF_MONTH, 1);
            int thumay = c.get(Calendar.DAY_OF_WEEK);
            Log.d("thumay", thumay + "");

            for (int i = 1; i < thumay; i++) {
                MonthCalender thn = new MonthCalender("", "");
                lst.add(thn);
            }
            for (int i = 1; i <= slNgay; i++) {
                String TMGduong = "" + i;
                if (i == ngayhomnay) {
                    TMGduong = "n" + TMGduong;
                }
                int am[] = amDuong.convertSolar2Lunar(i, thangcv, namcv, 7);

                String amtam = "" + am[0];
                if (am[0] == 1 || am[0] == slNgay) {
                    amtam += "/" + am[1];
                }
                MonthCalender thn = new MonthCalender("" + TMGduong, "" + amtam);
                lst.add(thn);
            }
            monthAdapter = new MonthAdapter(context, lst);
            gridView.setAdapter(monthAdapter);
        } catch (Exception ex) {

        }
        super.onResume();
    }
}

