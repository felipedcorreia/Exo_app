package correia.felipe.exo_app;

import android.app.Activity;
import android.os.Bundle;

import com.longtailvideo.jwplayer.JWPlayerFragment;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;

/**
 * Created by Felipe on 16/08/2017.
 */

public class FragmentExampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the Activity's content view.
        setContentView(R.layout.activity_fragment_example);

        // Get a handle to the JWPlayerFragment
        JWPlayerFragment fragment = (JWPlayerFragment) getFragmentManager().findFragmentById(R.id.playerFragment);

        // Get a handle to the JWPlayerView
        JWPlayerView playerView = fragment.getPlayer();


        // Create a PlaylistItem
        PlaylistItem video = new PlaylistItem("http://path/to/stream.mpd");

        // Load a stream into the player

        playerView.load(video);

    }
}
