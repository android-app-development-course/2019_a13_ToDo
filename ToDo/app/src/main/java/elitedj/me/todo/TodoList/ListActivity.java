package elitedj.me.todo.TodoList;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import elitedj.me.todo.R;
import elitedj.me.todo.me.Setting;
import elitedj.me.todo.me.SettingDB;

public class ListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView todolistview;
    private View newTodoView;
    private Intent intentNewTodo;
    private LocalActivityManager manager;
    private TodoItem[] todoItems = {
            new TodoItem(1,"学习","学习安卓","aaa", Calendar.getInstance().getTimeInMillis(), TaskState.DEFAULT, 0, "番茄钟"),
            new TodoItem(2,"学习","学习flash","aaa", Calendar.getInstance().getTimeInMillis(), TaskState.DEFAULT, 1, "番茄钟"),
            new TodoItem(3,"学习","学习数据库","aaa", Calendar.getInstance().getTimeInMillis(), TaskState.FINISHED, 2,"番茄钟"),
            new TodoItem(4,"学习","学习软件工程","aaa", Calendar.getInstance().getTimeInMillis(), TaskState.DEFAULT, 3, "番茄钟"),
            new TodoItem(5,"学习","学习编译原理","aaa", Calendar.getInstance().getTimeInMillis(), TaskState.FINISHED, 1, "番茄钟"),
            new TodoItem(6,"学习","学习数字图形","aaa", Calendar.getInstance().getTimeInMillis(), TaskState.DEFAULT, 2, "番茄钟"),
            new TodoItem(7,"学习","学习平面动画","aaa", Calendar.getInstance().getTimeInMillis(), TaskState.DEFAULT, 3, "番茄钟"),
    };
    private ArrayList<TodoItem> todoItemArrayList = new ArrayList<>();
    private TodoItemAdapter todoItemAdapter;
    private TaskAdapter taskAdapter;

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
        setContentView(R.layout.activity_list);

        // 设置LocalActivityManager
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        // 初始化toolbar
        initToolbar();
        initItems();
        todolistview = (RecyclerView) findViewById(R.id.todolist);
        todolistview.setLayoutManager(new LinearLayoutManager(this));
        //todoItemAdapter = new TodoItemAdapter(this, todoItemArrayList, getLayoutInflater());
        //todolistview.setAdapter(todoItemAdapter);
        taskAdapter = new TaskAdapter(this, todoItemArrayList);
        todolistview.setAdapter(taskAdapter);
    }

    private void initItems() {
        todoItemArrayList.clear();
        for(int i = 0; i < 7; i++)
        {
            todoItemArrayList.add(todoItems[i]);
        }
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.list_toolbar);
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
                        Toast.makeText(ListActivity.this, "rank", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_datastatic:
                        Toast.makeText(ListActivity.this, "data", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_mode:
                        Toast.makeText(ListActivity.this, "mode", Toast.LENGTH_SHORT).show();
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
        final PopupWindow  popupWindow = new PopupWindow(getLayoutInflater().inflate(R.layout.activity_new_todo, null), WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //final PopupWindow popupWindow = new PopupWindow(ListActivity.this);
        //获得要显示的视图
        View showView = ListActivity.this.getLayoutInflater().inflate(R.layout.activity_new_todo, null);
        //intentNewTodo = new Intent(TodoListActivity.this, NewTodoActivity.class);
        //newTodoView = manager.startActivity("viewID", intentNewTodo).getDecorView();
        //设置视图
        popupWindow.setContentView(showView);
        // 设置动画
        popupWindow.setAnimationStyle(R.style.pop_animation);
        //设置窗口的高
        popupWindow.setHeight(1000);
        //设置窗口的宽
        popupWindow.setWidth(800);
        //将窗口外部点击消失
        popupWindow.setOutsideTouchable(true);
        //设置获得焦点
        popupWindow.setFocusable(true);
        //将窗口显示在父控件的指定位置
        popupWindow.showAtLocation(showView, Gravity.CENTER,0,0);
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
        //找到控件
        final CheckBox radio1 = showView.findViewById(R.id.radio1);
        final CheckBox radio2 = showView.findViewById(R.id.radio2);
        final CheckBox radio3 = showView.findViewById(R.id.radio3);
        final EditText etTitle = showView.findViewById(R.id.et_title);
        final EditText etContent = showView.findViewById(R.id.et_content);
        String type; final String title; final String content;
        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio2.isChecked() || radio3.isChecked()) {
                    radio2.setChecked(false);
                    radio3.setChecked(false);
                }
                radio1.setChecked(true);
                System.out.println(radio1.getTag().toString());
            }
        });
        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio1.isChecked() || radio3.isChecked()) {
                    radio1.setChecked(false);
                    radio3.setChecked(false);
                }
                radio2.setChecked(true);
                System.out.println(radio2.getTag().toString());
            }
        });
        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio1.isChecked() || radio2.isChecked()) {
                    radio1.setChecked(false);
                    radio2.setChecked(false);
                }
                radio3.setChecked(true);
                System.out.println(radio3.getTag().toString());
            }
        });
        //点击按钮返回按钮监听事件
        Button sure = showView.findViewById(R.id.btn_newtodo_sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }

    /**
     * // todo
     * 打卡弹窗
     */
    public void clickIn() {
        //实例化对象
        final PopupWindow popupWindow = new PopupWindow(ListActivity.this);
        //获得要显示的视图
        //View showView = TodoListActivity.this.getLayoutInflater().inflate(R.layout.activity_click_in, null);
        intentNewTodo = new Intent(ListActivity.this, ClickInActivity.class);
        newTodoView = manager.startActivity("viewID", intentNewTodo).getDecorView();
        //设置视图
        popupWindow.setContentView(newTodoView);
        // 设置动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        //设置窗口的高
        popupWindow.setHeight(1300);
        //设置窗口的宽
        popupWindow.setWidth(800);
        //将窗口外部点击消失
        popupWindow.setOutsideTouchable(true);
        //设置获得焦点
        popupWindow.setFocusable(true);
        //将窗口显示在父控件的指定位置
        popupWindow.showAtLocation(newTodoView, Gravity.CENTER,0,0);
        //找到控件
        final EditText userName = newTodoView.findViewById(R.id.finishtime);
        final EditText password = newTodoView.findViewById(R.id.finishwork);
        final Toolbar toolbar = newTodoView.findViewById(R.id.clickin_toolbar);
        //点击左边返回按钮监听事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        Button login = newTodoView.findViewById(R.id.judge);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListActivity.this, "打卡成功", Toast.LENGTH_SHORT).show();
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
