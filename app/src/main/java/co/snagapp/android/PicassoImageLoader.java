package co.snagapp.android;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import co.snagapp.android.worker.ImageLoader;

/**
 * Created by zola on 2015/07/28.
 */
public class PicassoImageLoader implements ImageLoader {
    @Override
    public void loadImageIntoView(Context context, String imgUrl, ImageView imageView) {
        Picasso.with(context)
                .load(imgUrl).placeholder(ImageLoader.PLACEHOLDER)
                .into(imageView);
    }
}
