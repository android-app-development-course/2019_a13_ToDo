package elitedj.me.todo.me;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import elitedj.me.todo.AppMainActivity;
import elitedj.me.todo.R;


public  class MeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lv1;
    private ImageView touxiang;
    private SettingDB DB;
    private PersonDB PDB;
    private TextView usename;
    private SQLiteDatabase dbread,dread2;
    private Setting set;
    private static String path = "/sdcard/myHead/";// sd路径



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PDB = new PersonDB(this);
        dread2 = PDB.getReadableDatabase();

        DB = new SettingDB(this);
        dbread = DB.getReadableDatabase();
        set = new Setting();

        inittheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        usename = (TextView) findViewById(R.id.usern);
        Cursor cursor1 = dread2.query("Person", null, null, null, null,null, null);

        cursor1.moveToNext();
        String name = cursor1.getString(cursor1.getColumnIndex("name"));

        usename.setText(name);

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
                Bundle bundle  = new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);// 跳转并要求返回值，0代表请求值
            }
        });
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            touxiang.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();//获取第二个Activity传回的数据
        Toast.makeText(this, "阿达", Toast.LENGTH_SHORT).show();
        if(requestCode==1) {
            if (resultCode == 1) {
                Toast.makeText(MeActivity.this, bundle.getString("result"), Toast.LENGTH_LONG).show();
            }
        }
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
