package co.snagapp.android.worker;

import android.content.Context;
import android.widget.ImageView;

import co.snagapp.android.R;

/**
 * Created by zola on 2015/07/28.
 */
public interface ImageLoader {
    public static int PLACEHOLDER = R.drawable.ic_file_image_grey600_48dp;
    void loadImageIntoView(Context context, String imgUrl, ImageView imageView);
}
