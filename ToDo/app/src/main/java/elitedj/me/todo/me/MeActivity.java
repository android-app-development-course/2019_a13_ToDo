package elitedj.me.todo.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import elitedj.me.todo.R;


public  class MeActivity extends AppCompatActivity {

    private ListView lv1;

    private int[] imagesId={R.drawable.touxiang,R.drawable.touxiang,R.drawable.touxiang,R.drawable.touxiang};
    private	String[] names={"短毛猫","猴子","兔子","老鼠"};
    private  String[] contents={"可爱","顽皮","温顺","伶俐"};

    private String data[] = {"aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd"};//假数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        lv1 =  findViewById(R.id.listView2);
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        lv1.setAdapter(mAdapter);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MeActivity.this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
        //lv1.setAdapter(adapter);


    }
    class MyBaseAdapter extends BaseAdapter{
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
            View layout=View.inflate(MeActivity.this, R.layout.listview, null);
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
