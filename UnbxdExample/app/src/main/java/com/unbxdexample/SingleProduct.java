package com.unbxdexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.unbxd.APICallBackListener;
import com.unbxd.UnbxdAnalytics;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by training on 10/03/16.
 */
public class SingleProduct extends AppCompatActivity {
    private UnbxdAnalytics analytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product_activity);

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
        analytics = new UnbxdAnalytics(SingleProduct.this);
        FloatingActionButton btn_cart = (FloatingActionButton)findViewById(R.id.btn_cart);

        final Intent intent = getIntent();
        TextView txt_title = (TextView)findViewById(R.id.title_id);
        TextView txt_price = (TextView)findViewById(R.id.detail_price);
        ImageView img_Url =(ImageView)findViewById(R.id.posted_image);
        txt_title.setText(intent.getStringExtra("title"));
        txt_price.setText(intent.getStringExtra("price"));
        Glide.with(SingleProduct.this).load(intent.getStringExtra("imageUrl")).placeholder(R.drawable.watermark).animate(android.R.anim.fade_in).into(img_Url);
        Toast.makeText(SingleProduct.this, "Tracking Click: " + intent.getStringExtra("title"), Toast.LENGTH_SHORT).show();
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> paramsCart = new HashMap<String, String>();
                paramsCart.put("pid", intent.getStringExtra("pid"));
                analytics.track("cart", paramsCart);
                getAnalyticAPICall();
                Toast.makeText(SingleProduct.this, "Tracking cart: " + intent.getStringExtra("title"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    ---------------------------------------------------------  Get Analytic API Call ---------------------------------------------------------------------------
    private void getAnalyticAPICall(){
        UnbxdAnalytics.setOnAPICallbackListener(new APICallBackListener.OnAPICallbackStringListener() {
            @Override
            public void onSuccessResponseString(String response) {
//                        Log.e("response-",response+ "");
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyError-", volleyError.toString() + "");
            }

            @Override
            public void onStatusCode(int statusCode) {
                Log.e("statusCode- ", statusCode + "");
            }
        });
    }
}
