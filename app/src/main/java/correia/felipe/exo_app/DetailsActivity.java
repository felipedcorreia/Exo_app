package correia.felipe.exo_app;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Felipe on 30/08/2017.
 */

public class DetailsActivity extends ActionBarActivity {
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        String description = getIntent().getStringExtra("description");
        titleTextView = (TextView) findViewById(R.id.title);
        descriptionTextView = (TextView) findViewById(R.id.description);
        imageView = (ImageView) findViewById(R.id.video_item_image);
        titleTextView.setText(Html.fromHtml(title));
        descriptionTextView.setText(description);
        Picasso.with(this).load(image).into(imageView);
    }
}
