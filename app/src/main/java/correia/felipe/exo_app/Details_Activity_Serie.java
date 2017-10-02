package correia.felipe.exo_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Felipe on 25/09/2017.
 */

public class Details_Activity_Serie extends ActionBarActivity {
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_serie);
        String id = getIntent().getStringExtra("id");
        Log.d("VIDEO", "ID = " + id);
        String token = getIntent().getStringExtra("token");
        Log.d("VIDEO", "TOKEN = " + token);


        WebView wv = (WebView)findViewById(R.id.webview);

        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);
        wv.setWebViewClient(new WebViewClient());


        CookieManager.getInstance().setCookie("http://blessp.azurewebsites.net/browser/", "tk="+token);
        CookieManager.getInstance().setAcceptCookie(true);
        wv.loadUrl("http://blessp.azurewebsites.net/browser/"+id);

        /*CookieManager.getInstance().setCookie("http://192.168.0.14:8000/browser/"+id, "tk="+token);
        wv.loadUrl("http://192.168.0.14:8000/browser/"+id);*/

    }


}

        /*ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("cover");
        String id = getIntent().getStringExtra("id");
        String description = getIntent().getStringExtra("description");
        titleTextView = (TextView) findViewById(R.id.details_title);

        descriptionTextView = (TextView) findViewById(R.id.details_description);

        imageView = (SmartImageView) findViewById(R.id.details_image);
        Log.d("Details Activity", "String image: " + image);
        titleTextView.setText(Html.fromHtml(title));
        Log.d("Details Activity", "String title: " + title);
        descriptionTextView.setText(description);
        Log.d("Details Activity", "String description: " + description);
//        Picasso.with(this).load(image).into(imageView);
        Picasso.with(this).load(String.valueOf(image)).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent video = new Intent(Details_Activity_Serie.this, Activity_Video.class);
                video.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(video);
            }
        });

    }*/
