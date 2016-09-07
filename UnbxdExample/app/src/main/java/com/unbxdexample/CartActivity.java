package com.unbxdexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.unbxdexample.Model.ProductModel;

import java.util.ArrayList;

/**
 * Created by training on 10/03/16.
 */
public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private LinearLayoutManager rLayoutManager;
    private CartAdapter rAdapter;
    Context context = CartActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View customView = getLayoutInflater().inflate(R.layout.tool_bar_view, toolbar, false);
        toolbar.addView(customView);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.back_arrow, this.getTheme()));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.cart_view);
        recyclerView.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(rLayoutManager);
        rAdapter = new CartAdapter(context, GlobalClass.arrayListCart);
        recyclerView.setAdapter(rAdapter);
        Intent n = getIntent();
        String pTitle = n.getStringExtra("pTitle");
        Toast.makeText(CartActivity.this, "Tracking Product: " + pTitle, Toast.LENGTH_SHORT).show();
    }

    //    ---------------------------------------------------------  Cart Adpater ---------------------------------------------------------------------------
    public class CartAdapter extends RecyclerView
            .Adapter<RecyclerView.ViewHolder> {
        ArrayList<ProductModel> mDataset = new ArrayList<>();
        Context context;

        public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title, price;
            ImageView imageView;
            CardView cvProduct;

            public DataObjectHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.posted_image);
                title = (TextView) itemView.findViewById(R.id.title_id);
                price = (TextView) itemView.findViewById(R.id.detail_price);
                cvProduct = (CardView) itemView.findViewById(R.id.cv);
                cvProduct.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                final int position = getAdapterPosition();

                switch (view.getId()) {
                    case R.id.cv:
                        Intent n = new Intent(CartActivity.this, SingleProduct.class);
                        n.putExtra("title", mDataset.get(position).getTitle());
                        n.putExtra("price", mDataset.get(position).getPrice() + "");
                        n.putExtra("imageUrl", mDataset.get(position).getImageURL());
                        startActivity(n);
                        break;
                }
            }
        }


        public CartAdapter(Context context, ArrayList<ProductModel> myDataset) {
            mDataset = myDataset;
            this.context = context;
        }

        @Override
        public int getItemViewType(int position) {
            int viewType = 0;
            if (position == 0) {
                viewType = 1;
            }
            return viewType;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_view_cart, parent, false);
            return new DataObjectHolder(view);
        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int viewPosition) {
            final DataObjectHolder holder = (DataObjectHolder) viewHolder;
            holder.title.setText(mDataset.get(viewPosition).getTitle());
            holder.price.setText(mDataset.get(viewPosition).getPrice() + "");
            Glide.with(context).load(mDataset.get(viewPosition).getImageURL()).placeholder(R.drawable.watermark).animate(android.R.anim.fade_in).into(holder.imageView);
        }


        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
