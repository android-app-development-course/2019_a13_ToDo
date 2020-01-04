package elitedj.me.todo.me;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

import elitedj.me.todo.R;

public class rili extends AppCompatActivity {
    private SettingDB DB;
    private SQLiteDatabase dbread;
    private Setting set;

    private ListView lv1;

    private int[] imagesId = {R.drawable.zhou, R.drawable.zhou};
    private String[] kong = {};
    private String[] names = {};
    private String[] contents = {};
    private String[] start_time = {};
    private String[] end_time = {};
    private String[] names3 = {"唱跳rap", "打篮球"};
    private String[] contents3 = {"点击编写心得", "李涛崴脚了"};
    private String[] names1 = {"学习数据库", "学习编译原理"};
    private String[] contents1 = {"数据库真有趣", "编译原理太南了"};
    private String[] start_time3 = {"15 : 30", "17 : 00"};
    private String[] end_time3 = {"15 : 50", "18 : 00"};
    private String[] start_time1 = {" 9: 30", "16 : 00"};
    private String[] end_time1 = {"11 : 00", "18 : 00"};
    private String[] names2 = { "打cf"};
    private String[] contents2 = { "cf又掉分了，cy儿子好强"};
    private String[] start_time2 = { "21 : 30"};
    private String[] end_time2 = { "23 : 30"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DB = new SettingDB(this);
        dbread = DB.getReadableDatabase();
        set = new Setting();
        inittheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rili);
        Calendar nextDay = Calendar.getInstance();
        nextDay.add(Calendar.DATE, 10);
        Calendar lastyear = Calendar.getInstance();
        lastyear.add(Calendar.YEAR,-1);

        lv1 =  findViewById(R.id.listView2);
        final MyBaseAdapter mAdapter = new MyBaseAdapter();
        lv1.setAdapter(mAdapter);

        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);

        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener(){
            @Override
            public void onDateSelected(Date day) {

                int  month = day.getMonth();
                int  dayday = day.getDate();
                if(month==11 && dayday == 21)
                {
                    names = names1;
                    contents = contents1;
                    start_time = start_time1;
                    end_time = end_time1;
                }
                else if(month==11 && dayday == 12)
                {
                    names = names2;
                    contents = contents2;
                    start_time = start_time2;
                    end_time = end_time2;
                }
                else if(month==11 && dayday == 15)
                {
                    names = names3;
                    contents = contents3;
                    start_time = start_time3;
                    end_time = end_time3;
                }
                else
                {
                    names = contents = start_time = end_time =kong;
                }

                mAdapter.notifyDataSetChanged();

                //Toast.makeText(rili.this, month+ "  "+dayday, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onDateUnselected(Date day) {
                //Toast.makeText(rili.this, day.toString(), Toast.LENGTH_LONG).show();
            }

        });
        // Log.d("my",calendar.getSelectedDate().toString());
        Date today = new Date();
        Log.d("my",today.toString());
        calendar.init(lastyear.getTime(),nextDay.getTime() ).withSelectedDate(today);



    }

    class MyBaseAdapter extends BaseAdapter {
        @Override
        public long getItemId(int position) {
            // TODO 自动生成的方法存根
            return position;
        }

        @Override
        public Object getItem(int position) {
            // TODO 自动生成的方法存根
            return names[position];
        }

        @Override
        public int getCount() {
            // TODO 自动生成的方法存根
            return names.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO 自动生成的方法存根
            View layout = View.inflate(rili.this, R.layout.timetable, null);
            ImageView face = (ImageView) layout.findViewById(R.id.face);
            TextView name = (TextView) layout.findViewById(R.id.name);
            TextView xinde = (TextView) layout.findViewById(R.id.xinde);
            TextView start = (TextView) layout.findViewById(R.id.start_time);
            TextView end = (TextView) layout.findViewById(R.id.end_time);

            face.setImageResource(imagesId[position]);
            name.setText(names[position]);
            xinde.setText(contents[position]);
            start.setText(start_time[position]);
            end.setText(end_time[position]);

            return layout;
        }
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
