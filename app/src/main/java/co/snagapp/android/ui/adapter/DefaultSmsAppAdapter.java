package co.snagapp.android.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.snagapp.android.R;
import co.snagapp.android.worker.impl.SmsManager;

/**
 * Created on 2015/08/17.
 */
public class DefaultSmsAppAdapter extends ArrayAdapter<String> {

    private SmsManager smsManager;

    public DefaultSmsAppAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);

        if (smsManager == null){
            smsManager = new SmsManager(context);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.material_radio_button, null);
        }

        String p = getItem(position);

        if (p != null) {

            ImageView img =  (ImageView) v.findViewById(R.id.icon);
            TextView label = (TextView) v.findViewById(R.id.label);

            label.setText(p);
            Drawable icon = smsManager.getIcon(p);
            img.setImageDrawable(icon);

            // Asynchronous
            android.support.v7.graphics.Palette palette = android.support.v7.graphics.Palette.from(drawableToBitmap(icon)).generate();
            if (p != null) {
                v.setBackgroundColor(palette.getMutedColor(-1));
            }

        }

        return v;
    }

    //src http://stackoverflow.com/a/10600736/1984350
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
