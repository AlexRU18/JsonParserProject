package com.alexsprod.jsonparserproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsprod.jsonparserproject.items.Item;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import static com.alexsprod.jsonparserproject.items.JsonParser.TAG;

public class NewsRowAdapter extends ArrayAdapter<Item> {

    private Activity activity;
    private List<Item> items;
    private Item objBean;
    private int row;

    public NewsRowAdapter(Activity act, int resource, List<Item> arrayList) {
        super(act, resource, arrayList);
        this.activity = act;
        this.row = resource;
        this.items = arrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if ((items == null) || ((position + 1) > items.size()))
            return view;

        objBean = items.get(position);

        holder.tvTitle = (TextView) view.findViewById(R.id.title_cat1);
        holder.tvText = (TextView) view.findViewById(R.id.fulltext_cat1);
        holder.imgView = (ImageView) view.findViewById(R.id.img_cat1);

        if (holder.tvTitle != null && null != objBean.getTitle()
                && objBean.getTitle().trim().length() > 0) {
            holder.tvTitle.setText(Html.fromHtml(objBean.getTitle()));
        }
        if (holder.tvText != null && null != objBean.getText()
                && objBean.getText().trim().length() > 0) {
            holder.tvText.setText(Html.fromHtml(objBean.getText()));
        }
        if (holder.imgView != null) {
            if (null != objBean.getLink()
                    && objBean.getLink().trim().length() > 0) {
                //final ProgressBar pbar = holder.pbar;
                Picasso.with(this.getContext())
                        .load(String.valueOf(Html.fromHtml(objBean.getLink())))
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                               /* Save the bitmap or do something with it here */
                                //Set it in the ImageView
                                holder.imgView.setImageBitmap(bitmap);
                                Log.e(TAG, "Set Downloaded Image");
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Log.e(TAG, "OnBitmapFailed");
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                Log.e(TAG, "OnPrepareLoad");
                            }
                        });
            } else {
                holder.imgView.setImageResource(R.mipmap.ic_launcher);
                Log.d(TAG, "Set Default Image");
            }
        } else {
            Log.d(TAG, "ImgView is Null");
        }
        return view;
    }

    private class ViewHolder {
        private TextView tvTitle, tvText;
        private ImageView imgView;
    }
}