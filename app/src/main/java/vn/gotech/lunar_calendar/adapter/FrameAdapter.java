package vn.gotech.lunar_calendar.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import vn.gotech.lunar_calendar.database.DanhNgon;
import vn.gotech.lunar_calendar.frame.MyFrame;

public class FrameAdapter extends FragmentStatePagerAdapter {

    int mCount;
    ArrayList<DanhNgon> lstVN;


    //  Date date = new Date();
    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public FrameAdapter(FragmentManager fm, ArrayList<DanhNgon> lstVN) {
        super(fm);
        // TODO Auto-generated constructor stub
        this.lstVN = lstVN;

    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub


        return new MyFrame(arg0, lstVN);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mCount;
    }
}
