package correia.felipe.exo_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by Felipe on 23/08/2017.
 */

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.SimpleViewHolder> {

    //Imageloader to load images
    private ImageLoader imageLoader;

    //Context
    private Context context;

    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> images;
    private ArrayList<String> title;
    private ArrayList<String> description;
    private ArrayList<String> duration;
    private ArrayList<String> year;
    private ArrayList<String> serie;

    public GridViewAdapter (Context context, ArrayList<String> images, ArrayList<String> title){
        //Getting all the values
        this.context = context;
        this.images = images;
        this.title = title;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.video_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        //holder.button.setText(elements.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Position =" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.images.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView button;

        public SimpleViewHolder(View view) {
            super(view);
            button = (ImageView) view.findViewById(R.id.button);
        }
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        //Creating a linear layout
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        //NetworkImageView
        NetworkImageView networkImageView = new NetworkImageView(context);

        //Initializing ImageLoader
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(images.get(position), ImageLoader.getImageListener(networkImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        //Setting the image url to load
        networkImageView.setImageUrl(images.get(position),imageLoader);

        //Creating a textview to show the title
        TextView textView = new TextView(context);
        textView.setText(title.get(position));

        //Scaling the imageview
        networkImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        networkImageView.setLayoutParams(new GridView.LayoutParams(200,200));

        //Adding views to the layout
        linearLayout.addView(textView);
        linearLayout.addView(networkImageView);


        //Returnint the layout
        return linearLayout;
    }
}
