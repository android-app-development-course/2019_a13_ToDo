package elitedj.me.todo;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import elitedj.me.todo.bean.User;
import elitedj.me.todo.me.Setting;
import elitedj.me.todo.me.SettingDB;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username;
    private EditText nickname;
    private EditText password;
    private Button register;

    private SettingDB DB;
    private SQLiteDatabase dbread;
    private Setting set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DB = new SettingDB(this);
        dbread = DB.getReadableDatabase();
        set = new Setting();
        inittheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bmob.initialize(this, "270e4c5889c9b50d64c82ef459cbcee4");

        // 系统透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        init();
    }

    private void init(){
        username = findViewById(R.id.username);
        nickname = findViewById(R.id.nickname);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.register:
                registerFun();
                break;
                default:break;
        }
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(0, 0);
    }

    private void registerFun(){
        if(username.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
        }
        else if(nickname.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }
        else{
            User bu = new User();
            bu.setUsername(username.getText().toString());
            bu.setPassword(password.getText().toString());
            bu.setNickName(nickname.getText().toString());

            //将drawable里面的图片存到手机本地
            Resources res = getResources();
            //获取默认头像
            BitmapDrawable d = (BitmapDrawable) res.getDrawable(R.drawable.default_face);
            Bitmap img = d.getBitmap();
            //获取sd卡目录
            String sdcard = Environment.getExternalStorageDirectory().toString();
            //存储头像的文件夹地址
            File f = new File(sdcard+"/ToDo");
            if(!f.exists()) {
                f.mkdirs();
            }
            //文件名
            String fn = username.getText().toString()+"Default_face.png";
            //文件路径
            String path = sdcard+"/ToDo/"+fn;
            //通过文件流保存图片到手机内存
            try {
                OutputStream os = new FileOutputStream(path);
                img.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.close();
            }catch (Exception e) {
                Log.e("--->", "registerFun: "+e, null);
            }
            Log.e("--->", "registerFun: "+path, null);
            bu.setFace(new BmobFile(username.getText().toString()+"Default_face", "", path));
            BmobFile bf = bu.getFace();
            //上传头像
            bf.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null) {
                        Log.e("--->", "register face upload success", null);
                    } else {
                        Log.e("--->", "register face upload fail:"+e, null);
                    }
                }
            });
            //注册
            bu.signUp(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败"+e, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
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
