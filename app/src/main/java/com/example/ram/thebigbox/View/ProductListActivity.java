package com.example.ram.thebigbox.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.example.priya.thebigbox.R;
import com.example.ram.thebigbox.Model.Product;
import com.example.ram.thebigbox.Model.WalmartApiData;
import com.example.ram.thebigbox.Adapter.ProductListAdapter;
import com.example.ram.thebigbox.Utils.AlertUserDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 * Created by SaiShreeRam on 07/02/2018.
 */

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.Adapter recyclerAdapter;
    LinearLayoutManager layoutManager;
    WalmartApiData walmartData;
    ProgressBar progressBar;
    Boolean isScrolling = false;
    int maxProducts = 30;
    int pages = 1;
    int currentItems, totalItems, scrolledOutItems;

    private static final String TAG = ProductListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);

        //Intialization of recycler view
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(ProductListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (savedInstanceState != null) {
            // scroll to existing position which exist before rotation.
            recyclerView.scrollToPosition(savedInstanceState.getInt("itemposition"));
        }

        //Progress bar for page loading
        progressBar = findViewById(R.id.progressBar);

        new AsyncFetch().execute();

        //Lazy Loading on recycler view
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int totalProducts = walmartData.getTotalProducts();
                    //Current visible items on the recycler view
                    currentItems = layoutManager.getChildCount();
                    //Total items on the recycler view
                    totalItems = layoutManager.getItemCount();
                    //Scrolled out items from the recycler view
                    scrolledOutItems = layoutManager.findFirstVisibleItemPosition();

                    if (totalItems == totalProducts) {
                        return;
                    } else {
                        if ((totalProducts - totalItems) >= maxProducts) {
                            maxProducts = 30;
                        } else {
                            maxProducts = totalProducts - totalItems;
                        }
                    }
                    if (isScrolling && (currentItems + scrolledOutItems == totalItems)) {
                        isScrolling = false;
                        pages++;
                        new AsyncFetch().execute();
                    }
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        int value = getIntent().getIntExtra("position", 0);
        savedInstanceState.putInt("itemposition", value);
        super.onSaveInstanceState(savedInstanceState);
    }

    //Fetching data asynchronously using Asynctask
    // OKHttp library for network calls
    private class AsyncFetch extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String API_URI = getString(R.string.BASE_URL) + "/" + getString(R.string.WALMART) + "/" + pages + "/" + maxProducts;

            if (isNetworkAvailable()) {
                OkHttpClient client = new OkHttpClient();
                try {
                    Request request = new Request.Builder().url(API_URI).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        return response.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else AlertUser();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String jsonData) {

            //Parsing JSON data from the server
            if (jsonData != null) {
                Gson gson = new Gson();
                walmartData = gson.fromJson(jsonData, WalmartApiData.class);
                List<Product> productList = new ArrayList<>(walmartData.getProducts());
                recyclerAdapter = new ProductListAdapter(ProductListActivity.this, productList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            } else AlertUser();
        }

    }


    //Notify user under no internet connection using Dialog Fragment
    public void AlertUser() {
        AlertUserDialog dialog = new AlertUserDialog();
        dialog.show(getSupportFragmentManager(), TAG);
    }

    //checks if Network is available in the device or emulator
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (manager != null) activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
