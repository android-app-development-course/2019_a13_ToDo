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

public class TodoListActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

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

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.todo_toolbar);
        toolbar.inflateMenu(R.menu.todo_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_clock:
                        Toast.makeText(TodoListActivity.this, "打卡", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_add:
                        Toast.makeText(TodoListActivity.this, "Add selected", Toast.LENGTH_SHORT).show();
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
