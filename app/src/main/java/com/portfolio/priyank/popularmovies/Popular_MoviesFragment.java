package com.portfolio.priyank.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class Popular_MoviesFragment extends Fragment {

    private final String urlMovie = "http://api.themoviedb.org/3/discover/movie";
    private String sortBy = "sort_by=popularity.desc";
    private String apiKey = "api_key=";
    public GridView gridView;

    public Popular_MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_popular__movies, container, false);

        gridView = (GridView) view.findViewById(R.id.moviesGridView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_list_item_1, mThumbIds);
        gridView.setAdapter(new ImageAdapter(getContext()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    void updatePosters(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sortOrder_key), getString(R.string.pref_sortOrder_default));
        if(sortOrder == "mp") {
            sortBy = "sort_by=popularity.desc";
            String mdbUrl = urlMovie + "?" + sortBy + "&" + apiKey;
            new HttpAsyncTask().execute(mdbUrl);
        }else{
            sortBy = "sort_by=vote_average.asc";
            String mdbUrl = urlMovie + "?" + sortBy + "&" + apiKey;
            new HttpAsyncTask().execute(mdbUrl);
        }
    }

    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    @Override
    public void onStart() {
        super.onStart();
        updatePosters();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                //String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";
                //String apiKey = "&APPID=" + BuildConfig.OPEN_WEATHER_MAP_API_KEY;
                URL url = new URL(urls[0]);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                return forecastJsonStr;
            } catch (IOException e) {
                Log.e("zzzzzzzzzzz", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("zzzzzzzzzzz", "Error closing stream", e);
                    }
                }
            }
            //return null;
            //return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            try {
                Log.e("cccccccc ccc",result);
                JSONObject jsonRootObject = new JSONObject(result);

                JSONArray jsonArray = jsonRootObject.optJSONArray("results");
                final Movie[] movies = new Movie[jsonArray.length()];
                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String backdropPath= jsonObject.optString("backdrop_path").toString();
                    String posterPath= jsonObject.optString("poster_path").toString();
                    String originalTitle= jsonObject.optString("original_title").toString();
                    int id= Integer.parseInt(jsonObject.optString("id").toString());
                    String overview= jsonObject.optString("overview").toString();
                    String releaseDate= jsonObject.optString("release_date").toString();
                    float voteAverage= Float.parseFloat(jsonObject.optString("vote_average").toString());

                    Movie m = new Movie( backdropPath, posterPath, originalTitle, id, overview, releaseDate, voteAverage);
                    movies[i] = m;
                }
                Toast.makeText(getContext(), "Received!", Toast.LENGTH_SHORT).show();
                //GridView gridView = (GridView) findViewById(R.id.moviesGridView);
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_list_item_1, mThumbIds);
                gridView.setAdapter(new ImageAdapter(getContext(),movies));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        Bundle b = new Bundle();
                        b.putString("backdropPath", movies[position].getBackdropPath());
                        b.putString("posterPath", movies[position].getPosterPath());
                        b.putString("originalTitle", movies[position].getOriginalTitle());
                        b.putString("overview", movies[position].getOverview());
                        b.putString("releaseDate", movies[position].getReleaseDate());
                        b.putInt("id", movies[position].getId());
                        b.putFloat("voteAverage", movies[position].getVoteAverage());
                        Intent in = new Intent(getActivity(), MovieDetails.class);
                        in.putExtras(b);
                        startActivity(in);
                    }
                });


                //output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
