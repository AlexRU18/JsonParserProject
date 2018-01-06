package com.alexsprod.jsonparserproject.Utils;

import android.util.Log;

import com.alexsprod.jsonparserproject.items.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static String TAG = "TAG";
    Item objItem;
    List<Item> listArray;

    public List<Item> getData(String url, String Title, String FullText, String ImageURL) {
        try {
            listArray = new ArrayList<Item>();

            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (url != null) {
                try {
                    JSONArray array = new JSONArray(jsonStr);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject c = null;
                        c = array.getJSONObject(i);

                        objItem = new Item();
                        String id = c.getString("id");
                        Log.d("ID is: ", id);
                        Log.d("Title on params is: ", Title);
                        String title_cat1 = c.getString(Title);
                        Log.d("Title is: ", title_cat1);
                        String fulltext_cat1 = c.getString(FullText);
                        Log.d("Text is: ", fulltext_cat1);
                        String imgURL_cat1 = c.getString(ImageURL);
                        Log.d("ImageURL is: ", imgURL_cat1);
                        objItem.setId(id);
                        objItem.setTitle(title_cat1);
                        objItem.setText(fulltext_cat1);
                        objItem.setLink(imgURL_cat1);
                        listArray.add(objItem);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listArray;
    }
}