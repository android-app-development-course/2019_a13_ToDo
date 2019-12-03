package elitedj.me.todo.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

import elitedj.me.todo.R;

public class rili extends AppCompatActivity {

    private ListView lv1;

    private int[] imagesId = {R.drawable.zhou, R.drawable.zhou};
    private String[] names = {"唱跳rap", "打篮球"};
    private String[] contents = {"点击编写心得", "李涛崴脚了"};
    private String[] start_time = {"15 : 30", "17 : 00"};
    private String[] end_time = {"15 : 50", "18 : 00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rili);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .withSelectedDate(today);

        lv1 =  findViewById(R.id.listView2);
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        lv1.setAdapter(mAdapter);
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
}
