package elitedj.me.todo;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;

import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.ninegrid.NineGridView;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import elitedj.me.todo.TodoList.ListActivity;

import elitedj.me.todo.bean.User;
import elitedj.me.todo.datastatic.DataStaticActivity;
import elitedj.me.todo.discover.DiscoverActivity;
import elitedj.me.todo.discover.ImageLoader;
import elitedj.me.todo.discover.Moment;
import elitedj.me.todo.discover.MomentListAdapter;
import elitedj.me.todo.me.MeActivity;
import elitedj.me.todo.me.PersonDB;
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
    private PersonDB PDB;
    private SQLiteDatabase dbread,dread2;
    private Setting set;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PDB = new PersonDB(this);
        dread2 = PDB.getReadableDatabase();

        DB = new SettingDB(this);
        dbread = DB.getReadableDatabase();
        set = new Setting();

        if(BmobUser.isLogin()) {
            //获取登录用户
            user = BmobUser.getCurrentUser(User.class);
            //设置nickName
            String sql = "update Person set name = '"+ user.getNickName()+"'";
            dread2.execSQL(sql);
        }
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
//        String sql= "INSERT INTO Setting (_id, theme,lszd,music,resttime,geyan,xianshi,changliang,break_time,break_cnt,language) VALUES(1,1,1,'asd','5分钟','ALL IS WELL',1,0,'3分钟',5,1)";
//
//        dbread.execSQL(sql);
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
        intentTodo = new Intent(AppMainActivity.this, ListActivity.class);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //1--->是处理新的朋友圈
        switch (resultCode) {
            case 1:
                Log.e("--->", "1111111111111111111111", null);
                RecyclerView recyclerView = discoverView.findViewById(R.id.momentlist);
                MomentListAdapter momentListAdapter = (MomentListAdapter) recyclerView.getAdapter();
                Moment moment = (Moment) data.getSerializableExtra("newMoment");
                momentListAdapter.addItem(0, moment);
//                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
//                lm.scrollToPosition(0);
                recyclerView.scrollToPosition(0);
                break;

                default:break;
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
