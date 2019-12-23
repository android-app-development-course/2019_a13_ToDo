package elitedj.me.todo.me;

import android.content.Intent;
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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //this.setTheme(R.style.DrakAppTheme);
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
}