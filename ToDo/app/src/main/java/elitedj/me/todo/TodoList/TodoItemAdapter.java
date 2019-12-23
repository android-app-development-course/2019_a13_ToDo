package elitedj.me.todo.TodoList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import elitedj.me.todo.R;
import elitedj.me.todo.utils.DateUtils;

/**
 * 给RecyclerView提供的适配器
 */
public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TodoItem> todoItemList;   // 代办事件列表
    private LayoutInflater layoutInflater;

    public TodoItemAdapter(Context context, ArrayList<TodoItem> todoItemList, LayoutInflater layoutInflater){
        super();
        this.mContext = context;
        this.todoItemList = todoItemList;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getItemCount(){
        return todoItemList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.todo_item, viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ViewHolder h = (ViewHolder) viewHolder;
        TodoItem entity = todoItemList.get(i);
        h.setView(entity); // 设置组件内容
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        // todo_item中的组件
        CardView cardView;
        TextView todotitle;
        TextView todocontent;
        TextView tododate;
        TextView todostate;
        TodoItem todoItem;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            todotitle = (TextView) view.findViewById(R.id.tv_title);
            todocontent = (TextView) view.findViewById(R.id.tv_content);
            todostate = (TextView) view.findViewById(R.id.tv_state);
            tododate = (TextView) view.findViewById(R.id.tv_date);
        }

        public void setView(TodoItem entity) {
            this.todoItem = entity;
            // 设置组件现实的内容
            todotitle.setText(entity.getTitle());
            todocontent.setText(entity.getContent());
            long timeStamp = entity.getTimeStamp();
            String date = DateUtils.formatDateWeek(timeStamp);
            tododate.setText(date);
            int state = entity.getState();
            String sState = (state == TaskState.FINISHED) ? "已完成" : "未完成";
            todostate.setText(sState);
        }
    }
}
