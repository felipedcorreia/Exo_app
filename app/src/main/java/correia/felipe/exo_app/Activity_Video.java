package correia.felipe.exo_app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Felipe on 22/09/2017.
 */

public class Activity_Video extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        String id = getIntent().getStringExtra("id");

        WebView wv = (WebView)findViewById(R.id.webview);

        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);


        wv.loadUrl("http://www.google.com.br"+id);

    }





}
