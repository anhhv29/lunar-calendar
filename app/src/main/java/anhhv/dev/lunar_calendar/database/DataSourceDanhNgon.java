package anhhv.dev.lunar_calendar.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class DataSourceDanhNgon {
    private SQLiteDatabase database;
    private final MySQLiteHelper dbHelper;
    private final String[] allColumns = {
            "id",
            "content",
            "author"};

    public DataSourceDanhNgon(Context context) {
        dbHelper = new MySQLiteHelper(context);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dbHelper.openDataBase();
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Lấy tất cả dữ liệu
    public ArrayList<Object> getAllNews() {
        ArrayList<Object> news = new ArrayList<Object>();

        Cursor cursor = database.query("danhngon", allColumns,
                null, null, null, null, "id");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DanhNgon n = cursorToNews(cursor);
            news.add(n);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return news;
    }

    private DanhNgon cursorToNews(Cursor cursor) {
        DanhNgon news = new DanhNgon();
        news.setId(cursor.getString(0));
        news.setContent(cursor.getString(1));
        news.setAuthor(cursor.getString(2));
        return news;
    }
}
