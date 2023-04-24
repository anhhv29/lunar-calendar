package vn.gotech.lunar_calendar.frame;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import vn.gotech.lunar_calendar.HomeActivity;
import vn.gotech.lunar_calendar.LunarYearTools;
import vn.gotech.lunar_calendar.R;
import vn.gotech.lunar_calendar.database.DanhNgon;

public class MyFrameDay extends Fragment {

    Calendar c;
    Integer ngayduong;
    Integer thangduong;
    Integer namduong;
    Integer ViTri;
    String content = "MAKE YOUR CAR SAFER & SMARTER";
    String author = "GOTECH";
    //AmDuong amDuong=new AmDuong();
    LunarYearTools amDuong = new LunarYearTools();
    ArrayList<DanhNgon> lstVN;

    TextView txtGio;
    TextView txtNgayDuong;
    TextView txtThangDuong;
    TextView tvNgayAm;
    TextView tvThangAm;
    ImageButton ibToDay;
    TextView tvTitleVannien;
    TextView tvCadao;
    TextView tvAuthor;
    TextView tvThu;
    RelativeLayout rlTitleTop;
    RelativeLayout rlBg;
    Random rd = new Random();
    String ThongBaoSV = "";
    TextView tvTitleGio;
    TextView tvTitleNgay;
    TextView tvTitleThanh;

    public MyFrameDay() {
        super();
    }

    @SuppressLint("ValidFragment")
    public MyFrameDay(Integer ViTri, ArrayList<DanhNgon> lstVN) {
        super();

        try {
            this.ViTri = ViTri;
            this.lstVN = lstVN;
        } catch (Exception ex) {

        }
    }

