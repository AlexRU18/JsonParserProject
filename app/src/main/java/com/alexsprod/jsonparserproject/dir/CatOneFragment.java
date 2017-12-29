package com.alexsprod.jsonparserproject.dir;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alexsprod.jsonparserproject.HttpHandler;
import com.alexsprod.jsonparserproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class CatOneFragment extends Fragment {

    public ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_cat1, container, false);
        lv = (ListView) view.findViewById(R.id.list_cat1);
        new GetData().execute();
        return view;
    }

}

class GetData extends AsyncTask<Void, Void, Void> {

    private ArrayList<HashMap<String, String>> newslist = new ArrayList<>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText("Json Data is downloading", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String url = "http://legs-legs.ru/cat1json.php?json=cat1";
        String jsonStr = sh.makeServiceCall(url);
        newslist = new ArrayList<>();
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {

                JSONArray array = new JSONArray(jsonStr);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject c = null;
                    c = array.getJSONObject(i);
                    String id = c.getString("id");
                    String title_cat1 = c.getString("title_cat1");
                    String fulltext_cat1 = c.getString("fulltext_cat1");
                    // tmp hash map for single contact
                    HashMap<String, String> news_item = new HashMap<>();
                    news_item.put("id", id);
                    news_item.put("title_cat1", title_cat1);
                    news_item.put("fulltext_cat1", fulltext_cat1);
                    newslist.add(news_item);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                //CANNOT RESOLVE METHOD
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //CANNOT RESOLVE METHOD
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }

        } else {
            Log.e(TAG, "Couldn't get json from server.");
            //CANNOT RESOLVE METHOD
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //CANNOT RESOLVE METHOD
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        return null;
    }

    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        SimpleAdapter adapter = new SimpleAdapter(CatOneFragment.this, newslist,
                R.layout.item_cat1, new String[]{"title_cat1", "fulltext_cat1"},
                new int[]{R.id.title_cat1, R.id.fulltext_cat1});
        //CANNOT RESOLVE SYMBOL
        lv.setAdapter(adapter);
    }
}