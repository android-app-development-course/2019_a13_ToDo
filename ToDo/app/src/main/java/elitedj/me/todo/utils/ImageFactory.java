package elitedj.me.todo.utils;

import java.util.Arrays;
import java.util.List;

import elitedj.me.todo.R;

/**
 * Created by ZDM on 2019/12/23 21:34.
 */
public class ImageFactory {
    private ImageFactory() {
    }

    private static final String[] ids = new String[]{"bg_autumn_tree_min.jpg", "bg_kites_min.png", "bg_lake_min.jpg", "bg_leaves_min.jpg",
            "bg_magnolia_trees_min.jpg", "bg_solda_min.jpg", "bg_tree_min.jpg", "bg_tulip_min.jpg"};

    public static List<String> createBgImgs() {
        return Arrays.asList(ids);
    }

    private static final int[] icons = new int[]{
            R.drawable.bg_autumn_tree_min,
            R.drawable.bg_kites_min,
            R.drawable.bg_lake_min,
            R.drawable.bg_leaves_min,
            R.drawable.bg_magnolia_trees_min,
            R.drawable.bg_solda_min,
            R.drawable.bg_tree_min,
            R.drawable.bg_tulip_min
    };

    private static final int[] priorityIcons = new int[]{
            R.drawable.ic_priority_1,
            R.drawable.ic_priority_2,
            R.drawable.ic_priority_3,
            R.drawable.ic_priority_4
    };

    public static int[] createPriorityIcons() {
        return priorityIcons;
    }
    public static int[] createIcons() {
        return icons;
    }
}
