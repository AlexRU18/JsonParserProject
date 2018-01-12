package com.alexsprod.jsonparserproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
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
            //ID
            //String id = savedInstanceState.getString("ID");
            String title = savedInstanceState.getString("Title");
            String text = savedInstanceState.getString("Text");
            String imgUrl = savedInstanceState.getString("ImgLink");
            //Доп.текст
            //String doptext = savedInstanceState.getString("DopText");

            title_article.setText(title);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                fulltext_article.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
            } else {
                fulltext_article.setText(Html.fromHtml(text));
            }
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