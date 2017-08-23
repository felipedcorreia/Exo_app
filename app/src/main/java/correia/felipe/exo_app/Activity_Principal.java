package correia.felipe.exo_app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    //TAGS
    //Web api url
    public static final String DATA_URL = "http://8ecfbaac.ngrok.io/api/json";

    //Values
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_TITLE = "title";


    private HorizontalGridView horizontalGridView, horizontalGridView_2, horizontalGridView_3;
    ArrayList<String> images;
    ArrayList<String> title;
    ArrayList<String> description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_principal);
        /*gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new GridAdapter());*/



        horizontalGridView = (HorizontalGridView) findViewById(R.id.gridView);

        images = new ArrayList<>();
        title = new ArrayList<>();
        description = new ArrayList<>();

        getData();


        /*
        VideoItem adapter = new VideoItem(this);
        horizontalGridView.setAdapter(adapter);


        horizontalGridView_2 = (HorizontalGridView) findViewById(R.id.gridView2);
        VideoItem adapter_2 = new VideoItem(this);

        horizontalGridView_2.setAdapter(adapter_2);

        horizontalGridView_3 = (HorizontalGridView) findViewById(R.id.gridView3);
        VideoItem adapter_3 = new VideoItem(this);

        horizontalGridView_3.setAdapter(adapter_3);
        */



    }

    private void getData(){
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait..","Feathing data...", false, false);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();

                showGrid(response);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    private void showGrid(JSONArray jsonArray){
        //Looping through all the elements of json array
        for(int i = 0; i<jsonArray.length(); i++){
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url and title from json object
                images.add(obj.getString(TAG_IMAGE_URL));
                title.add(obj.getString(TAG_TITLE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Creating GridViewAdapter Object
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this,images,title);

        //Adding adapter to gridview
        horizontalGridView.setAdapter(gridViewAdapter);
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

        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject videoObj = new JSONObject(jsonObject.getString("dependence"));
                //Toast.makeText(getBaseContext())

            } catch (Exception e) {
                Log.d("ReadJSONFeedTask", e.getLocalizedMessage());
            }
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
                TextView titleview;

                titleview =(TextView)findViewById(R.id.text_video);
                new ReadJSONFeedTask().execute("http://8ecfbaac.ngrok.io/api/json" + titleview.getText().toString());


                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 440));
                imageView.setImageResource(integersRes.get(i));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        }
    }
}
