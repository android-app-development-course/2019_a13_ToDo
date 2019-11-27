package elitedj.me.todo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.varunest.sparkbutton.SparkButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

import elitedj.me.todo.AppMainActivity;
import elitedj.me.todo.Moment;
import elitedj.me.todo.R;

public class MomentListAdapter extends BaseAdapter {

    private ArrayList<Moment> moments = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public MomentListAdapter(ArrayList<Moment> moments, LayoutInflater layoutInflater){
        super();
        this.moments = moments;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return moments.get(position);
    }

    @Override
    public int getCount() {
        return moments.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.moment_item, parent, false);
            holder = new ViewHolder();
            holder.face = convertView.findViewById(R.id.face);
            holder.name = convertView.findViewById(R.id.friendname);
            holder.content = convertView.findViewById(R.id.content);
            holder.like = convertView.findViewById(R.id.like);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Moment moment = moments.get(position);
        holder.face.setImageResource(moment.getFace());
        holder.name.setText(moment.getName());
        holder.content.setText(moment.getContent());
        holder.like.setChecked(false);

        return convertView;
    }

    private class ViewHolder{
        RoundedImageView face;
        TextView name;
        TextView content;
        SparkButton like;
    }

}
