package com.example.hp.imdb;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class MainActivity extends AppCompatActivity implements Connectivity_Receiver.ConnectivityReceiverListener
{
    ViewPager mViewPager;
    static String MOVIE_ID = "id";
    static String MOVIE_TITLE = "original_title";
    static String RELEASE_DATE = "release_date";
    static String VOTE_COUNT = "vote_count";
    static String VOTE_AVERAGE = "vote_average";
    static String MOVIE_IMAGE = "poster_path";
    int mode = 0;
    TabLayout tabLayout;
    Connectivity_Receiver connectivityReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectivityReceiver = new Connectivity_Receiver();

        //Universal Image Loader Setup
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error_red)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024)
                .build();

        ImageLoader.getInstance().init(config);
        // End of Universal Image Loader Setup

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //Creating toolbar

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setLogo(R.drawable.imdbnew);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //Tab layout
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Now Playing"));
        tabLayout.addTab(tabLayout.newTab().setText("Top Rated"));
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Creating ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        Page_Adapter pageAdapter = new Page_Adapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(pageAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mViewPager.setOffscreenPageLimit(pageAdapter.getCount());
        //Checking the Internet connection
         checkConnection(); //Calling method
        tabLayout.setTabMode(TabLayout.MODE_FIXED); //Setting tab layout with view pager
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(ContextCompat.getColor(getApplicationContext(),
                R.color.tab_unselected), ContextCompat.getColor(getApplicationContext(), R.color.tab_selected) );
        tabLayout.setupWithViewPager(mViewPager);
    }
    // Method to check Internet connection status
    private void checkConnection() {
        boolean isConnected = Connectivity_Receiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the internet status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Internet Connection : Active";
            color = Color.WHITE;
        } else {
            message = "Internet connection : Disconnected";
            color = Color.RED;
        }

        final Snackbar snackbar = Snackbar.make(findViewById(R.id.snackbar), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Registering connection status listener
        ImdbApplication.getInstance().setConnectivityListener(MainActivity.this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
