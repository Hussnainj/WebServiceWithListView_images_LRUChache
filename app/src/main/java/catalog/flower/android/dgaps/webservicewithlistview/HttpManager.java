package catalog.flower.android.dgaps.webservicewithlistview;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wasim on 12/8/2016.
 */

public class HttpManager {

   /*

   /// AndroidHttpClient by Appache

   public  static  String getData(String uri)
    {
        AndroidHttpClient client = AndroidHttpClient.newInstance("AndroidUser");

        HttpGet request = new HttpGet(uri);
        HttpResponse response;

        try{

            response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
        catch (Exception e)
        {

            return null;
        }
        finally {
            client.close();
        }



    }*/

    public  static  String getData(String uri) {
        BufferedReader reader = null;
        try {

            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            while ((line= reader.readLine())!=null)
            {
                sb.append(line+"\n");

            }
            return  sb.toString();

        } catch (Exception e) {
          return  null;
        }

           finally {

            if(reader!=null)
            {

                try {

                  reader.close();
                }
                catch (Exception e)
                {
                   return  null;
                }
            }
        }


    }


    public  static  String getData(String uri,String username, String password) {
        BufferedReader reader = null;

        byte [] loginBytes= (username +":"+password).getBytes();
        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginBytes,Base64.DEFAULT));






        try {

            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.addRequestProperty("Authorization",loginBuilder.toString());

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            while ((line= reader.readLine())!=null)
            {
                sb.append(line+"\n");

            }
            return  sb.toString();

        } catch (Exception e) {
            return  null;
        }

        finally {

            if(reader!=null)
            {

                try {

                    reader.close();
                }
                catch (Exception e)
                {
                    return  null;
                }
            }
        }


    }
}
