package elitedj.me.todo.discover;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.support.v7.widget.Toolbar;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import elitedj.me.todo.R;
import elitedj.me.todo.me.Setting;
import elitedj.me.todo.me.SettingDB;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class EditNewMomentActivity extends AppCompatActivity {

    private EditText content;
    private GridView gridView;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private GridAdapter gridAdapter;
    private int wh;
    private int width;

    private SettingDB DB;
    private SQLiteDatabase dbread;
    private Setting set;

    private static final int REQUEST_CAMERA_CODE = 10;
    //private static final int REQUEST_PREVIEW_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DB = new SettingDB(this);
        dbread = DB.getReadableDatabase();
        set = new Setting();
        inittheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_moment);

         //系统透明状态栏
        ImmersionBar.with(this).init();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }

        setToolBar();

        // 计算每一张选择的图片的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        wh = display.getWidth();
        width = (wh-Dp2Px(this, 36)) / 3;

        content = findViewById(R.id.newContent);
        gridView = findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String img = (String) parent.getItemAtPosition(position);
                // 点击添加照片
                if(img.equals("addPic")) {
                    imagePaths.remove("addPic");
                    // 照片选择器，最多九张，imagePaths是存照片的url
                    Intent intent = new Intent();
                    intent.setClass(EditNewMomentActivity.this, MultiImageSelectorActivity.class);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, imagePaths);
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                }
            }
        });
        // 初始时只有添加图片按钮
        imagePaths.add("addPic");
        gridAdapter = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);

    }

    public void setToolBar(){
        //设置toolbar
        Toolbar toolbar = findViewById(R.id.newEditToolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.post:
                        postFun();
                        break;

                        default: break;
                }
                return true;
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
        inflater.inflate(R.menu.edit_new_moment_toolbar, menu);
        return true;
    }


    // 接收参数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 添加了照片
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    // list是选取的照片的url
                    ArrayList<String> list = data.getStringArrayListExtra(me.nereo.multi_image_selector.MultiImageSelectorActivity.EXTRA_RESULT);
                    // 加载照片
                    loadAdapter(list);
                    break;
                    default:break;
            }
        }
        // 没添加照片
        else if(resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    imagePaths.add("addPic");
                    gridView.setAdapter(new GridAdapter(imagePaths));
                    break;

                    default:break;
            }
        }
    }

    // 发布功能
    public void postFun() {
        if(!content.getText().toString().equals("")) {
            Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }


    // 加载照片
    private void loadAdapter(ArrayList<String> paths) {
        // 清空原先的，用新的paths数组赋值
        imagePaths.clear();
        imagePaths.addAll(paths);
        imagePaths.add("addPic");
        // 最多9张，多的删除
        while (imagePaths.size() >= 10) {
            imagePaths.remove(imagePaths.size() - 1);
        }
        gridAdapter = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
    }


    //GridView的Adapter
    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;
        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls =listUrls;
            inflater = LayoutInflater.from(EditNewMomentActivity.this);
        }

        @Override
        public int getCount() {
            return listUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.pic_item, parent, false);
                holder.image = convertView.findViewById(R.id.pic);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // 调整图片的宽度，适配手机
            AbsListView.LayoutParams param = new AbsListView.LayoutParams(width,width);
            convertView.setLayoutParams(param);

            final String path = listUrls.get(position);
            if(path.equals("addPic")) {
                holder.image.setImageResource(R.drawable.ic_add);
            } else {
                // 加载图片
                Glide.with(EditNewMomentActivity.this)
                        .load(path)
                        .centerCrop()
                        .crossFade()
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                        .dontAnimate()
                        .thumbnail(0.8f)
                        .into(holder.image);
            }

            return convertView;
        }

        class ViewHolder {
            ImageView image;
        }

    }


    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
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
