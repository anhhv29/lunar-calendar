package vn.gotech.lunar_calendar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;

import vn.gotech.lunar_calendar.adapter.FrameAdapterDay;
import vn.gotech.lunar_calendar.adapter.FrameAdapterMonth;
import vn.gotech.lunar_calendar.database.DanhNgon;
import vn.gotech.lunar_calendar.database.DataSourceDanhNgon;

public class HomeActivity extends FragmentActivity {

    // private TextView mTextMessage;
    ViewPager viewpagerNgay;
    FrameAdapterDay adapterNgay;
    ViewPager viewpagerThang;
    FrameAdapterMonth adapterThang;
    ViewFlipper vfHome;
    private DataSourceDanhNgon datasource;

    //AmDuong amDuong;
    private ArrayList<DanhNgon> lstVN;
    DatePicker dpDuong;
    DatePicker dpAm;
    LunarYearTools amDuong = new LunarYearTools();
    Boolean ok = true;
    public String ThongBaoSV = "";
    Boolean doubleBackToExitPressedOnce = false;
    MediaPlayer player;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // mTextMessage.setText(R.string.title_home);
                    try {
                        vfHome.setDisplayedChild(0);
                        viewpagerNgay.setCurrentItem(183);
                    } catch (Exception ex) {

                    }
                    return true;
                case R.id.navigation_dashboard:
                    // mTextMessage.setText(R.string.title_dashboard);
                    try {
                        vfHome.setDisplayedChild(1);
                        viewpagerThang.setCurrentItem(36);
                    } catch (Exception ex) {

                    }
                    return true;
                case R.id.navigation_notifications:
                    // mTextMessage.setText(R.string.title_notifications);
                    try {
                        vfHome.setDisplayedChild(2);
                    } catch (Exception ex) {

                    }
                    return true;
            }
            return false;
        }
    };

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {

        player.stop();
        player.reset();

        super.onPause();
    }

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
        setContentView(R.layout.activity_home);
        try {
            datasource = new DataSourceDanhNgon(this);
            datasource.open();


            lstVN = new ArrayList<DanhNgon>();

            vfHome = (ViewFlipper) findViewById(R.id.viewFliper);
            player = new MediaPlayer();

            viewpagerNgay = (ViewPager) findViewById(R.id.viewpagerNgay);
            adapterNgay = new FrameAdapterDay(getSupportFragmentManager(), lstVN);
            adapterNgay.setmCount(360);
            viewpagerNgay.setAdapter(adapterNgay);
            viewpagerNgay.setCurrentItem(183);

            viewpagerThang = (ViewPager) findViewById(R.id.viewpagerThang);
            adapterThang = new FrameAdapterMonth(getSupportFragmentManager());
            adapterThang.setmCount(72);
            viewpagerThang.setAdapter(adapterThang);
            viewpagerThang.setCurrentItem(36);

            dpDuong = (DatePicker) findViewById(R.id.dpDuong);
            dpAm = (DatePicker) findViewById(R.id.dpCDAm);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            int am[] = amDuong.convertSolar2Lunar(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), 7);
            dpAm.init(
                    am[2],
                    am[1] - 1,
                    am[0],
                    new DatePicker.OnDateChangedListener() {

                        @Override
                        public void onDateChanged(DatePicker view,
                                                  int year, int monthOfYear, int dayOfMonth) {

                            if (ok) {
                                ok = false;
                                int tam[] = amDuong.convertLunar2Solar(dayOfMonth, monthOfYear + 1, year, 0, 7);
                                dpDuong.updateDate(tam[2], tam[1] - 1, tam[0]);
                                ok = true;
                            }
                        }
                    });

            dpDuong.init(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    new DatePicker.OnDateChangedListener() {

                        @Override
                        public void onDateChanged(DatePicker view,
                                                  int year, int monthOfYear, int dayOfMonth) {

                            if (ok) {
                                ok = false;
                                int tam[] = amDuong.convertSolar2Lunar(dayOfMonth, monthOfYear + 1, year, 7);
                                dpAm.updateDate(tam[2], tam[1] - 1, tam[0]);
                                ok = true;
                            }
                        }
                    });
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
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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

            Log.e("value", values.size() + "");
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
            Log.e("value", ex.toString());
        }
    }

    @Override
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
