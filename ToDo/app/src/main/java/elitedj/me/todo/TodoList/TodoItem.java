package elitedj.me.todo.TodoList;

/**
 * 代办事件类
 */
public class TodoItem {

    private String todoName;   // 代办事件名
    private int imageId;       // 图片id

    public TodoItem(String todoName, int imageId) {
        this.todoName = todoName;
        this.imageId = imageId;
    }

    public String getTodoName() {
        return todoName;
    }

    public int getImageId() {
        return imageId;
    }
}
