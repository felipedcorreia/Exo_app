package correia.felipe.exo_app;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.loopj.android.image.SmartImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Felipe on 23/08/2017.
 */

public class GridViewAdapter extends ArrayAdapter<VideoItem> {

    //private final ColorMatrixColorFilter grayscaleFilter;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<VideoItem> mGridData = new ArrayList<VideoItem>();

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<VideoItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }


    /**
     * Updates grid data and refresh grid items.
     *
     * @param mGridData
     */
    public void setGridData(ArrayList<VideoItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) row.findViewById(R.id.video_item_title);
            holder.imageView = (SmartImageView) row.findViewById(R.id.video_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        VideoItem item = mGridData.get(position);
        holder.titleTextView.setText(Html.fromHtml(item.getTitle()));

        Picasso.with(mContext).load(String.valueOf(item.getImage())).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        TextView titleTextView;
        SmartImageView imageView;
    }
}