package vn.gotech.lunar_calendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Calendar;

import vn.gotech.lunar_calendar.adapter.FrameAdapter;
import vn.gotech.lunar_calendar.adapter.FrameAdapterMonth;
import vn.gotech.lunar_calendar.database.DanhNgon;
import vn.gotech.lunar_calendar.database.DataSourceDanhNgon;

public class MainActivity extends AppCompatActivity {

    // private TextView mTextMessage;
    ViewPager viewpagerNgay;
    FrameAdapter adapterNgay;

    ViewPager viewpagerThang;
    FrameAdapterMonth adapterThang;
    private DataSourceDanhNgon datasource;

    Boolean doubleBackToExitPressedOnce = false;

    //AmDuong amDuong;
    private ArrayList<DanhNgon> lstVN;

    DatePicker dpDuong;
    DatePicker dpAm;
    LunarYearTools amDuong = new LunarYearTools();

    Boolean ok = true;
    public String ThongBaoSV = "";

    /**
     * Called when leaving the activity
     */

    public void OnClick_HomNayThang(View view) {

        try {
            viewpagerThang.setCurrentItem(36);
        } catch (Exception ex) {

        }
    }

    public void OnClick_HomNay(View view) {
        try {
            viewpagerNgay.setCurrentItem(183);
        } catch (Exception ex) {

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            viewpagerThang.setCurrentItem(36);
            viewpagerNgay.setCurrentItem(183);
        } catch (Exception ex) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            threadLoadData();

            if (getIntent().getExtras() != null) {
                //do your stuff

                Intent intent = getIntent();
                String msg = intent.getStringExtra("content");
                if (msg != null) {
                    ThongBaoSV = "" + msg;
                }
            }


        } catch (Exception ex) {

        }
    }


    /*
Lay du lieu do vao arraylist
*/
    private void threadLoadData() {


        new Thread(new Runnable() {
            public void run() {
                try {


                    getDefaultDataOffline();


                } catch (Exception e) {

                }

            }
        }).start();
    }

    /*
Load du lieu vao arraylist
*/
    public void getDefaultDataOffline() {
        try {
            ArrayList<Object> values = datasource.getAllNews();

            for (Object item : values) {
                DanhNgon n1 = new DanhNgon();

                n1.setId(((DanhNgon) item).getId().trim());
                n1.setContent(((DanhNgon) item).getContent().trim());
                n1.setAuthor(((DanhNgon) item).getAuthor().trim());


                lstVN.add(n1);

            }

            datasource.close();
            viewpagerNgay.getAdapter().notifyDataSetChanged();
        } catch (Exception ex) {

        }
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        try {

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.doubleclick),
                    Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } catch (Exception ex) {

        }
    }
}
