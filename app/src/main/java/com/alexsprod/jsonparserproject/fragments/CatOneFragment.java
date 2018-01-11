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

import com.alexsprod.jsonparserproject.Adapters.CatOneAdapter;
import com.alexsprod.jsonparserproject.R;
import com.alexsprod.jsonparserproject.Utils.JsonParser;
import com.alexsprod.jsonparserproject.Utils.Utils;
import com.alexsprod.jsonparserproject.items.Item;

import java.util.List;

public class CatOneFragment extends Fragment {
    private static final String URL = "http://legs-legs.ru/cat1json.php?json=cat1";
    List<Item> arrayList;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_cat1, container, false);

        lv = view.findViewById(R.id.list_cat1);
        if (Utils.isNetworkAvailable(this.getActivity())) {
            String Title = "title_cat1";
            String image = "img_cat1";
            String fullText = "fulltext_cat1";
            String doptext = "doptext_cat1";
            new GetData().execute(URL, Title, fullText, image, doptext);
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
                CatOneAdapter objAdapter = new CatOneAdapter(getActivity(),
                        R.layout.item_cat1, arrayList);
                lv.setAdapter(objAdapter);
            }
        }
    }
}