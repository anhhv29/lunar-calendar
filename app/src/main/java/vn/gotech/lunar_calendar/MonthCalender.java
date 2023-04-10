package vn.gotech.lunar_calendar;

/**
 * Created by Manh on 1/6/2018.
 */

public class MonthCalender {
    String monthDuong="";
    String monthAm="";

    public MonthCalender(String monthDuong, String monthAm)
    {
        this.monthDuong=monthDuong;
        this.monthAm=monthAm;
    }

    public String get_MonthDuong() {
        return monthDuong;
    }

    public String get_monthAm() {
        return monthAm;
    }

}
