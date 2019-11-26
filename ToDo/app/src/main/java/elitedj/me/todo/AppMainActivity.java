package elitedj.me.todo;

import android.content.Context;
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

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;


public class AppMainActivity extends AppCompatActivity {

    private ListView lv1;
    private AlphaTabsIndicator bottomTab;
    private ViewPager viewPager;
    private View listView, dataView, discoverView, meView;
    private List<View> views = new ArrayList<>();
    private int[] imagesId={R.drawable.touxiang,R.drawable.touxiang,R.drawable.touxiang,R.drawable.touxiang};
    private	String[] names={"短毛猫","猴子","兔子","老鼠"};
    private  String[] contents={"可爱","顽皮","温顺","伶俐"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        // 系统透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        bottomTab = findViewById(R.id.bottomTab);
        viewPager = findViewById(R.id.viewPage);
        LayoutInflater inflater = getLayoutInflater();
        listView = inflater.inflate(R.layout.activity_list, null);
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
<<<<<<< HEAD
        //lv1.setAdapter(mAdapter);
=======
        lv1.setAdapter(mAdapter);

>>>>>>> 6dc8b0a4b593cc32a3c36fd2d3efe72b0432a570
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
