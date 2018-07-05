package com.example.ram.thebigbox.View;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
            TextView itemID = detailView.findViewById(R.id.ProductIDSerial);
            TextView itemLongDesc = detailView.findViewById(R.id.ProductLongDesc);
            TextView itemShortDesc = detailView.findViewById(R.id.ProductShortDesc);
            TextView itemReviewCount = detailView.findViewById(R.id.ReviewCount);
            TextView itemInStock = detailView.findViewById(R.id.inStock);
            ImageView itemImage = detailView.findViewById(R.id.ProductImage);
            RatingBar itemRating = detailView.findViewById(R.id.ProductRatingReview);

            //Setting up views with resources
            itemName.setText(product.getProductName());
            if (product.getPrice() != null) {
                itemPrice.setText(product.getPrice());
            } else
                itemPrice.setText(R.string.unlisted_price);
            if (product.getLongDescription() != null) {
                itemLongDesc.setText(Html.fromHtml(product.getLongDescription()));
            } else
                itemLongDesc.setText("N/A");
            if (product.getShortDescription() != null) {
                itemShortDesc.setText(Html.fromHtml(product.getShortDescription(), null, new CustomTagHandler()));
            } else {
                itemShortDesc.setText("N/A");
            }
            if (product.getReviewRating() != null) {
                itemRating.setRating(product.getReviewRating());
            } else itemRating.setRating(0);
            if (product.getReviewCount() != null) {
                itemReviewCount.setText("(" + Integer.toString(product.getReviewCount()) + ")");
            } else {
                itemReviewCount.setText("N/A");
            }
            if (product.getProductId() != null) {
                itemID.setText(product.getProductId());
            } else itemID.setText("N/A");
            itemID.setTypeface(null, Typeface.BOLD);
            itemInStock.setTextColor(product.getInStock() ? Color.parseColor("#08cc32") : Color.LTGRAY);
            if (product.getProductImage() != null)
                Glide.with(this).load(getString(R.string.BASE_URL) + product.getProductImage()).into(itemImage);

        }

        return detailView;
    }
}
