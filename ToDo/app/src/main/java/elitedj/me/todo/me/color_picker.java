package elitedj.me.todo.me;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shixia.colorpickerview.ColorPickerView;
import com.shixia.colorpickerview.OnColorChangeListener;

import elitedj.me.todo.MainActivity;
import elitedj.me.todo.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class color_picker extends AppCompatActivity {

    private FancyButton sure;
    private ColorPickerView colorPicker;
    private SettingDB DB;
    private SQLiteDatabase dbread;
    private Setting set;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        DB = new SettingDB(this);
        dbread = DB.getReadableDatabase();
        set = new Setting();
        inittheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        //initTheme();
        init();
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initTheme();
                //Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_color_picker);

            }
        });
    }

    public void initTheme() {
        this.setTheme(R.style.BlueAppTheme);
    }
    protected void init()
    {
        sure = findViewById(R.id.btn_sure);
        sure.setText("确 定");
        sure.setRadius(20);
        sure.setTextSize(30);
        colorPicker = findViewById(R.id.cpv_color);
        colorPicker.setOnColorChangeListener(new OnColorChangeListener() {
            @Override
            public void colorChanged(int color) {
                sure.setBackgroundColor(color);
                //tv.setText("#" + Integer.toHexString(color));
            }
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