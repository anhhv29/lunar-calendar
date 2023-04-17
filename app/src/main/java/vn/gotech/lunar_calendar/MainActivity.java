package vn.gotech.lunar_calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

import vn.gotech.lunar_calendar.adapter.MonthAdapter;
import vn.gotech.lunar_calendar.database.DanhNgon;
import vn.gotech.lunar_calendar.database.DataSourceDanhNgon;
import vn.gotech.lunar_calendar.mode.MonthCalender;

public class MainActivity extends AppCompatActivity {
    MonthAdapter monthAdapter;
    TextView tvMonth, tvDay, tvWeekdays, tvProverb, tvLunarDay, tvLunarMonth, tvAuthor;
    TextView tvTitle;
    GridView gridView;
    int day, month, year;
    Calendar calendar;
    LunarYearTools lunarYearTools = new LunarYearTools();
    ArrayList<DanhNgon> lstVN = new ArrayList<>();
    ArrayList<MonthCalender> lst = new ArrayList<>();
    String content = "MAKE YOUR CAR SAFER & SMARTER";
    String author = "GOTECH";
    DataSourceDanhNgon datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //day
        tvMonth = findViewById(R.id.tvMonth);
        tvDay = findViewById(R.id.tvDay);
        tvWeekdays = findViewById(R.id.tvWeekdays);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvProverb = findViewById(R.id.tvProverb);
        tvLunarDay = findViewById(R.id.tvLunarDay);
        tvLunarMonth = findViewById(R.id.tvLunarMonth);

