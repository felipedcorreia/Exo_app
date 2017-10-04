package correia.felipe.exo_app;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Felipe on 18/09/2017.
 */

public class GridViewAdapterTop extends ArrayAdapter<TopItem> {

    //private final ColorMatrixColorFilter grayscaleFilter;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<TopItem> mGridData = new ArrayList<TopItem>();

    public GridViewAdapterTop(Context mContext, int layoutResourceId, ArrayList<TopItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }

    public void setGridData(ArrayList<TopItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GridViewAdapter.ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new GridViewAdapter.ViewHolder();
            //holder.titleTextView = (TextView) row.findViewById(R.id.videoTop_item_title);
            holder.imageView = (SmartImageView) row.findViewById(R.id.videoTop_item_image);
            row.setTag(holder);
        } else {
            holder = (GridViewAdapter.ViewHolder) row.getTag();
        }

        TopItem item = mGridData.get(position);
       // holder.titleTextView.setText(Html.fromHtml(item.getTitle()));

        Picasso.with(mContext).load(String.valueOf(item.getImage())).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        TextView titleTextView;
        SmartImageView imageView;
    }

}
