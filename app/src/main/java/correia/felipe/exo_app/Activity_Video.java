package correia.felipe.exo_app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Felipe on 22/09/2017.
 */

public class Activity_Video extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        String id = getIntent().getStringExtra("id");
        Log.d("VIDEO", "ID = " + id);
        String token = getIntent().getStringExtra("token");
        Log.d("VIDEO", "TOKEN = " + token);


        WebView wv = (WebView)findViewById(R.id.webview);

        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);
        wv.setWebViewClient(new WebViewClient());

        //wv.loadUrl("http://www.google.com.br");
        CookieManager.getInstance().setCookie("http://blessp.azurewebsites.net/movie/"+id, "tk="+token);
        wv.loadUrl("http://blessp.azurewebsites.net/movie/"+id);
/*
        CookieManager.getInstance().setCookie("http://192.168.0.14:8000/movie/"+id, "tk="+token);
        wv.loadUrl("http://192.168.0.14:8000/movie/"+id);
*/
    }





}