        //month
        tvTitle = findViewById(R.id.tvTitle);
        gridView = findViewById(R.id.gvMonth);
        datasource = new DataSourceDanhNgon(this);
        datasource.open();
        threadLoadData();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {

        try {
            calendar = Calendar.getInstance();

            //xu ly ngay
            int weekdays = calendar.get(Calendar.DAY_OF_WEEK);
            if (weekdays == 1) {
                tvWeekdays.setText("Chủ Nhật");
            } else {
                tvWeekdays.setText("Thứ " + weekdays);
            }

            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);

            String[] can = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
            String[] chi = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi"};
            String canChi = can[year % 10] + " " + chi[year % 12]; // tính can chi của năm

            tvDay.setText("" + day);
            tvMonth.setText("Tháng " + month + " Năm " + year + " năm AL:" + canChi);
            tvAuthor = findViewById(R.id.tvAuthor);

            int lunnar[] = lunarYearTools.convertSolar2Lunar(day, month, year, 7);
            tvLunarDay.setText("" + lunnar[0]);
            tvLunarMonth.setText(lunnar[1] + "/" + lunnar[2]);

            if (lunnar[0] == 1 && lunnar[1] == 1) {
                content = "Chúc mừng năm mới. Ngày mùng 1 tết cố truyền dân tộc";
                author = "Xuân đã về";
            } else if (lunnar[0] == 2 && lunnar[1] == 1) {
                content = "Mùng 1 tết cha mùng 2 tết mẹ mùng 3 tết thầy";
                author = "Mùng 2 tết";
            } else if (lunnar[0] == 3 && lunnar[1] == 1) {
                content = "Mùng 3 tết thầy";
                author = "Mùng 3 tết";
            } else if (lunnar[0] == 15 && lunnar[1] == 1) {
                content = "Ngày dằm tháng giêng";
                author = "Tết nguyên tiêu";
            } else if (lunnar[0] == 3 && lunnar[1] == 3) {
                content = "Tết bánh trôi bánh tray";
                author = "Tết hàn thực";
            } else if (lunnar[0] == 10 && lunnar[1] == 3) {
                content = "Ngày dỗ tổ hùng vương. Vua hùng đã có công dựng nước";
                author = "Vua Hùng";
            } else if (lunnar[0] == 15 && lunnar[1] == 4) {
                content = "Ngày lễ phật đản";
                author = "A di đà phật";
            } else if (lunnar[0] == 5 && lunnar[1] == 5) {
                content = "Ngày tết đoan ngọ";
                author = "Tết đoan ngọ";
            } else if (lunnar[0] == 15 && lunnar[1] == 7) {
                content = "Công cha như núi thái sơn. Nghĩa mẹ như nước trong nguồn chảy ra";
                author = "Ngày lễ vu lan";
            } else if (lunnar[0] == 15 && lunnar[1] == 8) {
                content = "Ngày tết trung thu";
                author = "Chị hằng";
            } else if (lunnar[0] == 9 && lunnar[1] == 9) {
                content = "Ngày tết cửu trùng";
                author = "Diệt sâu bọ";
            } else if (lunnar[0] == 10 && lunnar[1] == 10) {
                content = "Ngày tết thường tân";
                author = "tết";
            } else if (lunnar[0] == 15 && lunnar[1] == 10) {
                content = "Ngày tết hạ nguyên";
                author = "Nguyên";
            } else if (lunnar[0] == 23 && lunnar[1] == 12) {
                content = "Tiễn Ông Công Ông Táo Về Trời";
                author = "Táo quân";
            } else if (day == 1 && month == 1) {
                content = "Ngày tết dương lịch";
                author = "Happy new year";
            } else if (day == 14 && month == 2) {
                content = "Ngày lễ tình nhân";
                author = "Valentine";
            } else if (day == 27 && month == 2) {
                content = "Ngày thầy thuốc Việt Nam";
                author = "Lương y như từ mẫu";
            } else if (day == 8 && month == 3) {
                content = "Ngày quốc tế phụ nữ";
                author = "Yêu thương";
            } else if (day == 26 && month == 3) {
                content = "Ngày thành lập Đoàn TNCS Hồ Chí Minh";
                author = "Hồ Chí Minh";
            } else if (day == 1 && month == 4) {
                content = "Ngày cá tháng 4";
                author = "Nói dối";
            } else if (day == 30 && month == 4) {
                content = "GIẢI PHÓNG MIỀN NAM";
                author = "Giải Phóng";
            } else if (day == 1 && month == 5) {
                content = "Ngày quốc tế lao động";
                author = "Lao động";
            } else if (day == 7 && month == 5) {
                content = "Ngày chiến thắng điện biên phủ";
                author = "Đừng ngủ quên trên chiến thắng";
            } else if (day == 13 && month == 5) {
                content = "Ngày của mẹ !";
                author = "Mẹ yêu";
            } else if (day == 19 && month == 5) {
                content = "Ngày sinh chủ tịch Hồ Chí Minh";
                author = "Sinh nhật bác";
            } else if (day == 1 && month == 6) {
                content = "Ngày quốc tế thiếu nhi";
                author = "Trẻ em hôm nay";
            } else if (day == 17 && month == 6) {
                content = "Ngày của cha";
                author = "Nợ cha 1 sự nghiệp";
            } else if (day == 21 && month == 6) {
                content = "Ngày báo chí Việt Nam";
                author = "Balo";
            } else if (day == 28 && month == 6) {
                content = "Ngày gia đình Việt Nam";
                author = "Gia Đình";
            } else if (day == 11 && month == 7) {
                content = "Ngày dân số thế giới";
                author = "Kế Hoạch Hóa Gia Đình";
            } else if (day == 27 && month == 7) {
                content = "Ngày thương binh liệt sĩ";
                author = "Tổ quốc ghi công";
            } else if (day == 28 && month == 7) {
                content = "Ngày công đoàn Việt nam";
                author = "Đoàn kết";
            } else if (day == 19 && month == 8) {
                content = "Ngày tổng khởi nghĩa";
                author = "Cách mạng tháng 8";
            } else if (day == 2 && month == 9) {
                content = "Ngày quốc khánh";
                author = "Cờ đỏ";
            } else if (day == 10 && month == 9) {
                content = "Ngày thành lập mặt trận tổ quốc Việt nam";
                author = "Việt Nam";
            } else if (day == 1 && month == 10) {
                content = "Ngày quốc tế người cao tuổi";
                author = "Kĩnh lão đắc thọ";
            } else if (day == 10 && month == 10) {
                content = "Ngày giải phóng Thủ Đô";
                author = "Hà Nội";
            } else if (day == 13 && month == 10) {
                content = "Ngày doanh nhân Việt Nam";
                author = "Startup";
            } else if (day == 20 && month == 10) {
                content = "Ngày phụ nữ Việt Nam";
                author = "Hoa Hồng Có Gai";
            } else if (day == 31 && month == 10) {
                content = "Ngày Hallowen";
                author = "Bí Ngô";
            } else if (day == 9 && month == 11) {
                content = "Ngày pháp luật Việt Nam";
                author = "pháp luật";
            } else if (day == 20 && month == 11) {
                content = "Ngày Nhà Giáo Việt Nam";
                author = "Bụi phấn";
            } else if (day == 23 && month == 11) {
                content = "Ngày thành lập hội chữ thập đỏ Việt Nam";
                author = "Cộng đồng";
            } else if (day == 1 && month == 12) {
                content = "Ngày toàn thế giới chống xi-đa";
                author = "HIV AIDS";
            } else if (day == 19 && month == 12) {
                content = "Ngày toàn quốc kháng chiến";
                author = "Cách mạng tháng 12";
            } else if (day == 22 && month == 12) {
                content = "Ngày thành lập quân đội nhân dân Việt Nam";
                author = "CF";
            } else if (day == 24 && month == 12) {
                content = "Ngày lễ giáng sinh";
                author = "Noel";
            } else if (day == 25 && month == 12) {
                content = "Giáng sinh an lành";
                author = "Noel";
            } else {
                if (lstVN.size() > 0) {
                    Integer choose = lunnar[0] + (lunnar[1] * 31);
                    content = "" + lstVN.get(choose).getContent();
                    author = "" + lstVN.get(choose).getAuthor();
                }
            }
            tvProverb.setText("" + content);
            tvAuthor.setText("" + author);

            //xu ly thang

            if (month == 0) {
                month = 12;
            }

            if (month < 10) {
                tvTitle.setText("0" + month + " - " + year);
            } else {
                tvTitle.setText("" + month + " - " + year);
            }

            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH, 1);
            int dayweeks = c.get(Calendar.DAY_OF_WEEK);

            for (int i = 1; i < dayweeks; i++) {
                MonthCalender week = new MonthCalender("", "");
                lst.add(week);
            }

            int countDay = Calendar.getInstance().getActualMaximum(Calendar.DATE);
            for (int i = 1; i <= countDay; i++) {
                String TMGduong = "" + i;
                Log.d("123123123", TMGduong + "");
                if (i == day) {
                    TMGduong = "n" + TMGduong;
                }
                int lunar[] = lunarYearTools.convertSolar2Lunar(i, month, year, 7);

                String center = "" + lunar[0];
                Log.d("center", center);
                if (lunar[0] == 1 || i == 1) {
                    center += "/" + lunar[1];
                }

                MonthCalender week = new MonthCalender("" + TMGduong, "" + center);
                lst.add(week);

                Log.d("lst", lst.size() + "-" + Calendar.getInstance().getActualMaximum(Calendar.DATE));
            }
            monthAdapter = new MonthAdapter(this, lst);
            gridView.setAdapter(monthAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        super.onResume();
    }

    private void threadLoadData() {
        new Thread(() -> {
            try {
                getDefaultDataOffline();
            } catch (Exception e) {

            }

        }).start();
    }

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
        } catch (Exception ex) {
        }
    }
}