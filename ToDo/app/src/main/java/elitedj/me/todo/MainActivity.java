package elitedj.me.todo;

import android.content.Intent;
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
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username;
    private EditText password;
    private Button login;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
//        BmobUser bu = new BmobUser();
//        bu.setUsername(username.getText().toString());
//        bu.setPassword(password.getText().toString());
//        bu.login(this, new SaveListener() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//            }
//        });
        Intent intent = new Intent(MainActivity.this, AppMainActivity.class);
        startActivity(intent);
    }

    private void registerFun(){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
