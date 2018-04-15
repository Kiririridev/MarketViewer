package com.kiririri.marketviewer;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;



/**
 * @author Bartlomiej Kirejczyk
 *
 *Class creates new thread that tries to get JSONObject from given URL. It creates HttpsURLConnection,
 * sets it's parameters and establishes connection.
 * If receives response code from https server. If response if "201 Created" it opens InputStream
 * that retrieves string containing JSONObject. Then it creates new JSONObject from it,
 * which can be accessed with getJSONObject() method.
 *
 */
public class RetrieveJSONTask extends AsyncTask
{
    private String urlString;
    private JSONObject object = null;


    /**
     * Contructor that gets url in String format in argument.
     * Also calls supper class constructor.
     *
     * @param urlString
     */
    RetrieveJSONTask(String urlString)
    {
        super();
        this.urlString = urlString;

    }


    /**
     * doInBackground() is method done in new thread on calling .execute() method in main thread.
     * It creates HttpsURLConnection, sets it's parameters and establishes connection.
     * If receives response code from https server. If response if "201 Created" it opens InputStream
     * and converts it to string. Then new JSONObject is created from the string.
     * It is placed in class variable, which can be accessed with getJSONObject() method.
     *
     * @param objects
     * @return
     */
    @Override
    protected Object doInBackground(Object[] objects)
    {
        String content;
        try
        {

            URL url = new URL(this.urlString);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setUseCaches(false);
            httpsURLConnection.setConnectTimeout(10000);
            httpsURLConnection.setReadTimeout(10000);
            httpsURLConnection.setAllowUserInteraction(false);
            httpsURLConnection.setInstanceFollowRedirects(true);
            httpsURLConnection.connect();

            int status = httpsURLConnection.getResponseCode();
            //Log.i("ResponseCode", Integer.toString(status));


            switch(status)
            {
                case 200:


                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = br.readLine()) != null)
                    {
                        sb.append(line);
                    }
                    br.close();
                    content = sb.toString();
                    object = new JSONObject(content);
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Returns JbSONOject retrieved from URL.
     * Returns null if doInBackground() method wasn't finished
     *
     * @return object
     */
    public JSONObject getJSONObject()
    {
        return object;
    }

}