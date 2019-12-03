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

public class Myinfo extends AppCompatActivity {

    private ListView lv1;

    private int[] imagesId={R.drawable.name,R.drawable.sex,R.drawable.birthday,R.drawable.email,R.drawable.tel,R.drawable.school,R.drawable.exit};
    private	String[] names={"蔡徐坤","男","1999-07-30","842184122@qq.com","13268453217","SCNU","退出登录"};
    private  String[] contents={"更改昵称","更改性别","更改生日","更改邮箱","更改电话","更改学校","退出该账号"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        lv1 =  findViewById(R.id.listView2);
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        lv1.setAdapter(mAdapter);

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
            View layout=View.inflate(Myinfo.this, R.layout.listview, null);
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
