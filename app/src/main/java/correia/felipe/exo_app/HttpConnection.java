package correia.felipe.exo_app;

import com.google.android.exoplayer2.C;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Felipe on 09/08/2017.
 */

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpConnection {
    public static String getSetDataWeb(String url, String data){

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        String answer = "";

        try{
           // ArrayList<String> valores = new ArrayList<String>();
            //valores.add(new BasicNameValuePair("method", method));
            //valores.add(data);
            //valores.add(new BasicNameValuePair("json", data));


            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type","application/json");
            StringEntity postString = new StringEntity(data, "UTF-8");
            httpPost.setEntity(postString);

           // httpPost.setEntity(new UrlEncodedFormEntity(valores));
            HttpResponse resposta = httpClient.execute(httpPost);
            answer = EntityUtils.toString(resposta.getEntity());
        }
        catch(NullPointerException e){ e.printStackTrace(); }
        catch(ClientProtocolException e){ e.printStackTrace(); }
        catch(IOException e){ e.printStackTrace(); }

        return(answer);
    }

    private void getJson(final String url) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Object retorno = null;
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet request = new HttpGet();

                    request.setURI(new URI(url));

                    HttpResponse response = httpclient.execute(request);
                    InputStream content = response.getEntity().getContent();

                    Reader reader = new InputStreamReader(content);
                    Gson gson = new Gson();
                    retorno = gson.fromJson(reader, HashMap.class);
                    content.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}