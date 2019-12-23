package elitedj.me.todo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import elitedj.me.todo.bean.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username;
    private EditText nickname;
    private EditText password;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            bu.signUp(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(RegisterActivity.this, "注册失败:"+s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
