package elitedj.me.todo.discover;

import java.util.ArrayList;

public class Moment {
    private String face;
    private String name;
    private String content;
    private ArrayList<Picture> pictures;

    public void setFace(String face) {
        this.face = face;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPictures(ArrayList<Picture> pictures) {
        this.pictures = pictures;
    }

    public String getFace() {
        return face;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<Picture> getPictures() {
        return pictures;
    }
}
