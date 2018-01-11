package com.alexsprod.jsonparserproject.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alexsprod.jsonparserproject.Adapters.CatTwoAdapter;
import com.alexsprod.jsonparserproject.R;
import com.alexsprod.jsonparserproject.Utils.JsonParser;
import com.alexsprod.jsonparserproject.Utils.Utils;
import com.alexsprod.jsonparserproject.items.Item;

import java.util.List;

public class CatTwoFragment extends Fragment {
    private static final String URL = "http://legs-legs.ru/cat1json.php?json=cat2";
    List<Item> arrayList;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_cat2, container, false);

        lv = view.findViewById(R.id.list_cat2);
        if (Utils.isNetworkAvailable(this.getActivity())) {
            String Title = "title_cat2";
            String fullText = "fulltext_cat2";
            String image = "img_cat2";
            String doptext = "doptext_cat2";
            new CatTwoFragment.GetData().execute(URL, Title, fullText, image, doptext);
        } else {
            Toast.makeText(getActivity(), "No Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    class GetData extends AsyncTask<String, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            arrayList = new JsonParser().getData(params[0], params[1], params[2], params[3], params[4]);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (null != pDialog && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (null == arrayList || arrayList.size() == 0) {
                Toast.makeText(getActivity(), "No data found from web", Toast.LENGTH_LONG).show();
            } else {
                CatTwoAdapter objAdapter = new CatTwoAdapter(getActivity(),
                        R.layout.item_cat2, arrayList);
                lv.setAdapter(objAdapter);
            }
        }
    }
}