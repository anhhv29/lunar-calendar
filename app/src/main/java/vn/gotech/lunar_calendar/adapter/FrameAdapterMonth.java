package vn.gotech.lunar_calendar.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vn.gotech.lunar_calendar.frame.MyFrameMonth;

public class FrameAdapterMonth extends FragmentStatePagerAdapter {

    int mCount;


    //  Date date = new Date();
    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public FrameAdapterMonth(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub

    }

    @Override
    public int getItemPosition(Object object) {


        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub


        return new MyFrameMonth(arg0);


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mCount;
    }
}
