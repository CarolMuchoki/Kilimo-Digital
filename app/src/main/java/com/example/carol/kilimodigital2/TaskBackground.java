package com.example.carol.kilimodigital2;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Carol on 3/2/2018.
 */

public class TaskBackground extends AsyncTask<String,String,String> {
    HttpURLConnection conn;
    URL url = null;
    Context ctx;

    public TaskBackground(Context ctx)
    {
        this.ctx=ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            url = new URL("http://192.168.43.248:80/wit/signup.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "exception";

        }

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoOutput(true);
            Uri.Builder builder = new Uri.Builder().appendQueryParameter("username", params[0]).appendQueryParameter("phone_number", params[0]);
            String query = builder.build().getEncodedQuery();
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            os.close();
            conn.connect();
        } catch (IOException e) {
            String k = e.toString();
            return k;
        }
        try {
            int response_code = conn.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK) {
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);

                }
                return (result.toString());
            } else {
                return ("Unsuccessful");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "exception";
        } finally {
            conn.disconnect();
            return null;
        }

    }
       @Override
        protected void onPostExecute (String result)
       {

                Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();


      }
}


