package elitedj.me.todo;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

import elitedj.me.todo.Adapter.MomentListAdapter;
import elitedj.me.todo.TodoList.ListActivity;


public class AppMainActivity extends AppCompatActivity {

    private ImageView touxiang;
    private ListView lv1;
    private RecyclerView moment_list;
    private AlphaTabsIndicator bottomTab;
    private ViewPager viewPager;
    private View listView, dataView, discoverView, meView;
    private List<View> views = new ArrayList<>();
    private ArrayList<Moment> moments = new ArrayList<>();
    private LocalActivityManager manager;
    private Intent intentList;
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
        intentList = new Intent(AppMainActivity.this, ListActivity.class);
        listView = manager.startActivity("viewID", intentList).getDecorView();
        //listView = inflater.inflate(R.layout.activity_list, null);
        dataView = inflater.inflate(R.layout.activity_data, null);
        discoverView = inflater.inflate(R.layout.activity_discover, null);
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

        discoverViewInit();

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




    public void discoverViewInit(){
        // 设置朋友圈的titleBar的margin，让它在系统状态栏底部，适配所有手机
        LinearLayout titleBar = discoverView.findViewById(R.id.linearlayout1);
        // LinearLayout 表示该控件在LinearLayout里面，其他控件用法类似
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(titleBar.getLayoutParams());
        lp.setMargins(0,new NativeBarHeight().getNativeBarHeight(AppMainActivity.this)+16,0,0);
        titleBar.setLayoutParams(lp);

        AppBarLayout appBarLayout = discoverView.findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                RoundedImageView face = discoverView.findViewById(R.id.myFace);
                CoordinatorLayout bg = discoverView.findViewById(R.id.discoverViewBG);
                if(Math.abs(i) >= appBarLayout.getTotalScrollRange()) { //折叠
                    face.setVisibility(View.GONE);
                } else { //未折叠
                    bg.setBackgroundColor(getResources().getColor(R.color.white));
                    face.setVisibility(View.VISIBLE);
                }
            }
        });

        // 朋友圈的列表
        moment_list = discoverView.findViewById(R.id.momentlist);
        for(int i=1;i<=15;i++){
            Moment moment = new Moment();
            moment.setFace(R.drawable.img3);
            moment.setName((char)(i+'A')+"");
            moment.setContent("ajsdfhajklsdghlasdkghasjkldghasjkgadfgdfh");
            moments.add(moment);
        }
        moment_list.setLayoutManager(new LinearLayoutManager(discoverView.getContext()));
        MomentListAdapter momentListAdapter = new MomentListAdapter(moments, getLayoutInflater());
        moment_list.setAdapter(momentListAdapter);
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
