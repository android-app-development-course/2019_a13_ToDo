package elitedj.me.todo.TodoList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import elitedj.me.todo.R;
import elitedj.me.todo.utils.ImageFactory;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TodoItem> mList;
    private boolean showPriority = true;
    private final int[] mPriorityIcons;
    private final int[] icons;

    public boolean isShowPriority() {
        return showPriority;
    }
    public void setShowPriority(boolean showPriority) {
        this.showPriority = showPriority;
    }

    public TaskAdapter(Context mContext, ArrayList<TodoItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mPriorityIcons = ImageFactory.createPriorityIcons();
        icons = ImageFactory.createIcons();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ViewHolder h = (ViewHolder) viewHolder;
        TodoItem entity = mList.get(i);
        h.setView(entity);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvTitle;
        TextView mTvContent;
        ImageView mIvIcon;
        ImageView mIvCurrPriority;
        LinearLayout mLlTaskFinishedMask;
        TodoItem entity;

        public ViewHolder(View view){
            super(view);
            mTvTitle = (TextView) view.findViewById(R.id.tv_title);
            mTvContent = (TextView) view.findViewById(R.id.tv_content);
            mIvIcon = (ImageView) view.findViewById(R.id.sdv_icon);
            mIvCurrPriority = (ImageView) view.findViewById(R.id.iv_curr_priority);
            mLlTaskFinishedMask = (LinearLayout) view.findViewById(R.id.ll_task_finished_mask);
        }

        public void setView(TodoItem entity) {
            this.entity = entity;

            // Title
            mTvTitle.setText(entity.getTitle());

            // Content
            String content = entity.getContent();
            int length = content.length();
            String s = content.substring(0, Math.min(length, 28));
            if (length >= 28) s += "...";
            mTvContent.setText(s);

            // ICON
            int ids = (int)(Math.random()*8);
            mIvIcon.setImageResource(icons[ids]);
            mIvIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Priority
            if (showPriority) {
                if (!mIvCurrPriority.isShown())
                    mIvCurrPriority.setVisibility(View.VISIBLE);
                int priority = entity.getPriority();
                mIvCurrPriority.setImageResource(mPriorityIcons[priority]);
            } else {
                if (mIvCurrPriority.isShown())
                    mIvCurrPriority.setVisibility(View.INVISIBLE);
            }

            // IsFinished
            int state = entity.getState();
            if (state == TaskState.FINISHED)
                mLlTaskFinishedMask.setVisibility(View.VISIBLE);
            else
                mLlTaskFinishedMask.setVisibility(View.INVISIBLE);
        }
    }
}
