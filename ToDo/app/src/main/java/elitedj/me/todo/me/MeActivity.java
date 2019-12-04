package elitedj.me.todo.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import elitedj.me.todo.AppMainActivity;
import elitedj.me.todo.R;


public  class MeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lv1;
    private ImageView touxiang;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        LayoutInflater inflater = getLayoutInflater();

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MeActivity.this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
        //lv1.setAdapter(adapter);

        lv1 =  findViewById(R.id.listView2);
        MeListAdpter mAdapter = new MeListAdpter(inflater);
        //lv1.setAdapter(mAdapter);
        lv1.setAdapter(mAdapter);
        lv1.setOnItemClickListener(MeActivity.this);
        //头像按钮
        touxiang = (ImageView) findViewById(R.id.touxiang);
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, Myinfo.class);
                startActivity(intent);
            }
        });

    }
    //列表的反应事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //通过view获取其内部的组件，进而进行操作
        //String text = (String) ((TextView)view.findViewById(R.id.text)).getText();
        //大多数情况下，position和id相同，并且都从0开始
        switch (position){
            case 0://历史记录时间轴
                Intent intent = new Intent(MeActivity.this, rili.class);
                startActivity(intent);
                break;
            case 1://未来时间表
                break;
            case 2://Todo核心设置
                Intent intent2 = new Intent(MeActivity.this, Coresetting.class);
                startActivity(intent2);
                break;
            case  3://背景海报图设置

                break;
            case  4://主题颜色
                Intent intent3 = new Intent(MeActivity.this, Chosecolor.class);
                startActivity(intent3);
                break;
            case 5://更多外观和其他设置
                Intent intent5 = new Intent(MeActivity.this, Othersetting.class);
                startActivity(intent5);
                break;
            case 6://帮助
                break;
            case  7://分享
                break;
            default:break;
        }

        //String showText = "点击第" + position + "，ID为：" + id;
        //Toast.makeText(this, showText, Toast.LENGTH_LONG).show();
    }


}
