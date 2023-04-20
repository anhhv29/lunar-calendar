package vn.gotech.lunar_calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import vn.gotech.lunar_calendar.adapter.MonthAdapter;
import vn.gotech.lunar_calendar.database.DanhNgon;
import vn.gotech.lunar_calendar.database.DataSourceDanhNgon;
import vn.gotech.lunar_calendar.mode.MonthCalender;

public class MainActivity extends AppCompatActivity {
    MonthAdapter monthAdapter;
    TextView tvMonth, tvDay, tvWeekdays, tvProverb, tvLunarDay, tvLunarMonth, tvAuthor;
    TextView tvTitle;
    GridView gridView;
    int hour, day, month, year;
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

            Date date = calendar.getTime();
            hour = date.getHours();

            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);

            int[] lunar = lunarYearTools.convertSolar2Lunar(day, month, year, 7);

            int dayLunar = lunar[0];
            int monthLunar = lunar[1];
            int yearLunar = lunar[2];

            //Tính Can, Chi Ngày/Tháng/Năm
            String[] can = {"Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm", "Quý"};
            String[] chi = {"Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi"};

            //Năm Can Chi
            String canYear = can[(yearLunar + 6) % 10];
            String chiYear = chi[(yearLunar + 8) % 12];
            String lunarYear = canYear + " " + chiYear;

            //Tháng Can Chi
            String[] chiM = {"Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu"};
            String canMonth = can[(yearLunar * 12 + monthLunar + 3) % 10];
            String chiMonth = chiM[monthLunar - 1];
            String lunarMonth = canMonth + " " + chiMonth;

            //Ngày Can Chi
            int dayJuliusNumber = jdFromDate(day, month, year);
            String canDay = can[(dayJuliusNumber + 9) % 10];
            String chiDay = chi[(dayJuliusNumber + 1) % 12];
            String lunarDay = canDay + " " + chiDay;

            //Chi Giờ
            String[] chiHour = {"Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi"};
            int gioIndex = (hour + 1) / 2 % 12;
            Log.e("gio", chiHour[gioIndex]);

            //Tính ngày hoàng đạo
            String[] hoangDao = new String[6];
