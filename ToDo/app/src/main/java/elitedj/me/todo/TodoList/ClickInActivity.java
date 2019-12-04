package elitedj.me.todo.TodoList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

import elitedj.me.todo.R;

public class ClickInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_in);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_click);
        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .withSelectedDate(today);
    }
}
