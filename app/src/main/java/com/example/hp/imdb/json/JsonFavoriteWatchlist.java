package com.example.hp.imdb.json;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.example.hp.imdb.Database;
import com.example.hp.imdb.MovieAdapter;
import com.example.hp.imdb.MovieInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class JsonFavoriteWatchlist extends AsyncTask<Object, Object, ArrayList<HashMap<String, String>>> {
    ArrayList<MovieInfo> arrayList_imdb;
    RecyclerView fav_watch;
    Context context;
    MovieInfo imdb;
    Database movieDbHelper;
    RecyclerView currentView;
    int mode = 0;
    private ArrayList<HashMap<String, String>> arrayList;
    private JSONObject jsonObject;
    private String url_fav;

    public JsonFavoriteWatchlist(Context applicationContext, RecyclerView view, int mode) {
        this.context = applicationContext;
        this.currentView = view;
        this.mode = mode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(Object... params) {
        arrayList_imdb = new ArrayList<>();
        arrayList_imdb.clear();
        movieDbHelper = new Database(context);

        if (mode == 1) {
            arrayList_imdb = movieDbHelper.getFavorite();
            Log.e("Fav entry", String.valueOf(arrayList_imdb));
        } else if (mode == 2) {
            arrayList_imdb = movieDbHelper.getWatchlist();
        }

        arrayList = new ArrayList<>();

        Json_Data jsoNdata = new Json_Data();
        try {
            for (int i = 0; i < arrayList_imdb.size(); i++) {
                url_fav = "http://api.themoviedb.org/3/movie/" + arrayList_imdb.get(i).getMovie_id()
                        + "?api_key=8496be0b2149805afa458ab8ec27560c";
                jsonObject = jsoNdata.getJSONFromURL(url_fav);
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put("id", jsonObject.getString("id"));
                hashMap.put("original_title", jsonObject.getString("original_title"));
                hashMap.put("release_date", jsonObject.getString("release_date"));
                hashMap.put("popularity", jsonObject.getString("popularity"));
                hashMap.put("vote_count", jsonObject.getString("vote_count"));
                hashMap.put("vote_average", jsonObject.getString("vote_average"));
                hashMap.put("poster_path", "http://image.tmdb.org/t/p/original" +
                        jsonObject.getString("poster_path"));
                arrayList.add(hashMap);
            }

        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        Log.e("Arraylist fav",arrayList.toString());
        return arrayList;
    }
    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
        super.onPostExecute(result);
        MovieAdapter mAdapter = new MovieAdapter(context, result);
        currentView.setAdapter(mAdapter);
    }
}

