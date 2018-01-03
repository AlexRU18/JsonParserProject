package com.alexsprod.jsonparserproject.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alexsprod.jsonparserproject.NewsRowAdapter;
import com.alexsprod.jsonparserproject.R;
import com.alexsprod.jsonparserproject.Utils;
import com.alexsprod.jsonparserproject.items.Item;
import com.alexsprod.jsonparserproject.items.JsonParser;

import java.util.List;

public class CatOneFragment extends Fragment {
    private static final String URL1 = "http://legs-legs.ru/cat1json.php?json=cat1";
    private static final String URL = "[{\"id\":\"6\",\"title_cat1\":\"Футболист Кокорин пострелял" +
            " из пистолета на свадьбе в Осетии\u200D\",\"img_cat1\":\"http:\\/\\/legs-legs.ru\\/media\\/image\\/69ac" +
            "0d3fd5efda52bbd81de7f32c77488ddd9ae4.jpg\",\"fulltext_cat1\":\"<p>Нападающий санкт-петербургского " +
            "Зенита Александр Кокорин пострелял из пистолета во время посещения свадьбы в Осетии. Видео в <b>инстаграме<\\/b> " +
            "спортсмена.<\\/p>\\r\\n<p>Стрельбу в воздух Кокорин назвал дебютом.<\\/p>\\r\\n<p>Ранее сообщалось, ч" +
            "то Кокорин выложил в инстагреме фотографию с пистолетом, но позже удалил ее.<\\/p>\",\"doptext_cat1\":\"Любой текст\"}]";
    List<Item> arrayList;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_cat1, container, false);

        lv = (ListView) view.findViewById(R.id.list_cat1);
        //lv.setOnItemClickListener(this);

        if (Utils.isNetworkAvailable(this.getActivity())) {
            new GetData().execute(URL);
        } else {
            Toast.makeText(getActivity(), "No Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    class GetData extends AsyncTask<String, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Json Data is downloading...");  //Loading...
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            arrayList = new JsonParser().getData(params[0]);
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
                NewsRowAdapter objAdapter = new NewsRowAdapter(getActivity(),
                        R.layout.item_cat1, arrayList);
                lv.setAdapter(objAdapter);
            }
        }
    }
}