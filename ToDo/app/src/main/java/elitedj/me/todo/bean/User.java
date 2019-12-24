package elitedj.me.todo.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {

    private String nickName;
    private BmobFile face;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BmobFile getFace() {
        return face;
    }

    public void setFace(BmobFile face) {
        this.face = face;
    }
}
