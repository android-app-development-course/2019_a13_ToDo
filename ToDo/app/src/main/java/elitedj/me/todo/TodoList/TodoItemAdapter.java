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

import java.util.List;

import elitedj.me.todo.R;

/**
 * 给RecyclerView提供的适配器
 */
public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder> {

    private Context mContext;
    private List<TodoItem> todoItemList;   // 代办事件列表

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView todoname;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            imageView = (ImageView) view.findViewById(R.id.todo_image);
            todoname = (TextView) view.findViewById(R.id.todo_name);
        }
    }

    public TodoItemAdapter(List<TodoItem> todoItemList) {
        this.todoItemList = todoItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mContext == null) {
            mContext = viewGroup.getContext();
        }
        // 加载子项
        View view = LayoutInflater.from(mContext).inflate(R.layout.todo_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // 获取todo子项
        TodoItem todoItem = todoItemList.get(i);
        viewHolder.todoname.setText(todoItem.getTodoName());
        Glide.with(mContext).load(todoItem.getImageId()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return todoItemList.size() ;
    }
}
