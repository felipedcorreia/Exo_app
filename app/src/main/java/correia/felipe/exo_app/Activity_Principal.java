package correia.felipe.exo_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.image.SmartImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Felipe on 18/08/2017.
 */



public class Activity_Principal extends AppCompatActivity {
    private static final String TAG = Activity_Principal.class.getSimpleName();

    private GridView mGridView, mGridView2, mGridView3, mGridView4, mGridView5;
    private ProgressBar mProgressBar;
    private TextView txt_key_0,txt_key_1, txt_key_2, txt_key_3;

    private GridViewAdapter mGridAdapter, mGridAdapter2, mGridAdapter3, mGridAdapter4;
    private ArrayList<VideoItem> mGridData, mGridData2,mGridData3,mGridData4;
    private GridViewAdapterTop mGridAdapterTop;
    private ArrayList<TopItem> mGridDataTop;
    private ImageView thumb_principal;
    private String FEED_URL = "http://blessplay.com.br/api/dependence";
    //private String FEED_URL = "http://javatechig.com/?json=get_recent_posts&count=45";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_principal);

        mGridView = (GridView)findViewById(R.id.gridView);
        mGridView2 = (GridView)findViewById(R.id.gridView_2);
        mGridView3 = (GridView)findViewById(R.id.gridView_3);
        mGridView4 = (GridView)findViewById(R.id.gridView_4);
        mGridView5 = (GridView)findViewById(R.id.gridView_5);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);


        txt_key_0 = (TextView)findViewById(R.id.textView_Key0);
        txt_key_1 = (TextView)findViewById(R.id.textView_Key1);
        txt_key_2 = (TextView)findViewById(R.id.textView_Key2);
        txt_key_3 = (TextView)findViewById(R.id.textView_Key3);





        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridData2 = new ArrayList<>();
        mGridData3 = new ArrayList<>();
        mGridData4 = new ArrayList<>();
        mGridDataTop = new ArrayList<>();

        mGridView.setNumColumns(8);
        mGridView2.setNumColumns(8);
        mGridView3.setNumColumns(8);
        mGridView4.setNumColumns(8);
        mGridView5.setNumColumns(1);

        mGridAdapter = new GridViewAdapter(this, R.layout.video_item, mGridData);
        mGridView.setAdapter(mGridAdapter);

        mGridAdapter2 = new GridViewAdapter(this, R.layout.video_item, mGridData2);
        mGridView2.setAdapter(mGridAdapter2);

        mGridAdapter3 = new GridViewAdapter(this, R.layout.video_item, mGridData3);
        mGridView3.setAdapter(mGridAdapter3);

        mGridAdapter4 = new GridViewAdapter(this, R.layout.video_item, mGridData4);
        mGridView4.setAdapter(mGridAdapter4);

        mGridAdapterTop = new GridViewAdapterTop(this, R.layout.top_item, mGridDataTop);
        mGridView5.setAdapter(mGridAdapterTop);

        //Grid view click event


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Get item at position
                VideoItem item = (VideoItem) parent.getItemAtPosition(position);


                Intent intent = new Intent(Activity_Principal.this, DetailsActivity.class);
                //ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);
                SmartImageView imageView = (SmartImageView) v.findViewById(R.id.video_item_image);

                // Interesting data to pass across are the thumbnail size/location, the
                // resourceId of the source bitmap, the picture description, and the
                // orientation (to avoid returning back to an obsolete configuration if
                // the device rotates again in the meantime)

                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);

                //Pass the image title and url to DetailsActivity
                intent.putExtra("left", screenLocation[0]).
                        putExtra("top", screenLocation[1]).
                        putExtra("width", imageView.getWidth()).
                        putExtra("height", imageView.getHeight()).
                        putExtra("title", item.getTitle()).
                        putExtra("image", item.getImage());

                //Start details activity
                startActivity(intent);
            }
        });


        //Start download
        new AsyncHttpTask().execute(FEED_URL);
        mProgressBar.setVisibility(View.VISIBLE);
    }


    //Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            //Integer result;
            Integer statusCode = 0;
            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);
                httpGet.setHeader("Authorization",
                        "Bearer                       eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6Ly9ibGVzc3BsYXkuY29tLmJyL2FwaS9sb2dpbiIsImlhdCI6MTUwNTc0NjgzNSwiZXhwIjoxNTA1ODIzNjM1LCJuYmYiOjE1MDU3NDY4MzUsImp0aSI6IkdnRWhVWXA2bU5zSnZEZTkifQ.fA6qQEo7cqtVVKXNBBJxJFZ-1PTzrL4IkLq1OMmlbhw");
                HttpResponse httpResponse = httpclient.execute(httpGet);
                //HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));

                String response = streamToString(httpResponse.getEntity().getContent());
                Log.d("doInBackground", "Response: " + response);
                makeTitle(response);
                parseResult(response);

                statusCode = httpResponse.getStatusLine().getStatusCode();

                /*
                // 200 represents HTTP OK
                if (statusCode == 200) {
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = statusCode; // Successful
                } else {
                    result = 0; //"Failed
                }*/
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return statusCode;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 200) {
                Toast.makeText(Activity_Principal.this, "Status Code: "+result, Toast.LENGTH_SHORT).show();
                mGridAdapter.setGridData(mGridData);
                mGridAdapter2.setGridData(mGridData2);
                mGridAdapter3.setGridData(mGridData3);
                mGridAdapter4.setGridData(mGridData4);
                mGridAdapterTop.setGridData(mGridDataTop);
            } else {
                Toast.makeText(Activity_Principal.this, "Status Code: "+result, Toast.LENGTH_SHORT).show();
                //Toast.makeText(Activity_Principal.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

            //Hide progressbar
            mProgressBar.setVisibility(View.GONE);
        }
    }


    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (null != stream) {
            stream.close();
        }
        return result;
    }

    /**
     * Parsing the feed results and get the list
     *
     * @param result
     */
    //parser do json da API
    private void parseResult(String result) throws IOException {
        try {
            JSONObject response = new JSONObject(result);
            VideoItem item = null;
            TopItem topItem = null;
            Iterator<?> iterator = response.keys();
            Log.d("Parser", "PARSER RESULT ");
            int count = 0;
            while(iterator.hasNext()){
                String key = (String)iterator.next();
                Log.d("Parser", "JSON key: " + key);

                if(count == 0) {
                    Object obj = response.get(key);
                    if (obj instanceof JSONArray) {
                        JSONObject jsonObject = (JSONObject) obj;
                        String title = jsonObject.getString("title");
                        Log.d("Parser", "JSON Title: " + title);
                        String thumb = jsonObject.getString("thumb");
                        Log.d("Parser", "JSON Thumb: " + thumb);
                        topItem = new TopItem();
                        topItem.setTitle(title);
                        topItem.setImage(thumb);


                        mGridDataTop.add(topItem);
                    }
                }else if(count == 1) {
                    Object o = response.get(key);
                    if (o instanceof JSONArray) {
                        JSONArray jsonA = (JSONArray) o;
                        Log.d("Parser", "JSON Array: " + jsonA);
                        int numberOfItems = jsonA.length();
                        for (int i = 0; i < numberOfItems; i++) {

                            JSONObject jsonO = jsonA.optJSONObject(i);
                            Log.d("Parser", "JSON Object: " + jsonO);

                            String title = jsonO.getString("title");
                            Log.d("Parser", "JSON Title: " + title);
                            String thumb = jsonO.getString("thumb");
                            Log.d("Parser", "JSON Thumb: " + thumb);
                            String description = jsonO.getString("description");
                            Log.d("Parser", "JSON Thumb: " + description);


                            item = new VideoItem();
                            item.setTitle(title);
                            //if (thumb_bmp != null) {
                            item.setImage(thumb);
                            //}
                            item.setDescription(description);

                            mGridData.add(item);
//                        }
                        }
                    }
                } else if(count == 2) {
                    Object o = response.get(key);
                    if (o instanceof JSONArray) {
                        JSONArray jsonA = (JSONArray) o;
                        Log.d("Parser", "JSON Array: " + jsonA);
                        int numberOfItems = jsonA.length();
                        for (int i = 0; i < numberOfItems; i++) {

                            JSONObject jsonO = jsonA.optJSONObject(i);
                            Log.d("Parser", "JSON Object: " + jsonO);
                            //if (jsonO == null) {
                            String title = jsonO.getString("title");
                            Log.d("Parser", "JSON Title: " + title);
                            String thumb = jsonO.getString("thumb");
                            Log.d("Parser", "JSON Thumb: " + thumb);
                            String description = jsonO.getString("description");
                            Log.d("Parser", "JSON Thumb: " + description);

                            URL url = new URL(thumb);
                            Bitmap thumb_bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                            item = new VideoItem();
                            item.setTitle(title);
                            //if (thumb_bmp != null) {
                            item.setImage(thumb);
                            //}
                            item.setDescription(description);

                            mGridData2.add(item);
//                        }
                        }
                    }
                } else if(count == 3) {
                    Object o = response.get(key);
                    if (o instanceof JSONArray) {
                        JSONArray jsonA = (JSONArray) o;
                        Log.d("Parser", "JSON Array: " + jsonA);
                        int numberOfItems = jsonA.length();
                        for (int i = 0; i < numberOfItems; i++) {

                            JSONObject jsonO = jsonA.optJSONObject(i);
                            Log.d("Parser", "JSON Object: " + jsonO);
                            //if (jsonO == null) {
                            String title = jsonO.getString("title");
                            Log.d("Parser", "JSON Title: " + title);
                            String thumb = jsonO.getString("thumb");
                            Log.d("Parser", "JSON Thumb: " + thumb);
                            String description = jsonO.getString("description");
                            Log.d("Parser", "JSON Thumb: " + description);

                            /*URL url = new URL(thumb);
                            Bitmap thumb_bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());*/

                            item = new VideoItem();
                            item.setTitle(title);
                            //if (thumb_bmp != null) {
                            item.setImage(thumb);
                            //}
                            item.setDescription(description);

                            mGridData3.add(item);
//                        }
                        }
                    }
                }else if(count == 4) {
                    Object o = response.get(key);
                    if (o instanceof JSONArray) {
                        JSONArray jsonA = (JSONArray) o;
                        Log.d("Parser", "JSON Array: " + jsonA);
                        int numberOfItems = jsonA.length();
                        for (int i = 0; i < numberOfItems; i++) {

                            JSONObject jsonO = jsonA.optJSONObject(i);
                            Log.d("Parser", "JSON Object: " + jsonO);
                            //if (jsonO == null) {
                            String title = jsonO.getString("title");
                            Log.d("Parser", "JSON Title: " + title);
                            String thumb = jsonO.getString("thumb");
                            Log.d("Parser", "JSON Thumb: " + thumb);
                            String description = jsonO.getString("description");
                            Log.d("Parser", "JSON Thumb: " + description);

                            /*URL url = new URL(thumb);
                            Bitmap thumb_bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());*/

                            item = new VideoItem();
                            item.setTitle(title);
                            //if (thumb_bmp != null) {
                            item.setImage(thumb);
                            //}
                            item.setDescription(description);

                            mGridData4.add(item);
//                        }
                        }
                    }
                }
                count++;
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void makeTitle(final String result) throws IOException {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject response = new JSONObject(result);
                    Iterator<?> iterator = response.keys();
                    Log.d("Parser", "MAKE TITLE ");
                    int count = 0;
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        Log.d("Parser", "JSON Title key: " + key);
                        Log.d("Parser", "count: " + count);
                        if(count ==1){
                            txt_key_0.setText(key);
                        }else if(count == 2){
                            txt_key_1.setText(key);
                        }else if(count == 3){
                            txt_key_2.setText(key);
                        }else if(count == 4){
                            txt_key_3.setText(key);
                        }
                        count++;
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }


            }
        });


    }

    //Parse do json do exemplo


    /*private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            VideoItem item;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("title");
                item = new VideoItem();
                item.setTitle(title);
                JSONArray attachments = post.getJSONArray("attachments");
                if (null != attachments && attachments.length() > 0) {
                    JSONObject attachment = attachments.getJSONObject(0);
                    if (attachment != null)
                        item.setImage(attachment.getString("url"));
                }
                mGridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //possivel solução

    private void parseResult(String result) throws IOException {
        try {
            Toast.makeText(Activity_Principal.this, "kkk", Toast.LENGTH_SHORT).show();
            JSONArray jsonArray = new JSONArray(result);
            VideoItem item;
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                JSONArray lancamento = object.getJSONArray("lançamento");
                for(int j=0;j<lancamento.length();j++){
                    JSONObject lancamento_type = lancamento.getJSONObject(j);
                    String title = lancamento_type.getString("title");
                    String thumb = lancamento_type.getString("thumb");
                    URL url = new URL(thumb);
                    Bitmap thumb_bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    item = new VideoItem();
                    item.setTitle(title);
                    if(thumb_bmp != null){
                        item.setImage(thumb_bmp);
                    }
                    mGridData.add(item);
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    */
}
