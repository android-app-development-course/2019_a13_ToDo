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
    private MomentListAdapter momentListAdapter;

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

        Bmob.initialize(this, "270e4c5889c9b50d64c82ef459cbcee4");

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

        //设置appbar
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

        //获取用户名和头像控件
        nickName = findViewById(R.id.nickname);
        face = findViewById(R.id.myFace);
        //设置头像
        Glide.with(this)
                .load("http://photocdn.sohu.com/20131128/Img390959345.jpg")
                .asBitmap()
                .into(face);

        //获取登录的用户信息
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
        initMomentList();
        momentList.setLayoutManager(new LinearLayoutManager(this));
        momentListAdapter = new MomentListAdapter(this, moments, getLayoutInflater());
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
        getParent().startActivityForResult(intent, 1);
        //startActivity(intent);
    }

//    //在AppMainActivity里面的onActivityResult函数里面处理结果
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e("--->", "onActivityResult: hello", null);
//        switch (resultCode) {
//            case 1:
//                Moment m = (Moment) data.getSerializableExtra("newMoment");
//                Log.e("--->", "onActivityResult: "+m.getName(), null);
//                momentListAdapter.addItem(0, m);
//                break;
//
//                default: break;
//        }
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

    public void initMomentList() {
        Moment m;
        ArrayList<Picture> p;

        //刘强东
        m = new Moment();
        m.setFace("http://5b0988e595225.cdn.sohucs.com/images/20180904/a5501e9c02bf44a89ec9474d18e17f95.jpeg");
        m.setName("刘强东");
        m.setContent("脱下西裤，套上囚服，奥利给！");
        p = new ArrayList<>();
        p.add(new Picture("https://wx4.sinaimg.cn/large/006fT3Elgy1fuwksvst0sj30l90byqck.jpg"));
        p.add(new Picture("http://5b0988e595225.cdn.sohucs.com/images/20180904/a5501e9c02bf44a89ec9474d18e17f95.jpeg"));
        m.setPictures(p);
        moments.add(m);

        //马云
        m = new Moment();
        m.setFace("http://n.sinaimg.cn/finance/transform/20161231/ypok-fxzencv2934675.jpg");
        m.setName("马云");
        m.setContent("我对钱没有兴趣！");
        p = new ArrayList<>();
        p.add(new Picture("http://i5.hexun.com/2018-10-10/194692903.jpg"));
        p.add(new Picture("http://i8.hexun.com/2018-10-10/194692904.jpg"));
        p.add(new Picture("http://i3.hexun.com/2018-10-10/194692905.jpg"));
        m.setPictures(p);
        moments.add(m);

        //马化腾坐公交
        m = new Moment();
        m.setFace("http://photocdn.sohu.com/20131128/Img390959345.jpg");
        m.setName("马化腾");
        m.setContent("今天穷到坐公交了");
        p = new ArrayList<>();
        p.add(new Picture("http://img.chenpe.com/8d/cc/a5/2b/8dcca52b982ffcae2d299453248f2b78size_580x370_size.jpg"));
        p.add(new Picture("http://5b0988e595225.cdn.sohucs.com/images/20170914/08bcb01ba73647ad9145a76cbac9cb8a.jpg"));
        m.setPictures(p);
        moments.add(m);

        //李彦宏
        m = new Moment();
        m.setFace("http://www.sx.chinanews.com/news/2011/0427/U71P8T1D36722F5DT20110427165123.jpg");
        m.setName("李彦宏");
        m.setContent("What's your problem?");
        p = new ArrayList<>();
        p.add(new Picture("https://wx4.sinaimg.cn/mw690/007MC2Nagy1g4nn901xx2j306b05lt8o.jpg"));
        m.setPictures(p);
        moments.add(m);

        //蔡徐坤
        m = new Moment();
        m.setFace("http://5b0988e595225.cdn.sohucs.com/q_70,c_zoom,w_640/images/20190418/24e27baac15748759f2080aadbd99cc8.jpeg");
        m.setName("蔡徐坤(篮球之神)");
        m.setContent("欧文都学不会我的运球， 哦哦哦哦哦~");
        p = new ArrayList<>();
        p.add(new Picture("http://n.sinaimg.cn/front/253/w619h434/20190403/5kjK-hvcmeuy0880454.jpg"));
        p.add(new Picture("http://n.sinaimg.cn/front/231/w607h424/20190403/m0bK-hvcmeuy0880599.jpg"));
        p.add(new Picture("http://n.sinaimg.cn/sinacn09/300/w440h660/20180416/ce89-fzcyxmv2121495.jpg"));
        m.setPictures(p);
        moments.add(m);

        //PDD
        m = new Moment();
        m.setFace("http://img1.dwstatic.com/lol/1701/348197040731/348197144646.jpeg");
        m.setName("PDD");
        m.setContent("英雄联盟全球总决赛两届的FMVP都是从我这里出去的，很多人都说我被Ning和Tian演了，开玩笑我可是PDD，谁能演我，只有李现能演我。有人说韩商言的原型是我，我去专门看了那部剧，确实挺像的，都创办了俱乐部，都没有夺冠，都是冷酷的帅哥。");
        p = new ArrayList<>();
        p.add(new Picture("http://img1.gtimg.com/gamezone/pics/hv1/78/82/2222/144506538.png"));
        p.add(new Picture("http://img1.gtimg.com/gamezone/pics/hv1/77/82/2222/144506537.png"));
        p.add(new Picture("https://04imgmini.eastday.com/mobile/20191224/20191224001414_819daf8f344b29ee65d47b732abbcbe4_1.jpeg"));
        m.setPictures(p);
        moments.add(m);


    }

}