//            String[] hacDao = new String[6];

            switch (monthLunar % 6) {
                case 0:
                    hoangDao = new String[]{"Tuất", "Hợi", "Dần", "Mão", "Tỵ", "Thân"};
//                    hacDao = new String[]{"Tý", "Sửu", "Thìn", "Ngọ", "Mùi", "Dậu"};
                    break;
                case 1:
                    hoangDao = new String[]{"Tý", "Sửu", "Thìn", "Tỵ", "Mùi", "Tuất"};
//                    hacDao = new String[]{"Dần", "Mão", "Ngọ", "Thân", "Dậu", "Hợi"};
                    break;
                case 2:
                    hoangDao = new String[]{"Dần", "Mão", "Ngọ", "Mùi", "Dậu", "Tý"};
//                    hacDao = new String[]{"Thìn", "Tỵ", "Thân", "Tuất", "Hợi", "Sửu"};
                    break;
                case 3:
                    hoangDao = new String[]{"Thìn", "Tỵ", "Thân", "Dậu", "Hợi", "Dần"};
//                    hacDao = new String[]{"Ngọ", "Mùi", "Tuất", "Tý", "Sửu", "Mão"};
                    break;
                case 4:
                    hoangDao = new String[]{"Ngọ", "Mùi", "Tuất", "Hợi", "Sửu", "Thìn"};
//                    hacDao = new String[]{"Thân", "Dậu", "Tý", "Dần", "Mão", "Tỵ"};
                    break;
                case 5:
                    hoangDao = new String[]{"Thân", "Dậu", "Tý", "Sửu", "Mão", "Ngọ"};
//                    hacDao = new String[]{"Tuất", "Hợi", "Dần", "Thìn", "Tỵ", "Mùi"};
                    break;
            }

            boolean checkNgayHoangDao = false;
            for (String s : hoangDao) {
                if (chiDay.equals(s)) {
                    Log.e("123abc", "hoangDao");
                    checkNgayHoangDao = true;
                    break;
                }
            }

            String ngayHoangHacDao = (checkNgayHoangDao) ? "Hoàng Đạo" : "Hắc Đạo";

            //Tính giờ hoàng đạo
            String[] gioHoangDao = new String[6];

            switch (chiDay) {
                case "Tỵ":
                case "Hợi":
                    gioHoangDao = new String[]{"Sửu", "Thìn", "Ngọ", "Tuất", "Hợi"};
                    break;
                case "Tý":
                case "Ngọ":
                    gioHoangDao = new String[]{"Tý", "Sửu", "Mão", "Ngọ", "Thân", "Dậu"};
                    break;
                case "Sửu":
                case "Mùi":
                    gioHoangDao = new String[]{"Dần", "Mão", "Tỵ", "Thân", "Tuất", "Hợi"};
                    break;
                case "Dần":
                case "Thân":
                    gioHoangDao = new String[]{"Tý", "Sửu", "Thìn", "Tỵ", "Mùi", "Tuất"};
                    break;
                case "Mão":
                case "Dậu":
                    gioHoangDao = new String[]{"Tý", "Dần", "Mão", "Ngọ", "Mùi", "Dậu"};
                    break;
                case "Thìn":
                case "Tuất":
                    gioHoangDao = new String[]{"Dần", "Thìn", "Tỵ", "Thân", "Dậu", "Hợi"};
                    break;
            }

            boolean checkGioHoangDao = false;
            for (String s : gioHoangDao) {
                if (chiHour[gioIndex].equals(s)) {
                    Log.e("123abc", "hoangDao");
                    checkGioHoangDao = true;
                    break;
                }
            }

            String gioHoangHacDao = (checkGioHoangDao) ? "Hoàng Đạo" : "Hắc Đạo";

            tvDay.setText("" + day);
            tvMonth.setText("Tháng " + month + " Năm " + year + " - năm AL: " + lunarYear + " - tháng AL: " + lunarMonth + " - ngày AL: " + lunarDay + " - Ngày: " + ngayHoangHacDao + " - Giờ: " + chiHour[gioIndex] + " - " + gioHoangHacDao);
            tvAuthor = findViewById(R.id.tvAuthor);

            tvLunarDay.setText("" + dayLunar);
            tvLunarMonth.setText(monthLunar + "/" + yearLunar);

            //Các ngày lễ trong năm
            HashMap<String, String[]> holidaysLunnar = new HashMap<>(); //ngày lễ âm
            holidaysLunnar.put("1-1", new String[]{"Chúc mừng năm mới. Ngày mùng 1 tết cố truyền dân tộc", "Xuân đã về"});
            holidaysLunnar.put("2-1", new String[]{"Mùng 1 tết cha mùng 2 tết mẹ mùng 3 tết thầy", "Mùng 2 tết"});
            holidaysLunnar.put("3-1", new String[]{"Mùng 3 tết thầy", "Mùng 3 tết"});
            holidaysLunnar.put("15-1", new String[]{"Ngày dằm tháng giêng", "Tết nguyên tiêu"});
            holidaysLunnar.put("3-3", new String[]{"Tết bánh trôi bánh tray", "Tết hàn thực"});
            holidaysLunnar.put("10-3", new String[]{"Ngày dỗ tổ hùng vương. Vua hùng đã có công dựng nước", "Vua Hùng"});
            holidaysLunnar.put("15-4", new String[]{"Ngày lễ phật đản", "A di đà phật"});
            holidaysLunnar.put("5-5", new String[]{"Ngày tết đoan ngọ", "Tết đoan ngọ"});
            holidaysLunnar.put("15-7", new String[]{"Công cha như núi thái sơn. Nghĩa mẹ như nước trong nguồn chảy ra", "Ngày lễ vu lan"});
            holidaysLunnar.put("15-8", new String[]{"Ngày tết trung thu", "Chị hằng"});
            holidaysLunnar.put("9-9", new String[]{"Ngày tết cửu trùng", "Diệt sâu bọ"});
            holidaysLunnar.put("10-10", new String[]{"Ngày tết thường tân", "Tết"});
            holidaysLunnar.put("15-10", new String[]{"Ngày tết hạ nguyên", "Nguyên"});
            holidaysLunnar.put("23-12", new String[]{"Tiễn Ông Công Ông Táo Về Trời", "Táo quân"});

            HashMap<String, String[]> holidays = new HashMap<>(); //ngày lễ dương
            holidays.put("1-1", new String[]{"Ngày tết dương lịch", "Happy new year"});
            holidays.put("14-2", new String[]{"Ngày lễ tình nhân", "Valentine"});
            holidays.put("27-2", new String[]{"Ngày thầy thuốc Việt Nam", "Lương y như từ mẫu"});
            holidays.put("8-3", new String[]{"Ngày quốc tế phụ nữ", "Yêu thương"});
            holidays.put("26-3", new String[]{"Ngày thành lập Đoàn TNCS Hồ Chí Minh", "Hồ Chí Minh"});
            holidays.put("1-4", new String[]{"Ngày cá tháng 4", "Nói dối"});
            holidays.put("30-4", new String[]{"GIẢI PHÓNG MIỀN NAM", "Giải Phóng"});
            holidays.put("1-5", new String[]{"Ngày quốc tế lao động", "Lao động"});
            holidays.put("7-5", new String[]{"Ngày chiến thắng điện biên phủ", "Đừng ngủ quên trên chiến thắng"});
            holidays.put("13-5", new String[]{"Ngày của mẹ !", "Mẹ yêu"});
            holidays.put("19-5", new String[]{"Ngày sinh chủ tịch Hồ Chí Minh", "Sinh nhật Bác"});
            holidays.put("1-6", new String[]{"Ngày quốc tế thiếu nhi", "Trẻ em hôm nay"});
            holidays.put("17-6", new String[]{"Ngày của cha", "Tình cha"});
            holidays.put("21-6", new String[]{"Ngày báo chí Việt Nam", "Báo chí"});
            holidays.put("28-6", new String[]{"Ngày gia đình Việt Nam", "Gia Đình"});
            holidays.put("11-7", new String[]{"Ngày dân số thế giới", "Kế Hoạch Hóa Gia Đình"});
            holidays.put("27-7", new String[]{"Ngày thương binh liệt sĩ", "Tổ quốc ghi công"});
            holidays.put("28-7", new String[]{"Ngày công đoàn Việt Nam", "Đoàn kết"});
            holidays.put("19-8", new String[]{"Ngày tổng khởi nghĩa", "Cách mạng tháng tám"});
            holidays.put("2-9", new String[]{"Ngày quốc khánh", "Cờ đỏ"});
            holidays.put("10-9", new String[]{"Ngày thành lập mặt trận tổ quốc Việt Nam", "Việt Nam"});
            holidays.put("1-10", new String[]{"Ngày quốc tế người cao tuổi", "Kĩnh lão đắc thọ"});
            holidays.put("10-10", new String[]{"Ngày giải phóng Thủ Đô", "Hà Nội"});
            holidays.put("13-10", new String[]{"Ngày doanh nhân Việt Nam", "Doanh nhân Việt Nam"});
            holidays.put("20-10", new String[]{"Ngày phụ nữ Việt Nam", "Hoa hồng có gai"});
            holidays.put("31-10", new String[]{"Ngày Halloween", "Bí Ngô"});
            holidays.put("9-11", new String[]{"Ngày pháp luật Việt Nam", "Pháp luật"});
            holidays.put("20-11", new String[]{"Ngày Nhà Giáo Việt Nam", "Bụi phấn"});
            holidays.put("23-11", new String[]{"Ngày thành lập hội chữ thập đỏ Việt Nam", "Cộng đồng"});
            holidays.put("1-12", new String[]{"Ngày toàn thế giới chống si-đa", "HIV AIDS"});
            holidays.put("19-12", new String[]{"Ngày toàn quốc kháng chiến", "Cách mạng tháng 12"});
            holidays.put("22-12", new String[]{"Ngày thành lập quân đội nhân dân Việt Nam", "Quân đội nhân dân"});
            holidays.put("24-12", new String[]{"Ngày lễ giáng sinh", "Noel"});
            holidays.put("25-12", new String[]{"Giáng sinh an lành", "Noel"});

            String holidayLunarKey = lunar[0] + "-" + lunar[1];
            String holidayKey = day + "-" + month;

            if (holidaysLunnar.containsKey(holidayLunarKey)) {
                String[] holidayLunar = holidaysLunnar.get(holidayLunarKey);
                if (holidayLunar != null) {
                    content = holidayLunar[0];
                    author = holidayLunar[1];
                }
            } else if (holidays.containsKey(holidayKey)) {
                String[] holiday = holidays.get(holidayKey);
                if (holiday != null) {
                    content = holiday[0];
                    author = holiday[1];
                }
            } else {
                if (lstVN.size() > 0) {
                    int choose = lunar[0] + (lunar[1] * 31);
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

            tvTitle.setText((month < 10) ? "0" + month + " - " + year : month + " - " + year);

            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH, 1);
            int dayweeks = c.get(Calendar.DAY_OF_WEEK);

            lst.clear();
            for (int i = 1; i < dayweeks; i++) {
                MonthCalender week = new MonthCalender("", "");
                Log.d("lst", lst.size() + "");
                lst.add(week);
            }

            int countDay = Calendar.getInstance().getActualMaximum(Calendar.DATE);
            for (int i = 1; i <= countDay; i++) {
                String TMGduong = "" + i;
                Log.d("123123123", TMGduong + "");
                if (i == day) {
                    TMGduong = "n" + TMGduong;
                }

                int[] lunar1 = lunarYearTools.convertSolar2Lunar(i, month, year, 7);

                String center = "" + lunar1[0];
                Log.d("center", center);
                if (lunar1[0] == 1 || i == 1) {
                    center += "/" + lunar1[1];
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

    public int jdFromDate(int day, int month, int year) {
        int a = (14 - month) / 12;
        int y = year + 4800 - a;
        int m = month + 12 * a - 3;
        int jd = day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
        if (jd < 2299161) {
            jd = day + (153 * m + 2) / 5 + 365 * y + y / 4 - 32083;
        }
        //jd = jd - 1721425;
        return jd;
    }

    private void checkHoangHacDao(String chiDay, String[] chiDays, String message) {
        for (String s : chiDays) {
            if (chiDay.equals(s)) {
                Log.e("123abc", message);
                break;
            }
        }
    }
}