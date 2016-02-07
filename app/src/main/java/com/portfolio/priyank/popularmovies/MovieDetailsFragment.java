package com.portfolio.priyank.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    private TextView tvOriginalTitle;
    private TextView tvOverview;
    private TextView tvReleaseDate;
    private TextView tvVoteAverage;
    private ImageView ivPoster;

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent in = getActivity().getIntent();
        Bundle b = in.getExtras();
        String backdropPath=b.getString("backdropPath");
        String posterPath= "http://image.tmdb.org/t/p/w185/"+b.getString("posterPath");
        String originalTitle=b.getString("originalTitle");
        int id=b.getInt("id");
        String overview=b.getString("overview");
        String releaseDate=b.getString("releaseDate");
        float voteAverage=b.getFloat("voteAverage");

        View fragmentView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        tvOriginalTitle = (TextView) fragmentView.findViewById(R.id.tvOriginalTitle);
        tvOriginalTitle.setText(originalTitle);
        tvOverview = (TextView) fragmentView.findViewById(R.id.tvOverview);
        tvOverview.setText(overview);
        tvReleaseDate = (TextView) fragmentView.findViewById(R.id.tvReleaseDate);
        tvReleaseDate.setText(releaseDate);
        tvVoteAverage = (TextView) fragmentView.findViewById(R.id.tvVoteAverage);
        tvVoteAverage.setText(voteAverage+"");
        ivPoster = (ImageView) fragmentView.findViewById(R.id.ivPoster);
        Picasso.with(getContext()).load(posterPath).fit().into(ivPoster);

        return fragmentView;
    }
}
