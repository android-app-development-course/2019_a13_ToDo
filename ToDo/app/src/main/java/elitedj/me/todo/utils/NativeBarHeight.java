package elitedj.me.todo.utils;

import android.content.Context;
import android.content.res.Resources;

public class NativeBarHeight {

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public int getNativeBarHeight(Context context) {
        Resources resource = context.getResources();
        int result = 0;
        int resourceId = resource.getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = resource.getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
