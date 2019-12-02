package elitedj.me.todo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.varunest.sparkbutton.SparkButton;

import java.util.ArrayList;
import elitedj.me.todo.Moment;
import elitedj.me.todo.R;

public class MomentListAdapter extends RecyclerView.Adapter<MomentListAdapter.ViewHolder> {

    private ArrayList<Moment> moments;
    private LayoutInflater layoutInflater;

    public MomentListAdapter(ArrayList<Moment> moments, LayoutInflater layoutInflater){
        super();
        this.moments = moments;
        this.layoutInflater = layoutInflater;
        //Log.e("----------------->", "MomentListAdapter: "+moments.size(), null);
    }

    @Override
    public int getItemCount(){
        return moments.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.moment_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Moment moment = moments.get(position);
        holder.face.setImageResource(moment.getFace());
        holder.friendname.setText(moment.getName());
        holder.content.setText(moment.getContent());
        holder.like.setChecked(false);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView face;
        private TextView friendname;
        private TextView content;
        private SparkButton like;

        public ViewHolder(View view){
            super(view);
            face = view.findViewById(R.id.face);
            friendname = view.findViewById(R.id.friendname);
            content = view.findViewById(R.id.content);
            like = view.findViewById(R.id.like);
            //Log.e("--->>>", "ViewHolder: "+like.toString(), null);
        }
    }

}