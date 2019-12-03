package elitedj.me.todo;

import android.app.LocalActivityManager;
import android.content.Intent;
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

import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

import elitedj.me.todo.discover.DiscoverActivity;
import elitedj.me.todo.TodoList.TodoListActivity;
import elitedj.me.todo.me.Myinfo;


public class AppMainActivity extends AppCompatActivity {

    private ImageView touxiang;
    private ListView lv1;
    private AlphaTabsIndicator bottomTab;
    private ViewPager viewPager;
    private View listView, dataView, discoverView, meView;
    private List<View> views = new ArrayList<>();
    private LocalActivityManager manager;
    private Intent intentList, intentDiscover;
    private int[] imagesId={R.drawable.finish,R.drawable.target,R.drawable.core,R.drawable.fengjing,R.drawable.color,R.drawable.set,R.drawable.help,R.drawable.share};
    private	String[] names={"历史记录时间轴","未来时间表","ToDo核心设置","背景海报图设置","主题颜色","更多外观 | 其他设置","帮助","分享给朋友"};
    private  String[] contents={"已完成计划的记录","重要日期倒计时","铃声震动|休息时长","计时或锁机时的背景海报","自定义主题颜色","卡片背景|主界面背景|语言设置","常见的使用问题和解决方法","如果你觉得好用就分享给你的小伙伴们呗"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        // 系统透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        // 设置LocalActivityManager
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        bottomTab = findViewById(R.id.bottomTab);
        viewPager = findViewById(R.id.viewPage);
        LayoutInflater inflater = getLayoutInflater();

        // 关联ListActivity
        intentList = new Intent(AppMainActivity.this, TodoListActivity.class);
        listView = manager.startActivity("viewID", intentList).getDecorView();
        //listView = inflater.inflate(R.layout.activity_todo_list, null);

        dataView = inflater.inflate(R.layout.activity_data_static, null);

        // 关联DiscoverActivity
        intentDiscover = new Intent(AppMainActivity.this, DiscoverActivity.class);
        discoverView = manager.startActivity("viewID", intentDiscover).getDecorView();
        //discoverView = inflater.inflate(R.layout.activity_discover, null);

        meView = inflater.inflate(R.layout.activity_me, null);
        views.add(listView);
        views.add(dataView);
        views.add(discoverView);
        views.add(meView);
        viewPager.setAdapter(new pagerAdapter());
        bottomTab.setViewPager(viewPager);

        lv1 =  meView.findViewById(R.id.listView2);
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        //lv1.setAdapter(mAdapter);
        lv1.setAdapter(mAdapter);


        //头像按钮
        touxiang = (ImageView) meView.findViewById(R.id.touxiang);
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppMainActivity.this, Myinfo.class);
                startActivity(intent);
            }
        });
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
            View layout=View.inflate(AppMainActivity.this, R.layout.listview, null);
            ImageView face = (ImageView)layout.findViewById(R.id.face);
            TextView name =(TextView)layout.findViewById(R.id.name);
            TextView mark = (TextView)layout.findViewById(R.id.mark);

            face.setImageResource(imagesId[position]);
            name.setText(names[position]);
            mark.setText(contents[position]);

            return layout;
        }

    }
}
