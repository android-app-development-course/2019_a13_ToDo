package elitedj.me.todo.discover;

import java.io.Serializable;

public class Picture implements Serializable {
    private String uri;

    public Picture(String uri) {
        this.uri = uri;
    }

    void setUri(String uri) {
        this.uri = uri;
    }

    String getUri() {
        return uri;
    }

}
