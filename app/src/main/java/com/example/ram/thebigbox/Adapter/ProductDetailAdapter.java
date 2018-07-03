package com.example.ram.thebigbox.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ram.thebigbox.Model.Product;
import com.example.ram.thebigbox.View.ProductDetailFragment;

import java.util.List;

/*
 * Created by SaiShreeRam on 07/02/2018.
 */

public class ProductDetailAdapter extends FragmentStatePagerAdapter {
    List<Product> products;

    public ProductDetailAdapter(FragmentManager fm, List<Product> products) {
        super(fm);
        this.products = products;
    }

    @Override
    public Fragment getItem(int position) {
        ProductDetailFragment detailFragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        Product item = products.get(position);
        bundle.putSerializable("CurrentProduct", item);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public int getCount() {
        if (products != null) {
            return products.size();
        }
        return 0;
    }
}
