package elitedj.me.todo.me;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;

import elitedj.me.todo.R;

public class Coresetting extends AppCompatActivity {
    private SettingDB DB;
    private SQLiteDatabase dbread;
    private SQLiteDatabase dbw;
    private Setting set;
    private TextView Resttime;
    private TextView Break_time;
    private TextView Break_cnt;
    private SwitchCompat Xianshi;
    private SwitchCompat Changliang;
    private RelativeLayout RestimeR;
    private RelativeLayout BreaktimeR;
    private RelativeLayout BreakcntR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DB = new SettingDB(this);
        dbread = DB.getReadableDatabase();
        dbw   = DB.getWritableDatabase();
        set = new Setting();
        getData();
        inittheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coresetting);
        init();

        RestimeR  = findViewById(R.id.R3);
        RestimeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun3();
                getData();
            }

        });
        BreaktimeR  = findViewById(R.id.R7);
        BreaktimeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun7();
                getData();
            }

        });

        BreakcntR  = findViewById(R.id.R8);
        BreakcntR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun8();
                getData();
            }

        });


//        ContentValues values = new ContentValues();
//        values.put("_id",1);
//        values.put("theme",1);
//        values.put("lszd",1);
//        values.put("music","sdsad");
//        values.put("resttime","5分钟");
//        values.put("geyan","ALL IS WELL");
//        values.put("xianshi",1);
//        values.put("changliang",2);
//        values.put("break_time","3分钟");
//        values.put("break_cnt",5);
//        values.put("language",1);
//         String sql= "INSERT INTO Setting (_id, theme,lszd,music,resttime,geyan,xianshi,changliang,break_time,break_cnt,language) VALUES(1,1,1,'asd','5分钟','ALL IS WELL',1,0,'3分钟',5,1)";
//
//        dbread.execSQL(sql);
        //dbw.insert("Setting",null,values);
        //  dbread.execSQL("delete from Setting");
        //dbw.close();
    }

    private void getData() {
        Cursor cursor = dbread.query("Setting", null, null, null, null,null, null);
        int count = cursor.getCount();
        String str =String.valueOf(count);
        if(count==0)
        {
            Toast.makeText(this,"meiyou",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
            set.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            set.setTheme(cursor.getInt(cursor.getColumnIndex("theme")));
            set.setLszd(cursor.getInt(cursor.getColumnIndex("lszd")));
            set.setMusic(cursor.getString(cursor.getColumnIndex("music")));
            set.setResttime(cursor.getString(cursor.getColumnIndex("resttime")));
            set.setGeyan(cursor.getString(cursor.getColumnIndex("geyan")));
            set.setXianshi(cursor.getInt(cursor.getColumnIndex("xianshi")));
            set.setChangliang(cursor.getInt(cursor.getColumnIndex("changliang")));
            set.setBreatime(cursor.getString(cursor.getColumnIndex("break_time")));
            set.setBreakcnt(cursor.getInt(cursor.getColumnIndex("break_cnt")));

        }

        cursor.close();
    }

    protected  void init()
    {
        Resttime = findViewById(R.id.resttime);
        Resttime.setText(set.getResttime());
        Break_time = findViewById(R.id.break_time);
        Break_time.setText(set.getBreatime());
        Break_cnt = findViewById(R.id.break_cnt);
        Break_cnt.setText(set.getBreakcnt()+"");
        Xianshi = findViewById(R.id.xianshi);
        if(set.getXianshi()==1)
            Xianshi.setChecked(true);
        else
            Xianshi.setChecked(false);
        Changliang = findViewById(R.id.changliang);
        if(set.getChangliang()==1)
            Changliang.setChecked(true);
        else
            Changliang.setChecked(false);
    }

    public void fun3()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改休息时长");
        Log.d("my","qwe");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String col = "resttime";
                String updatesql = "update Setting set "+col+" = '"+ input.getText().toString()+"'";

                dbread.execSQL(updatesql);
                Resttime.setText(input.getText());
                //mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();
    }
    public void fun7()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("自定义暂停时间");
        Log.d("my","qwe");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String col = "break_time";
                String updatesql = "update Setting set "+col+" = '"+ input.getText().toString()+"'";

                dbread.execSQL(updatesql);
                Break_time.setText(input.getText());
                //mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();
    }
    public void fun8()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改每月强制暂停的次数");
        Log.d("my","qwe");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String col = "break_cnt";
                String updatesql = "update Setting set "+col+" = '"+ input.getText().toString()+"'";

                dbread.execSQL(updatesql);
                Break_cnt.setText(input.getText());
                //mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();

    }
    public void inittheme() {
        Cursor cursor = dbread.query("Setting", null, null, null, null,null, null);
        cursor.moveToNext();
        set.setTheme(cursor.getInt(cursor.getColumnIndex("theme")));
        switch (set.getTheme())
        {
            case 0:
                setTheme(R.style.GreenAppTheme);
                break;
            case 1:
                setTheme(R.style.BlueAppTheme);
                break;
            case 2:
                setTheme(R.style.PurpleAppTheme);
                break;
            case 3:
                setTheme(R.style.BronzeAppTheme);
                break;
            case 4:
                setTheme(R.style.PinkAppTheme);
                break;
            case 5:
                setTheme(R.style.OrAppTheme);
                break;

            default:
                break;
        }
        cursor.close();
    }

}
