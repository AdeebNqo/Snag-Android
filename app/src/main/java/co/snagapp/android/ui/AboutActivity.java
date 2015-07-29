package co.snagapp.android.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import co.snagapp.android.PicassoImageLoader;
import co.snagapp.android.R;
import co.snagapp.android.worker.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;

public class AboutActivity extends ActionBarActivity {

    private String gravatarUrl;
    private ImageView circleImageView;
    private ImageLoader imageLoader;
    private Toolbar toolbar;
    private HtmlTextView aboutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_activity_about));

        imageLoader = new PicassoImageLoader();
        circleImageView = (ImageView) findViewById(R.id.profile_image);
        gravatarUrl = getString(R.string.gravatar_url);
        imageLoader.loadImageIntoView(this, gravatarUrl, circleImageView);

        aboutContent = (HtmlTextView) findViewById(R.id.html_text);
        aboutContent.setHtmlFromRawResource(getApplicationContext(), R.raw.about_text, new HtmlTextView.RemoteImageGetter());
    }

    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;

    }
}
