package correia.felipe.exo_app;

import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * Created by Felipe on 18/08/2017.
 */

public class Activity_Principal extends AppCompatActivity {

    GridView gridView;
    ArrayList<Integer> integersRes = new ArrayList<>();

    {
        //Todo For to thumbnails

        integersRes.add(R.drawable.ic_video_default);
        integersRes.add(R.drawable.ic_video_default);
        integersRes.add(R.drawable.ic_video_default);

    }
    private HorizontalGridView horizontalGridView, horizontalGridView_2, horizontalGridView_3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_principal);
        /*gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new GridAdapter());*/

        horizontalGridView = (HorizontalGridView)findViewById(R.id.gridView);
        VideoItem adapter = new VideoItem(this);

        horizontalGridView.setAdapter(adapter);

        horizontalGridView_2 = (HorizontalGridView)findViewById(R.id.gridView2);
        VideoItem adapter_2 = new VideoItem(this);

        horizontalGridView_2.setAdapter(adapter_2);

        horizontalGridView_3 = (HorizontalGridView)findViewById(R.id.gridView3);
        VideoItem adapter_3 = new VideoItem(this);

        horizontalGridView_3.setAdapter(adapter_3);


    }



        /*GridLayout gl = new GridLayout(this);
        gl.setColumnCount(4);
        gl.setRowCount(4);

        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                GridLayout.Spec row = GridLayout.spec(i);
                GridLayout.Spec column = GridLayout.spec(j);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(row, column);

                //ToDo
                //set Video

                //  gl.addView(iv, lp);
            }

        }*/


    private class GridAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return integersRes.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView = new ImageView(getApplicationContext());

            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 440));
            imageView.setImageResource(integersRes.get(i));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }
    }
}
