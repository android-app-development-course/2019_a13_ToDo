package elitedj.me.todo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import elitedj.me.todo.bean.User;
import elitedj.me.todo.me.Setting;
import elitedj.me.todo.me.SettingDB;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username;
    private EditText password;
    private Button login;
    private TextView register;

    private SettingDB DB;
    private SQLiteDatabase dbread;
    private Setting set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DB = new SettingDB(this);
        dbread = DB.getReadableDatabase();
        set = new Setting();
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
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "270e4c5889c9b50d64c82ef459cbcee4");

        // 系统透明状态栏
        ImmersionBar.with(this).init();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }

        init();
    }

    private void init(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login:
                loginFun();
                break;
            case R.id.register:
                registerFun();
                break;

                default:break;
        }
    }

    private void loginFun(){
        BmobUser bu = new BmobUser();
        bu.setUsername(username.getText().toString());
        bu.setPassword(password.getText().toString());
        bu.login(new SaveListener<User>() {
            @Override
            public  void  done(User myUser, BmobException e)
            {
                if(e==null)
                {
                    User user=User.getCurrentUser(User.class);
                    Intent intent = new Intent(MainActivity.this, AppMainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void registerFun(){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
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
