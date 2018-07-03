package com.example.ram.thebigbox.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.priya.thebigbox.R;
import com.example.ram.thebigbox.Model.Product;
import com.example.ram.thebigbox.Adapter.ProductDetailAdapter;

import java.util.List;

/*
 * Created by SaiShreeRam on 07/02/2018.
 */

public class ProductDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ProductDetailAdapter detailAdapter;
    public List<Product> productList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_activity);
        viewPager = findViewById(R.id.pager);
        productList = (List<Product>) getIntent().getSerializableExtra("LIST");
        detailAdapter = new ProductDetailAdapter(getSupportFragmentManager(), productList);
        viewPager.setAdapter(detailAdapter);
        int index = getIntent().getIntExtra("position", 0);
        viewPager.setCurrentItem(index);
    }
}
