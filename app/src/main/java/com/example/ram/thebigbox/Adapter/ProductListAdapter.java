package com.example.ram.thebigbox.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.priya.thebigbox.R;

import com.bumptech.glide.Glide;
import com.example.ram.thebigbox.Model.Product;
import com.example.ram.thebigbox.Utils.OnBottomReachedListener;
import com.example.ram.thebigbox.View.ProductDetailActivity;

import java.io.Serializable;
import java.util.List;

/*
 * Created by SaiShreeRam on 07/02/2018.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<Product> productList;
    private Context context;
    OnBottomReachedListener onBottomReachedListener;


    public ProductListAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.context = context;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    //Instantiating layout & view holders
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(view);
    }

    //Binding views to the recycler view
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position == productList.size() - 1){
            onBottomReachedListener.onBottomReached(position);
            }

        if (!productList.isEmpty()) {
            Product item = productList.get(position);
            holder.ProductName.setText(item.getProductName());
            holder.ProductPrice.setText(item.getPrice());
            holder.ReviewCount.setText("(" + String.format("%d", (item.getReviewCount())) + ")");
            holder.ReviewRating.setRating(item.getReviewRating());
            Glide.with(context).load(context.getString(R.string.BASE_URL) + item.getProductImage()).fitCenter().into(holder.ProductImage);
        }
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView ProductName;
        private TextView ProductPrice;
        private TextView ReviewCount;
        private RatingBar ReviewRating;
        private ImageView ProductImage;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.ProductName);
            ProductPrice = itemView.findViewById(R.id.ProductPrice);
            ProductImage = itemView.findViewById(R.id.ProductImage);
            ReviewCount = itemView.findViewById(R.id.ReviewCount);
            ReviewRating = itemView.findViewById(R.id.ratingBar);

            cardView = itemView.findViewById(R.id.CardView);
            cardView.setOnClickListener(this);

        }

        //To start ProductDetailsActivity
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("LIST", (Serializable) productList);
            context.startActivity(intent);
        }
    }
}