    @Override
    public void onResume() {
        try {
            c = Calendar.getInstance();
            // Log.e("Gio:",":"+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE));
            Integer dGio = c.get(Calendar.HOUR_OF_DAY);
            String dPhut = "" + c.get(Calendar.MINUTE);
            String dQuyTime = "";

            if (dGio <= 10) {
                dQuyTime = "Giờ Sáng";
            } else if (dGio <= 13) {
                dQuyTime = "Giờ Chưa";
            } else if (dGio <= 17) {
                dQuyTime = "Giờ Chiều";
            } else {
                dQuyTime = "Giờ Tối";
            }

            if (dGio > 12) {
                dGio = dGio - 12;
            }

            if (dPhut.length() == 1) {
                dPhut = "0" + dPhut;
            }


            if (dGio < 10) {
                txtGio.setText("0" + dGio + ":" + dPhut);
            } else {
                txtGio.setText("" + dGio + ":" + dPhut);
            }

            if (ViTri == 183) {
                ibToDay.setVisibility(View.GONE);
                tvTitleVannien.setText("Lịch Vạn Niên");
            } else {
                ibToDay.setVisibility(View.VISIBLE);

                int tam = ViTri - 183;
                c.add(Calendar.DATE, tam);
                tvTitleVannien.setText("GOTECH");
            }


            int thumay = c.get(Calendar.DAY_OF_WEEK);
            if (thumay == 1) {
                tvThu.setText("Chủ Nhật");
                tvThu.setTextColor(Color.parseColor("#FF6A6A"));
                txtNgayDuong.setTextColor(Color.parseColor("#FF6A6A"));
                txtThangDuong.setTextColor(Color.parseColor("#FF6A6A"));
                rlTitleTop.setBackgroundColor(Color.parseColor("#FF6A6A"));
            } else {
                tvThu.setText("Thứ " + thumay);
                if (thumay == 2) {
                    tvThu.setTextColor(Color.parseColor("#00AA00"));
                    txtNgayDuong.setTextColor(Color.parseColor("#00AA00"));
                    txtThangDuong.setTextColor(Color.parseColor("#00AA00"));
                    rlTitleTop.setBackgroundColor(Color.parseColor("#00AA00"));
                } else if (thumay == 3 || thumay == 7) {
                    tvThu.setTextColor(Color.parseColor("#0099FF"));
                    txtNgayDuong.setTextColor(Color.parseColor("#0099FF"));
                    txtThangDuong.setTextColor(Color.parseColor("#0099FF"));
                    rlTitleTop.setBackgroundColor(Color.parseColor("#0099FF"));
                } else if (thumay == 4) {
                    tvThu.setTextColor(Color.parseColor("#009966"));
                    txtNgayDuong.setTextColor(Color.parseColor("#009966"));
                    txtThangDuong.setTextColor(Color.parseColor("#009966"));
                    rlTitleTop.setBackgroundColor(Color.parseColor("#009966"));
                } else if (thumay == 5) {
                    tvThu.setTextColor(Color.parseColor("#CC0099"));
                    txtNgayDuong.setTextColor(Color.parseColor("#CC0099"));
                    txtThangDuong.setTextColor(Color.parseColor("#CC0099"));
                    rlTitleTop.setBackgroundColor(Color.parseColor("#CC0099"));
                } else if (thumay == 6) {
                    tvThu.setTextColor(Color.parseColor("#CD853F"));
                    txtNgayDuong.setTextColor(Color.parseColor("#CD853F"));
                    txtThangDuong.setTextColor(Color.parseColor("#CD853F"));
                    rlTitleTop.setBackgroundColor(Color.parseColor("#CD853F"));
                }

            }

            ngayduong = c.get(Calendar.DAY_OF_MONTH);
            thangduong = c.get(Calendar.MONTH) + 1;
            namduong = c.get(Calendar.YEAR);
            txtNgayDuong.setText("" + ngayduong);
            txtThangDuong.setText("Tháng " + thangduong + " Năm " + namduong);

            int am[] = amDuong.convertSolar2Lunar(ngayduong, thangduong, namduong, 7);
            tvNgayAm.setText("" + am[0]);
            tvThangAm.setText(am[1] + "/" + am[2]);

            if (am[0] == 1 && am[1] == 1) {
                content = "Chúc mừng năm mới. Ngày mùng 1 tết cố truyền dân tộc";
                author = "Xuân đã về";
            } else if (am[0] == 2 && am[1] == 1) {
                content = "Mùng 1 tết cha mùng 2 tết mẹ mùng 3 tết thầy";
                author = "Mùng 2 tết";
            } else if (am[0] == 3 && am[1] == 1) {
                content = "Mùng 3 tết thầy";
                author = "Mùng 3 tết";
            } else if (am[0] == 15 && am[1] == 1) {
                content = "Ngày dằm tháng giêng";
                author = "Tết nguyên tiêu";
            } else if (am[0] == 3 && am[1] == 3) {
                content = "Tết bánh trôi bánh tray";
                author = "Tết hàn thực";
            } else if (am[0] == 10 && am[1] == 3) {
                content = "Ngày dỗ tổ hùng vương. Vua hùng đã có công dựng nước";
                author = "Vua Hùng";
            } else if (am[0] == 15 && am[1] == 4) {
                content = "Ngày lễ phật đản";
                author = "A di đà phật";
            } else if (am[0] == 5 && am[1] == 5) {
                content = "Ngày tết đoan ngọ";
                author = "Tết đoan ngọ";
            } else if (am[0] == 15 && am[1] == 7) {
                content = "Công cha như núi thái sơn. Nghĩa mẹ như nước trong nguồn chảy ra";
                author = "Ngày lễ vu lan";
            } else if (am[0] == 15 && am[1] == 8) {
                content = "Ngày tết trung thu";
                author = "Chị hằng";
            } else if (am[0] == 9 && am[1] == 9) {
                content = "Ngày tết cửu trùng";
                author = "Diệt sâu bọ";
            } else if (am[0] == 10 && am[1] == 10) {
                content = "Ngày tết thường tân";
                author = "tết";
            } else if (am[0] == 15 && am[1] == 10) {
                content = "Ngày tết hạ nguyên";
                author = "Nguyên";
            } else if (am[0] == 23 && am[1] == 12) {
                content = "Tiễn Ông Công Ông Táo Về Trời";
                author = "Táo quân";
            } else if (ngayduong == 1 && thangduong == 1) {
                content = "Ngày tết dương lịch";
                author = "Happy new year";
            } else if (ngayduong == 14 && thangduong == 2) {
                content = "Ngày lễ tình nhân";
                author = "Valentine";
            } else if (ngayduong == 27 && thangduong == 2) {
                content = "Ngày thầy thuốc Việt Nam";
                author = "Lương y như từ mẫu";
            } else if (ngayduong == 8 && thangduong == 3) {
                content = "Ngày quốc tế phụ nữ";
                author = "Yêu thương";
            } else if (ngayduong == 26 && thangduong == 3) {
                content = "Ngày thành lập Đoàn TNCS Hồ Chí Minh";
                author = "Hồ Chí Minh";
            } else if (ngayduong == 1 && thangduong == 4) {
                content = "Ngày cá tháng 4";
                author = "Nói dối";
            } else if (ngayduong == 30 && thangduong == 4) {
                content = "GIẢI PHÓNG MIỀN NAM";
                author = "Giải Phóng";
            } else if (ngayduong == 1 && thangduong == 5) {
                content = "Ngày quốc tế lao động";
                author = "Lao động";
            } else if (ngayduong == 7 && thangduong == 5) {
                content = "Ngày chiến thắng điện biên phủ";
                author = "Đừng ngủ quên trên chiến thắng";
            } else if (ngayduong == 13 && thangduong == 5) {
                content = "Ngày của mẹ !";
                author = "Mẹ yêu";
            } else if (ngayduong == 19 && thangduong == 5) {
                content = "Ngày sinh chủ tịch Hồ Chí Minh";
                author = "Sinh nhật bác";
            } else if (ngayduong == 1 && thangduong == 6) {
                content = "Ngày quốc tế thiếu nhi";
                author = "Trẻ em hôm nay";
            } else if (ngayduong == 17 && thangduong == 6) {
                content = "Ngày của cha";
                author = "Nợ cha 1 sự nghiệp";
            } else if (ngayduong == 21 && thangduong == 6) {
                content = "Ngày báo chí Việt Nam";
                author = "Balo";
            } else if (ngayduong == 28 && thangduong == 6) {
                content = "Ngày gia đình Việt Nam";
                author = "Gia Đình";
            } else if (ngayduong == 11 && thangduong == 7) {
                content = "Ngày dân số thế giới";
                author = "Kế Hoạch Hóa Gia Đình";
            } else if (ngayduong == 27 && thangduong == 7) {
                content = "Ngày thương binh liệt sĩ";
                author = "Tổ quốc ghi công";
            } else if (ngayduong == 28 && thangduong == 7) {
                content = "Ngày công đoàn Việt nam";
                author = "Đoàn kết";
            } else if (ngayduong == 19 && thangduong == 8) {
                content = "Ngày tổng khởi nghĩa";
                author = "Cách mạng tháng 8";
            } else if (ngayduong == 2 && thangduong == 9) {
                content = "Ngày quốc khánh";
                author = "Cờ đỏ";
            } else if (ngayduong == 10 && thangduong == 9) {
                content = "Ngày thành lập mặt trận tổ quốc Việt nam";
                author = "Việt Nam";
            } else if (ngayduong == 1 && thangduong == 10) {
                content = "Ngày quốc tế người cao tuổi";
                author = "Kĩnh lão đắc thọ";
            } else if (ngayduong == 10 && thangduong == 10) {
                content = "Ngày giải phóng Thủ Đô";
                author = "Hà Nội";
            } else if (ngayduong == 13 && thangduong == 10) {
                content = "Ngày doanh nhân Việt Nam";
                author = "Startup";
            } else if (ngayduong == 20 && thangduong == 10) {
                content = "Ngày phụ nữ Việt Nam";
                author = "Hoa Hồng Có Gai";
            } else if (ngayduong == 31 && thangduong == 10) {
                content = "Ngày Hallowen";
                author = "Bí Ngô";
            } else if (ngayduong == 9 && thangduong == 11) {
                content = "Ngày pháp luật Việt Nam";
                author = "pháp luật";
            } else if (ngayduong == 20 && thangduong == 11) {
                content = "Ngày Nhà Giáo Việt Nam";
                author = "Bụi phấn";
            } else if (ngayduong == 23 && thangduong == 11) {
                content = "Ngày thành lập hội chữ thập đỏ Việt Nam";
                author = "Cộng đồng";
            } else if (ngayduong == 1 && thangduong == 12) {
                content = "Ngày toàn thế giới chống xi-đa";
                author = "HIV AIDS";
            } else if (ngayduong == 19 && thangduong == 12) {
                content = "Ngày toàn quốc kháng chiến";
                author = "Cách mạng tháng 12";
            } else if (ngayduong == 22 && thangduong == 12) {
                content = "Ngày thành lập quân đội nhân dân Việt Nam";
                author = "CF";
            } else if (ngayduong == 24 && thangduong == 12) {
                content = "Ngày lễ giáng sinh";
                author = "Noel";
            } else if (ngayduong == 25 && thangduong == 12) {
                content = "Giáng sinh an lành";
                author = "Noel";
            } else {

                if (lstVN.size() > 0) {
                    Integer chon = am[0] + (am[1] * 31);
                    content = "" + lstVN.get(chon).getContent();
                    author = "" + lstVN.get(chon).getAuthor();
                }

            }

            if (ViTri == 183 && ThongBaoSV != "") {
                tvCadao.setText("" + ThongBaoSV);
                tvAuthor.setText("GOTECH");
            } else {
                tvCadao.setText("" + content);
                tvAuthor.setText("" + author);
            }
        } catch (Exception ex) {

        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_day_layout, container, false);
        try {
            txtGio = (TextView) view.findViewById(R.id.tvGio);
            txtNgayDuong = (TextView) view.findViewById(R.id.tvDay);
            txtThangDuong = (TextView) view.findViewById(R.id.tvMonthYear);
            tvNgayAm = (TextView) view.findViewById(R.id.tvNgayAm);
            tvThangAm = (TextView) view.findViewById(R.id.tvThangAm);
            ibToDay = (ImageButton) view.findViewById(R.id.ibToDay);
            tvTitleVannien = (TextView) view.findViewById(R.id.tvTitleVanNien);
            tvCadao = (TextView) view.findViewById(R.id.tvCadao);
            tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
            tvThu = (TextView) view.findViewById(R.id.tvThuMay);
            rlTitleTop = (RelativeLayout) view.findViewById(R.id.rlTitleTop);
            rlBg = (RelativeLayout) view.findViewById(R.id.rlBg);
            tvTitleGio = (TextView) view.findViewById(R.id.tvTitleGio);
            tvTitleNgay = (TextView) view.findViewById(R.id.tvTitleNgay);
            tvTitleThanh = (TextView) view.findViewById(R.id.tvTitleThanh);
            Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Flamenco.ttf");
            tvTitleVannien.setTypeface(type);
            tvTitleGio.setTypeface(type);
            tvTitleNgay.setTypeface(type);
            tvTitleThanh.setTypeface(type);
            HomeActivity activity = (HomeActivity) getActivity();
            ThongBaoSV = "" + activity.ThongBaoSV;
        } catch (Exception ex) {

        }
        return view;
    }
}
