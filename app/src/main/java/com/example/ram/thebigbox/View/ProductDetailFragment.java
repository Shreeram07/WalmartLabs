package com.example.ram.thebigbox.View;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.priya.thebigbox.R;
import com.example.ram.thebigbox.Model.Product;
import com.example.ram.thebigbox.Utils.CustomTagHandler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import static android.text.Html.fromHtml;

/*
 * Created by SaiShreeRam on 07/02/2018.
 */

public class ProductDetailFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle jsonBundle = getArguments();
        Product product = null;
        if (jsonBundle != null) {
            product = (Product) jsonBundle.getSerializable("CurrentProduct");
        }

        View detailView = inflater.inflate(R.layout.product_detail_fragment, container, false);

        if (product != null) {

            //Initialize views to their respective ID's
            TextView itemName = detailView.findViewById(R.id.ProductName);
            TextView itemPrice = detailView.findViewById(R.id.ProductPrice);
            TextView itemID = detailView.findViewById(R.id.ProductID);
            TextView itemLongDesc = detailView.findViewById(R.id.ProductLongDesc);
            TextView itemShortDesc = detailView.findViewById(R.id.ProductShortDesc);
            TextView itemReviewCount = detailView.findViewById(R.id.ReviewCount);
            TextView itemInStock = detailView.findViewById(R.id.inStock);
            ImageView itemImage = detailView.findViewById(R.id.ProductImage);
            RatingBar itemRating = detailView.findViewById(R.id.ProductRatingReview);

            //Setting up views with resources
            itemName.setText(product.getProductName());
            itemPrice.setText(product.getPrice());
            itemLongDesc.setText(fromHtml(product.getLongDescription()));
            itemShortDesc.setText(Html.fromHtml(product.getShortDescription(), null, new CustomTagHandler()));
            itemRating.setRating(product.getReviewRating());
            itemReviewCount.setText(Integer.toString(product.getReviewCount()));
            itemID.setText(product.getProductId());
            boolean b = product.getInStock().equals("true");
            itemInStock.setText(b ? "Yes" : "No");
            Glide.with(this).load(getString(R.string.BASE_URL) + product.getProductImage()).into(itemImage);

        }

        return detailView;
    }
}
