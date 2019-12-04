package elitedj.me.todo.me;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import elitedj.me.todo.R;

public class Chosecolor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosecolor);
        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.colorban, R.drawable.colorban)
                .addSubMenu(Color.parseColor("#56C9BA"), R.drawable.point)//绿色
                .addSubMenu(Color.parseColor("#5CA0F3"), R.drawable.point)//蓝色
                .addSubMenu(Color.parseColor("#8650E4"), R.drawable.point)//紫色
                .addSubMenu(Color.parseColor("#CC8234"), R.drawable.point)//棕色
                .addSubMenu(Color.parseColor("#F797D5"), R.drawable.point)//粉色
                .addSubMenu(Color.parseColor("#FA682F"), R.drawable.point)// 橙色
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {}

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {}

            @Override
            public void onMenuClosed() {}

        });
    }
}
