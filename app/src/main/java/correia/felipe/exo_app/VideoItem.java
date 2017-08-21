package correia.felipe.exo_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe on 15/08/2017.
 */

public class VideoItem extends RecyclerView.Adapter<VideoItem.SimpleViewHolder>{

    private Context context;
    private List<String> elements;
    private TextView title;

    public VideoItem(Context context){
        this.context = context;
        this.elements = new ArrayList<String>();
        this.title = title;
        // Fill dummy list
        for(int i = 0; i < 10 ; i++){
            this.elements.add(i, "Position : " + i);
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView button;

        public SimpleViewHolder(View view) {
            super(view);
            button = (ImageView) view.findViewById(R.id.button);
        }
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.elements.size();
    }
}
