package elitedj.me.todo.me;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import elitedj.me.todo.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class Chosecolor extends AppCompatActivity {

    private SettingDB DB;
    private SQLiteDatabase dbread;
    private Setting set;
    private FancyButton Zidingyi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DB = new SettingDB(this);
        dbread = DB.getReadableDatabase();
        set = new Setting();
        inittheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosecolor);
        Zidingyi = (FancyButton) findViewById(R.id.btn_spotify);
        Zidingyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chosecolor.this, color_picker.class);
                // Bundle bundle  = new Bundle();
                //intent.putExtras(bundle);
                // startActivityForResult(intent, 1);// 跳转并要求返回值，0代表请求值
                startActivity(intent);
            }
        });
        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.colorban, R.drawable.colorban)
                .addSubMenu(Color.parseColor("#56C9BA"), R.drawable.point)//绿色
                .addSubMenu(Color.parseColor("#0084ff"), R.drawable.point)//蓝色
                .addSubMenu(Color.parseColor("#8650E4"), R.drawable.point)//紫色
                .addSubMenu(Color.parseColor("#CC8234"), R.drawable.point)//棕色
                .addSubMenu(Color.parseColor("#F797D5"), R.drawable.point)//粉色
                .addSubMenu(Color.parseColor("#FA682F"), R.drawable.point)// 橙色
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {

                        Log.d("my","qwe");
                        String col = "theme";
                        String updatesql = "update Setting set "+col+" = '"+ index+"'";
                        dbread.execSQL(updatesql);

                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {}

            @Override
            public void onMenuClosed() {}

        });
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
