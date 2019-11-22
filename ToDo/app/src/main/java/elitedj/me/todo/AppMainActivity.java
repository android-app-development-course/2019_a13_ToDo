package elitedj.me.todo;

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

import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;


public class AppMainActivity extends AppCompatActivity {

    private AlphaTabsIndicator bottomTab;
    private ViewPager viewPager;
    private View listView, dataView, discoverView, meView;
    private List<View> views = new ArrayList<>();

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
}
