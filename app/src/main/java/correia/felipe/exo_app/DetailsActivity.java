package correia.felipe.exo_app;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by Felipe on 30/08/2017.
 */

public class DetailsActivity extends ActionBarActivity {
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView durationTV;
    private TextView yearTV;
    private ImageView imageView;
    private Button bplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("cover");
        String id = getIntent().getStringExtra("id");
        String duration = getIntent().getStringExtra("duration");
        String year = getIntent().getStringExtra("year");
        String description = getIntent().getStringExtra("description");
        String token = getIntent().getStringExtra("token");
        Log.d("VIDEO", "TOKEN = " + token);

        titleTextView = (TextView) findViewById(R.id.details_title);

        descriptionTextView = (TextView) findViewById(R.id.details_description);

        durationTV = (TextView)findViewById(R.id.details_duration);

        yearTV = (TextView)findViewById(R.id.details_year);

        bplay = (Button)findViewById(R.id.details_bPlay);

        imageView = (SmartImageView) findViewById(R.id.details_image);
        Log.d("Details Activity", "String image: " + image);
        titleTextView.setText(Html.fromHtml(title));
        Log.d("Details Activity", "String title: " + title);
        descriptionTextView.setText(description);
        Log.d("Details Activity", "String description: " + description);
        durationTV.setText("Duração: " + duration);
        Log.d("Details Activity", "String duration: " + duration);
        yearTV.setText("Ano: " + year);
        Log.d("Details Activity", "String year: " + year);
//        Picasso.with(this).load(image).into(imageView);
        Picasso.with(this).load(String.valueOf(image)).into(imageView);

        bplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent video = new Intent(DetailsActivity.this, Activity_Video.class);
                video.putExtra("id", getIntent().getStringExtra("id")).putExtra("token", getIntent().getStringExtra("token"));
                startActivity(video);
            }
        });

    }
}
