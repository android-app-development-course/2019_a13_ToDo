package elitedj.me.todo.me;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PersonDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME_NOTES = "Person";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_Person_Name = "name";
    public static final String COLUMN_NAME_Person_sex = "sex";
    public static final String COLUMN_NAME_Person_birth = "birth";
    public static final String COLUMN_NAME_Person_email = "email";
    public static final String COLUMN_NAME_Person_tel = "tel";
    public static final String COLUMN_NAME_Person_school = "school";
    public static final String COLUMN_NAME_Person_out = "out";

    public PersonDB(Context context) {
        super(context, "Person", null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME_NOTES + "(" + COLUMN_NAME_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_Person_Name + " TEXT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_Person_sex + " TEXT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_Person_birth+ " TEXT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_Person_email + " TEXT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_Person_tel + " TEXT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_Person_school + " TEXT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_Person_out + " TEXT NOT NULL DEFAULT\"\"" + ")";
        Log.d("SQL", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}