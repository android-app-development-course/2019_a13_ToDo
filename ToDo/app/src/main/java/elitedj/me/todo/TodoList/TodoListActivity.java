package elitedj.me.todo.TodoList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import elitedj.me.todo.R;

/**
 * 代办列表Activity
 */
public class TodoListActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // 设置Toolbar
        toolbar = (Toolbar) findViewById(R.id.todo_toolbar);
        setSupportActionBar(toolbar);

        imageButton = findViewById(R.id.fanqie);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TodoListActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 设置菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_toolbar, menu);
        return true;
    }

    /**
     * 菜单点击事件
     * @param item
     * @return
     */
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
