package elitedj.me.todo.TodoList;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import elitedj.me.todo.R;

public class TodoListActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Toolbar toolbar;
    private LocalActivityManager manager;
    private View newTodoView;
    private Intent intentNewTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // 设置LocalActivityManager
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        // 初始化toolbar
        initToolbar();
        //setSupportActionBar(toolbar);

        imageButton = findViewById(R.id.fanqie);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TodoListActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.todo_toolbar);
        toolbar.inflateMenu(R.menu.todo_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_clock:
                        clickIn();
                        break;
                    case R.id.action_add:
                        newTodo();
                        break;
                    case R.id.action_rank:
                        Toast.makeText(TodoListActivity.this, "rank", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_datastatic:
                        Toast.makeText(TodoListActivity.this, "data", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_mode:
                        Toast.makeText(TodoListActivity.this, "mode", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 添加弹窗
     */
    public void newTodo() {
        //实例化对象
        final PopupWindow popupWindow = new PopupWindow(TodoListActivity.this);
        //获得要显示的视图
        View showView = TodoListActivity.this.getLayoutInflater().inflate(R.layout.activity_new_todo, null);
        //intentNewTodo = new Intent(TodoListActivity.this, NewTodoActivity.class);
        //newTodoView = manager.startActivity("viewID", intentNewTodo).getDecorView();
        //设置视图
        popupWindow.setContentView(showView);
        // 设置动画
        popupWindow.setAnimationStyle(R.style.pop_animation);
        //设置窗口的高
        popupWindow.setHeight(700);
        //设置窗口的宽
        popupWindow.setWidth(800);
        //将窗口外部点击消失
        popupWindow.setOutsideTouchable(true);
        //设置获得焦点
        popupWindow.setFocusable(true);
        //将窗口显示在父控件的指定位置
        popupWindow.showAtLocation(showView, Gravity.CENTER,0,0);
        //找到控件
        final EditText userName = showView.findViewById(R.id.user);
        final EditText password = showView.findViewById(R.id.pass);
        final Toolbar toolbar = showView.findViewById(R.id.popup_toolbar);
        //点击左边返回按钮监听事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        Button login = showView.findViewById(R.id.sure);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TodoListActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        //设置透明度
        attributes.alpha = 0.3f;
        //设置给Activity
        getWindow().setAttributes(attributes);

        //关闭PopupWindow的监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                //设置透明度
                attributes.alpha = 1.0f;
                //设置给Activity
                getWindow().setAttributes(attributes);
            }
        });
    }

    /**
     * 打卡弹窗
     */
    public void clickIn() {
        //实例化对象
        final PopupWindow popupWindow = new PopupWindow(TodoListActivity.this);
        //获得要显示的视图
        View showView = TodoListActivity.this.getLayoutInflater().inflate(R.layout.activity_click_in, null);
        //intentNewTodo = new Intent(TodoListActivity.this, NewTodoActivity.class);
        //newTodoView = manager.startActivity("viewID", intentNewTodo).getDecorView();
        //设置视图
        popupWindow.setContentView(showView);
        // 设置动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        //设置窗口的高
        popupWindow.setHeight(700);
        //设置窗口的宽
        popupWindow.setWidth(800);
        //将窗口外部点击消失
        popupWindow.setOutsideTouchable(true);
        //设置获得焦点
        popupWindow.setFocusable(true);
        //将窗口显示在父控件的指定位置
        popupWindow.showAtLocation(showView, Gravity.CENTER,0,0);
        //找到控件
        final EditText userName = showView.findViewById(R.id.finishtime);
        final EditText password = showView.findViewById(R.id.finishwork);
        final Toolbar toolbar = showView.findViewById(R.id.clickin_toolbar);
        //点击左边返回按钮监听事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        Button login = showView.findViewById(R.id.judge);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TodoListActivity.this, "打卡成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        //设置透明度
        attributes.alpha = 0.3f;
        //设置给Activity
        getWindow().setAttributes(attributes);

        //关闭PopupWindow的监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                //设置透明度
                attributes.alpha = 1.0f;
                //设置给Activity
                getWindow().setAttributes(attributes);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clock:
                Toast.makeText(this, "打卡", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_add:
                Toast.makeText(this, "Add selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_rank:
                Toast.makeText(this, "rank", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_datastatic:
                Toast.makeText(this, "data", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_mode:
                Toast.makeText(this, "mode", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
