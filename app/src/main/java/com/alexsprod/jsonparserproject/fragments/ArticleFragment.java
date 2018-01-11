package com.alexsprod.jsonparserproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsprod.jsonparserproject.R;
import com.squareup.picasso.Picasso;

public class ArticleFragment extends Fragment {

    TextView title_article;
    TextView fulltext_article;
    ImageView img_article;
    String TAG = "ArticleFr";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_article, container, false);
        title_article = view.findViewById(R.id.title_article);
        fulltext_article = view.findViewById(R.id.fulltext_article);
        img_article = view.findViewById(R.id.img_article);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            String id = savedInstanceState.getString("ID");
            Log.d(TAG, "ID is: " + id);
            String title = savedInstanceState.getString("Title");
            Log.d(TAG, "Title is: " + title);
            String text = savedInstanceState.getString("Text");
            Log.d(TAG, "Text is: " + text);
            String imgUrl = savedInstanceState.getString("ImgLink");
            String doptext = savedInstanceState.getString("DopText");

            title_article.setText(title);
            fulltext_article.setText(text);
            Picasso.with(this.getContext().getApplicationContext())
                    .load(imgUrl)
                    .resize(290, 250)
                    .onlyScaleDown()
                    .placeholder(R.mipmap.ic_empty)
                    .centerCrop()
                    .into(img_article);
        } else {
            img_article.setImageResource(R.mipmap.ic_launcher);
        }
    }
}