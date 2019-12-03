package elitedj.me.todo.discover;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;

import elitedj.me.todo.Adapter.MomentListAdapter;
import elitedj.me.todo.utils.NativeBarHeight;
import elitedj.me.todo.R;

public class DiscoverActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Moment> moments = new ArrayList<>();
    private RecyclerView momentList;
    private ImageButton newEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // 朋友圈的列表
        momentList = findViewById(R.id.momentlist);
        for(int i=1;i<=15;i++){
            Moment moment = new Moment();
            moment.setFace(R.drawable.img3);
            moment.setName((char)(i+'A')+"");
            moment.setContent("ajsdfhajklsdghlasdkghasjkldghasjkgadfgdfh");
            moments.add(moment);
        }
        momentList.setLayoutManager(new LinearLayoutManager(this));
        MomentListAdapter momentListAdapter = new MomentListAdapter(moments, getLayoutInflater());
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
        Toast.makeText(this, "newEdie", Toast.LENGTH_SHORT).show();
    }
}
