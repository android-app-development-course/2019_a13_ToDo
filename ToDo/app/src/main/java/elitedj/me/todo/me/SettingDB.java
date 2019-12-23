package elitedj.me.todo.me;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SettingDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Setting";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_theme = "theme";
    public static final String COLUMN_NAME_lingshengzhendong = "lszd";
    public static final String COLUMN_NAME_music = "music";
    public static final String COLUMN_NAME_rest_time = "resttime";
    public static final String COLUMN_NAME_geyan = "geyan";
    public static final String COLUMN_NAME_xianshi = "xianshi";
    public static final String COLUMN_NAME_changliang = "changliang";
    public static final String COLUMN_NAME_break_time = "break_time";
    public static final String COLUMN_NAME_break_cnt = "break_cnt";
    public static final String COLUMN_NAME_language = "language";

    public SettingDB(Context context) {
        super(context, "Setting", null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_NAME_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_theme + " INT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_lingshengzhendong + " INT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_music+ " TEXT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_rest_time + " TEXT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_geyan + " TEXT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_xianshi + " INT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_changliang + " INT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_break_time + " TEXT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_break_cnt + " INT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_language + " INT NOT NULL DEFAULT\"\"" + ")";
        Log.d("SQL", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }


}