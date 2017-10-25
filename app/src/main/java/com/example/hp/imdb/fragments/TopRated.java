package com.example.hp.imdb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hp.imdb.R;
import com.example.hp.imdb.json.Download_Json;
import com.example.hp.imdb.json.JsonFavoriteWatchlist;

public class TopRated extends Fragment {

    public TopRated() {
    }
    RecyclerView recyclerView_topRated;
    View view_topRated;
    int mode =0;
    String URL_topRated = "http://api.themoviedb.org/3/movie/top_rated?api_key=8496be0b2149805afa458ab8ec27560c";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onPause() {
        setRetainInstance(true);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view_topRated = inflater.inflate(R.layout.toprated_fragment, container, false);
        recyclerView_topRated = view_topRated.findViewById(R.id.recyclerView_topRated);
        loadRecyclerViewData();
        return view_topRated;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_favorites:
                mode = 1;
                JsonFavoriteWatchlist JSON_favorite_watchlist = new JsonFavoriteWatchlist(getActivity().
                        getApplicationContext(), recyclerView_topRated, mode);
                JSON_favorite_watchlist.execute();
                Toast.makeText(getActivity(),"My Favorites", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_watchlist:
                mode = 2;
                JsonFavoriteWatchlist JSON_favorite_watchlist1 = new JsonFavoriteWatchlist(getActivity().
                        getApplicationContext(),recyclerView_topRated, mode);
                JSON_favorite_watchlist1.execute();
                Toast.makeText(getActivity(),"My WatchList", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_refresh:
                new TopRated();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void loadRecyclerViewData(){
        Download_Json downloadJSON = new Download_Json(getActivity().getApplication().getApplicationContext(),
                recyclerView_topRated);
        downloadJSON.getJSON(URL_topRated);
    }
}

