package com.portfolio.priyank.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private boolean flag;
    private Movie[] movies;

    public ImageAdapter(Context c) {
        mContext = c;
        flag = false;
        inflater = LayoutInflater.from(c);
    }

    public ImageAdapter(Context c, Movie[] movies) {
        mContext = c;
        flag = true;
        this.movies = movies;
        inflater = LayoutInflater.from(c);
    }

    public int getCount() {
        if(flag){
            return movies.length;
        }else{
            return imageUrls.length;
        }
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if(flag){
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.grid_view_item_image, parent, false);
            }

            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185"+movies[position].getPosterPath()).fit().into((ImageView) convertView);

            return convertView;
        }else {
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.grid_view_item_image, parent, false);
            }

            Picasso.with(mContext).load(imageUrls[position]).fit().into((ImageView) convertView);

            return convertView;
        }
        /*
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
        */
    }

    // references to our images

    // references to our images
    private Integer[] imageUrls = {
    };
}