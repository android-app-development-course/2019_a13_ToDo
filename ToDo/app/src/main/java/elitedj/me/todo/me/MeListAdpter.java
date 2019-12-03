package elitedj.me.todo.me;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;

import elitedj.me.todo.R;

public class MeListAdpter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private int[] imagesId={R.drawable.finish,R.drawable.target,R.drawable.core,R.drawable.fengjing,R.drawable.color,R.drawable.set,R.drawable.help,R.drawable.share};
    private	String[] names={"历史记录时间轴","未来时间表","ToDo核心设置","背景海报图设置","主题颜色","更多外观 | 其他设置","帮助","分享给朋友"};
    private  String[] contents={"已完成计划的记录","重要日期倒计时","铃声震动|休息时长","计时或锁机时的背景海报","自定义主题颜色","卡片背景|主界面背景|语言设置","常见的使用问题和解决方法","如果你觉得好用就分享给你的小伙伴们呗"};

    public MeListAdpter(LayoutInflater layoutInflater){
        super();
        this.layoutInflater = layoutInflater;
    }
    @Override
    public long getItemId(int position) {
        // TODO 自动生成的方法存根
        return position;
    }

    @Override
    public Object getItem(int position) {
        // TODO 自动生成的方法存根
        return names[position];
    }

    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return names.length;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO 自动生成的方法存根
        convertView = layoutInflater.inflate(R.layout.listview, parent, false);
        //View layout=View.inflate(MeListAdpter.this, R.layout.listview, null);
        ImageView face = (ImageView)convertView.findViewById(R.id.face);
        TextView name =(TextView)convertView.findViewById(R.id.name);
        TextView mark = (TextView)convertView.findViewById(R.id.mark);

        face.setImageResource(imagesId[position]);
        name.setText(names[position]);
        mark.setText(contents[position]);

        return convertView;
    }

}