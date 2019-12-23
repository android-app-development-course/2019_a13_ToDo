package elitedj.me.todo.TodoList;

/**
 * 代办事件类
 */
public class TodoItem {

    private int dayOfWeek;   // 星期几
    private String title;    // 标题
    private String content;  // 内容
    private String icon;     // 图标
    private long timeStamp;  // 时间戳
    private int state;       // 状态
    private int priority;    // 优先级

    public TodoItem() {

    }

    public TodoItem(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public TodoItem(int dayOfWeek, String title, String content, String icon, long timeStamp, int state, int priority) {
        this.dayOfWeek = dayOfWeek;
        this.title = title;
        this.content = content;
        this.icon = icon;
        this.timeStamp = timeStamp;
        this.state = state;
        this.priority = priority;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getIcon() {
        return icon;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getState() {
        return state;
    }

    public int getPriority() {
        return priority;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof TodoItem)) return false;
        TodoItem o = (TodoItem) obj;


        if ((o.title == title || o.title.equals(title))
                && (o.content == content || o.content.equals(content))
                && o.state == state
                && (o.icon == icon || o.icon.equals(icon))
//                && o.timeStamp == timeStamp
                && o.dayOfWeek == dayOfWeek
                && o.priority == priority)
            return true;
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        TodoItem entity = (TodoItem) super.clone();
        entity.dayOfWeek = dayOfWeek;
        entity.title = title;
        entity.content = content;
        entity.icon = icon;
        entity.timeStamp = timeStamp;
        entity.state = state;
        entity.priority = priority;
        return entity;
    }

    public TodoItem cloneObj() {
        TodoItem entity = new TodoItem(dayOfWeek);
        entity.title = title;
        entity.content = content;
        entity.icon = icon;
        entity.timeStamp = timeStamp;
        entity.state = state;
        entity.priority = priority;
        return entity;
    }


    public void setTaskDetailEntity(TodoItem e) {
        this.title = e.title;
        this.content = e.content;
        this.icon = e.icon;
        this.timeStamp = e.timeStamp;
        this.state = e.state;
        this.priority = e.priority;
        this.dayOfWeek = e.dayOfWeek;
    }


    @Override
    public String toString() {
        return "TodoItem{" +
                "dayOfWeek=" + dayOfWeek +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", icon='" + icon + '\'' +
                ", timeStamp=" + timeStamp +
                ", state=" + state +
                ", priority=" + priority +
                '}';
    }
}
