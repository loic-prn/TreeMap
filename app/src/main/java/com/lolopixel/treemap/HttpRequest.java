package com.lolopixel.treemap;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HttpRequest extends AsyncTask<Object, Void, String> {
    private String host;
    private TreeAdapter ada;
    private ArrayList<Tree> trees;

    @Override
    protected String doInBackground(Object... objects) {
        BufferedReader input;
        String txt = "";
        this.host = (String)objects[0];
        this.ada = (TreeAdapter) objects[1];
        this.trees = (ArrayList<Tree>) objects[2];


        String flux = "";
        try{
            URL targetURL = new URL(this.host);
            HttpURLConnection connURL = (HttpURLConnection) targetURL.openConnection();
            if(connURL.getResponseCode() == HttpURLConnection.HTTP_OK){
                input = new BufferedReader(new InputStreamReader(connURL.getInputStream()));
                StringBuilder builder = new StringBuilder();
                while((txt = input.readLine()) != null){
                    builder.append(txt);
                }
                flux = builder.toString();
                input.close();
                JSONObject toParse = new JSONObject(flux);
                JSONArray records = toParse.optJSONArray("records");
                for(int i = 0; i < records.length(); i++){
                    JSONObject tmp = ((JSONObject)records.get(i)).optJSONObject("fields");
                    trees.add(new Tree(
                            tmp.optDouble("hauteurenm")*100.0,
                            tmp.optDouble("circonferenceencm"),
                            tmp.optString("stadedeveloppement"),
                            tmp.optString("genre"),
                            tmp.optString("adresse") + ", " + tmp.optString("arrondissement"),
                            (double)tmp.optJSONArray("geom_x_y").get(0),
                            (double)tmp.optJSONArray("geom_x_y").get(1),
                            tmp.optString("libellefrancais")
                    ));
                }
            }
            else{
                txt = "ERROR";
            }
        } catch (MalformedURLException e) {
            Log.d("AsyncTask", "BAD URL");
        } catch (IOException e) {
            Log.d("AsyncTask", "PAS DE CO");
        } catch (JSONException e) {
            Log.d("AsyncTask", "CAN'T PARSE JSON");
        }
        return txt;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        this.ada.notifyDataSetChanged();
    }
}
