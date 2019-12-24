package elitedj.me.todo.TodoList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import elitedj.me.todo.R;
import elitedj.me.todo.R2;
import elitedj.me.todo.me.Setting;
import elitedj.me.todo.me.SettingDB;
import elitedj.me.todo.utils.CommonUtil;

public class NewTodoActivity extends AppCompatActivity {

    @BindViews({R2.id.radio1, R2.id.radio2, R2.id.radio3})
    List<CheckBox> radios; // 单选组

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
        setContentView(R.layout.activity_new_todo);

        ButterKnife.bind(this);

        // 如果有初始状态需要显示，参见：
        // com.dommy.selectcustom.util.CommonUtil.checkOne()
        // com.dommy.selectcustom.util.CommonUtil.checkMany()

    }

    /**
     * 单选项点击事件
     * @param checkBox
     */
    @OnClick({R2.id.radio1, R2.id.radio2, R2.id.radio3})
    void changeRadios(CheckBox checkBox) {
        CommonUtil.unCheck(radios);
        checkBox.setChecked(true);

        // 显示选中项值
        String checkedValues = CommonUtil.getOne(radios);
        System.out.println("选中了：" + checkedValues);
    }

//    /**
//     * 复选项点击事件
//     * @param checkBox
//     */
//    @OnClick({R.id.checkbox1, R.id.checkbox2, R.id.checkbox3, R.id.checkbox4, R.id.checkbox5, R.id.checkbox6, R.id.checkbox7})
//    void changeCheckBoxs(CheckBox checkBox) {
//        // 显示选中项值
//        String checkedValues = CommonUtil.getMany(checkBoxes);
//        tvValue.setText("选中了：" + checkedValues);
//    }


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
