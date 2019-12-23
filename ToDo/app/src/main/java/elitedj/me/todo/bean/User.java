package elitedj.me.todo.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
