package correia.felipe.exo_app;

import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.longtailvideo.jwplayer.JWPlayerView;

/**
 * Created by Felipe on 15/08/2017.
 */

public class JWplayer extends AppCompatActivity {

    JWPlayerView playerView = (JWPlayerView) findViewById(R.id.playerView);
    //ViewGroup jwPlayerViewContainer = (ViewGroup) findViewById(R.id.jwPlayerContainer);
    //jwPlayerViewContainer.addView(playerView);

    @Override
    protected void onResume() {
        // Let JW Player know that the app has returned from the background
        super.onResume();
        playerView.onResume();
    }

    @Override
    protected void onPause() {
        // Let JW Player know that the app is going to the background
        playerView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // Let JW Player know that the app is being destroyed
        playerView.onDestroy();
        super.onDestroy();
    }
}
