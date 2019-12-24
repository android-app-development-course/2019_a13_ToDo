package elitedj.me.todo.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.varunest.sparkbutton.SparkButton;

import java.util.ArrayList;

import elitedj.me.todo.R;

public class MomentListAdapter extends RecyclerView.Adapter<MomentListAdapter.ViewHolder> {

    private ArrayList<Moment> moments;
    private LayoutInflater layoutInflater;
    private Context context;

    public MomentListAdapter(Context context, ArrayList<Moment> moments, LayoutInflater layoutInflater){
        super();
        this.context = context;
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
        Glide.with(context)
                .load(moment.getFace())
                .asBitmap()
                .into(holder.face);
        holder.friendname.setText(moment.getName());
        holder.content.setText(moment.getContent());
        holder.like.setChecked(false);

        ArrayList<Picture> pictures = moment.getPictures();
        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
        for(Picture p : pictures) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setBigImageUrl(p.getUri());
            imageInfo.setThumbnailUrl(p.getUri());
            imageInfos.add(imageInfo);
        }
        holder.nineGridView.setAdapter(new NineGridViewClickAdapter(context, imageInfos));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView face;
        private TextView friendname;
        private TextView content;
        private SparkButton like;
        private NineGridView nineGridView;

        public ViewHolder(View view){
            super(view);
            face = view.findViewById(R.id.face);
            friendname = view.findViewById(R.id.friendname);
            content = view.findViewById(R.id.content);
            like = view.findViewById(R.id.like);
            nineGridView = view.findViewById(R.id.ninePic);
            //Log.e("--->>>", "ViewHolder: "+like.toString(), null);
        }
    }

}
