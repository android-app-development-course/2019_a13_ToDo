package elitedj.me.todo.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import elitedj.me.todo.AppMainActivity;
import elitedj.me.todo.R;


public  class MeActivity extends AppCompatActivity {

    private ListView lv1;
    private ImageView touxiang;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        LayoutInflater inflater = getLayoutInflater();

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MeActivity.this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
        //lv1.setAdapter(adapter);

//        lv1 =  findViewById(R.id.listView2);
//        MeListAdpter mAdapter = new MeListAdpter(inflater);
//        //lv1.setAdapter(mAdapter);
//        lv1.setAdapter(mAdapter);
//        //头像按钮
//        touxiang = (ImageView) findViewById(R.id.touxiang);
//        touxiang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MeActivity.this, Myinfo.class);
//                startActivity(intent);
//            }
//        });

    }


}
