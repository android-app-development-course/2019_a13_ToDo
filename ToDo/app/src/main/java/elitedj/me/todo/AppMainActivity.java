package elitedj.me.todo;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.ListView;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.ninegrid.NineGridView;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

import elitedj.me.todo.TodoList.TodoListActivity;

import elitedj.me.todo.datastatic.DataStaticActivity;
import elitedj.me.todo.discover.DiscoverActivity;
import elitedj.me.todo.discover.ImageLoader;
import elitedj.me.todo.me.MeActivity;
import elitedj.me.todo.me.MeListAdpter;
import elitedj.me.todo.me.Myinfo;
import elitedj.me.todo.me.Setting;
import elitedj.me.todo.me.SettingDB;

public class AppMainActivity extends AppCompatActivity {

    private ImageView touxiang;
    private ListView lv1;
    private AlphaTabsIndicator bottomTab;
    private ViewPager viewPager;
    private View TodoView, dataView, discoverView, meView;
    private List<View> views = new ArrayList<>();
    private LocalActivityManager manager;
    private Intent intentTodo, intentDiscover, intentMe, intentData;

    private SettingDB DB;
    private SQLiteDatabase dbread;
    private Setting set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DB = new SettingDB(this);
        dbread = DB.getReadableDatabase();
        set = new Setting();
        inittheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);


        // 朋友圈九宫格图片的加载器
        NineGridView.setImageLoader(new ImageLoader());

        // 系统透明状态栏
        ImmersionBar.with(this).init();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }

        // 设置LocalActivityManager
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        bottomTab = findViewById(R.id.bottomTab);
        viewPager = findViewById(R.id.viewPage);
        LayoutInflater inflater = getLayoutInflater();

        // 关联ListActivity
        intentTodo = new Intent(AppMainActivity.this, TodoListActivity.class);
        TodoView = manager.startActivity("viewID", intentTodo).getDecorView();
        //listView = inflater.inflate(R.layout.activity_todo_list, null);

        // 关联data
        intentData = new Intent(AppMainActivity.this, DataStaticActivity.class);
        dataView = manager.startActivity("viewID", intentData).getDecorView();
        //dataView = inflater.inflate(R.layout.activity_data_static, null);

        // 关联me
        intentMe = new Intent(AppMainActivity.this, MeActivity.class);
        meView = manager.startActivity("viewID", intentMe).getDecorView();
        //meView = inflater.inflate(R.layout.activity_me, null);

        // 关联DiscoverActivity
        intentDiscover = new Intent(AppMainActivity.this, DiscoverActivity.class);
        discoverView = manager.startActivity("viewID", intentDiscover).getDecorView();
        //discoverView = inflater.inflate(R.layout.activity_discover, null);


        views.add(TodoView);
        views.add(dataView);
        views.add(discoverView);
        views.add(meView);
        viewPager.setAdapter(new pagerAdapter());
        bottomTab.setViewPager(viewPager);




    }


    //viewPager的Adapter
    private class pagerAdapter extends PagerAdapter
    {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            container.addView(views.get(position));
            return views.get(position);
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
