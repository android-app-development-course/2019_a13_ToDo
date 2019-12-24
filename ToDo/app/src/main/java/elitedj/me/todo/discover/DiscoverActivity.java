package elitedj.me.todo.discover;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.lzy.ninegrid.NineGridView;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import elitedj.me.todo.bean.User;
import elitedj.me.todo.me.Setting;
import elitedj.me.todo.me.SettingDB;
import elitedj.me.todo.utils.NativeBarHeight;
import elitedj.me.todo.R;

public class DiscoverActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Moment> moments = new ArrayList<>();
    private RecyclerView momentList;
    private ImageButton newEdit;
    private TextView nickName;
    private RoundedImageView face;
    private User user;

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
        setContentView(R.layout.activity_discover);

        discoverViewInit();

    }

    public void discoverViewInit(){
        // 设置朋友圈的titleBar的margin，让它在系统状态栏底部，适配所有手机
        LinearLayout titleBar = findViewById(R.id.linearlayout1);
        // LinearLayout 表示该控件在LinearLayout里面，其他控件用法类似
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(titleBar.getLayoutParams());
        lp.setMargins(0,new NativeBarHeight().getNativeBarHeight(this)+16,0,0);
        titleBar.setLayoutParams(lp);

        newEdit = findViewById(R.id.newedit);
        newEdit.setOnClickListener(this);

        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                RoundedImageView face = findViewById(R.id.myFace);
                CoordinatorLayout bg = findViewById(R.id.discoverViewBG);
                if(Math.abs(i) >= appBarLayout.getTotalScrollRange()) { //折叠
                    face.setVisibility(View.GONE);
                } else { //未折叠
                    bg.setBackgroundColor(getResources().getColor(R.color.white));
                    face.setVisibility(View.VISIBLE);
                }
            }
        });

        nickName = findViewById(R.id.nickname);

        face = findViewById(R.id.myFace);
        Glide.with(this)
                .load("https://c-ssl.duitang.com/uploads/item/201601/25/20160125160131_t8ZC2.thumb.700_0.jpeg")
                .asBitmap()
                .into(face);

        if(BmobUser.isLogin()) {
            //获取登录用户
            user = BmobUser.getCurrentUser(User.class);
            //设置nickName
            nickName.setText(user.getNickName());
            //下载头像
            BmobFile bf = user.getFace();
            bf.download(new DownloadFileListener() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null) {
                        Glide.with(DiscoverActivity.this)
                                .load(s)
                                .asBitmap()
                                .into(face);
                    }else {
                        Log.e("--->", "done: discover face download fail"+e, null);
                    }
                }

                @Override
                public void onProgress(Integer integer, long l) {

                }
            });

        } else {
            Log.e("--->", "discoverViewInit: discover not get face and nickname", null);
        }

        // 朋友圈的列表
        momentList = findViewById(R.id.momentlist);
        for(int i=1;i<=9;i++){
            Moment moment = new Moment();
            moment.setFace(R.drawable.img3);
            moment.setName("小"+(char)(i+'A'-1));
            moment.setContent("ajsdfhajklsdghlasdkghasjkldghasjkgadfgdfh");
            ArrayList<Picture> pictures = new ArrayList<>();
            for(int j=1;j<=Math.min(i, 9);j++) {
                pictures.add(new Picture("https://c-ssl.duitang.com/uploads/item/201706/25/20170625143749_mtSZE.thumb.700_0.jpeg"));
            }
            moment.setPictures(pictures);
            moments.add(moment);
        }
        momentList.setLayoutManager(new LinearLayoutManager(this));
        MomentListAdapter momentListAdapter = new MomentListAdapter(this, moments, getLayoutInflater());
        momentList.setAdapter(momentListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newedit:
                newEditFun();
                break;

                default: break;
        }
    }

    private void newEditFun(){
        Intent intent = new Intent(this, EditNewMomentActivity.class);
        startActivity(intent);
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
