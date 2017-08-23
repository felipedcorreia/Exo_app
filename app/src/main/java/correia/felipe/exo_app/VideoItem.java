package correia.felipe.exo_app;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe on 15/08/2017.
 */

//public class VideoItem extends RecyclerView.Adapter<VideoItem.SimpleViewHolder>, BaseAdapter{
  public class VideoItem extends  BaseAdapter{

    private Context context;
    private List<String> elements;
    private ArrayList<String> images;
    private ArrayList<String> title;

    public VideoItem(Context context, ArrayList<String> images, ArrayList<String> titles){
        this.context = context;
        this.elements = new ArrayList<String>();
        this.title = title;
        // Fill dummy list
        for(int i = 0; i < 10 ; i++){
            this.elements.add(i, "Position : " + i);
        }
    }

    /*public static class SimpleViewHolder extends RecyclerView.ViewHolder {
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
    public int getItemCount() {
        return this.elements.size();
    }
    */

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }



    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    public class ReadJSONFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }

        protected void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONObject videoObj = new JSONObject(jsonObject.getString("dependence"));
                //Toast.makeText(getBaseContext())

            }catch (Exception e){
                Log.d("ReadJSONFeedTask", e.getLocalizedMessage());
            }
        }
    }
}
