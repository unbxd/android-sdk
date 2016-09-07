package com.unbxdexample;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.unbxd.APICallBackListener;
import com.unbxd.UnbxdAnalytics;
import com.unbxd.UnbxdAutosuggest;
import com.unbxd.UnbxdGlobal;
import com.unbxd.UnbxdSearch;
import com.unbxdexample.Model.ProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    private LinearLayoutManager rLayoutManager;
    private Map<String, String> paramsSorting;
    private static ArrayList<ProductModel> productModelList = new ArrayList<>();
    private static ArrayList<ProductModel> productAutoSuggestList = new ArrayList<>();
    private ProductAdapter rAdapter;
    private AutoSuggestAdapter autoSuggAdapter;
    Context context = MainActivity.this;
    public static MenuItem item;
    public static SearchView sv;
    public int status;
    private UnbxdAnalytics analytics;
    public boolean globalIntentReceived = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        GlobalClass.arrayListCart = new ArrayList<ProductModel>();
        toolbar.inflateMenu(R.menu.menu_main);
        analytics = new UnbxdAnalytics(MainActivity.this);

        // Tracking visit
        Map<String, String> paramsVisitor = new HashMap<String, String>();
        analytics.track("visitor", paramsVisitor);

        getAnalyticAPICall();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        Button btn_filter = (Button) findViewById(R.id.btn_filter);
        Button btn_sort = (Button) findViewById(R.id.btn_Sort);
        UnbxdGlobal.configure(MainActivity.this, "unbxdandroid_com-u1456999444079", "92b91feb727edff3ba1fc80b85380c74", "36551efceeb84d48b2e25a6cd446e467");
        recyclerView.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(rLayoutManager);
        rAdapter = new ProductAdapter(context, productModelList);
        recyclerView.setAdapter(rAdapter);


        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(n);
            }
        });
        btn_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                status = 0;
               paramsSorting = new HashMap<String, String>();
                AlertDialog.Builder alertSortingDialog = new AlertDialog.Builder(MainActivity.this);
                final View customView = getLayoutInflater().inflate(R.layout.edit_sort_field, null);
                alertSortingDialog.setView(customView);
                ToggleButton toggle_price = (ToggleButton)customView.findViewById(R.id.toggle_btn_price);
                ToggleButton toggle_Category = (ToggleButton)customView.findViewById(R.id.toggle_btn_Category);
                paramsSorting.put("price","asc");
                paramsSorting.put("category","asc");

                toggle_price.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            paramsSorting.put("price","asc");
                        } else {
                            paramsSorting.put("price","desc");
                        }
                    }
                });
                toggle_Category.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            paramsSorting.put("category","asc");
                        } else {
                            paramsSorting.put("category","desc");
                        }
                    }
                });
                alertSortingDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UnbxdSearch.sorting(paramsSorting);
                        UnbxdSearch.searchFor(MainActivity.this, "Sportcoat");
                        getSearchAPICall();
                    }
                });
                alertSortingDialog.setNegativeButton("Cancel", null);
                alertSortingDialog.setTitle("Sorting?");
                alertSortingDialog.show();
            }
        });


    }
    //    ---------------------------------------------------------  Get Analytic API ---------------------------------------------------------------------------
    private void getAnalyticAPICall() {
        UnbxdAnalytics.setOnAPICallbackListener(new APICallBackListener.OnAPICallbackStringListener() {
            @Override
            public void onSuccessResponseString(String response) {
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

            @Override
            public void onStatusCode(int statusCode) {
                Log.e("statusCode- ", statusCode + "");
            }
        });
    }

    //    ---------------------------------------------------------  Get Search API ---------------------------------------------------------------------------
    public void getSearchAPICall() {
        UnbxdSearch.setOnAPICallbackListener(new APICallBackListener.OnAPICallbackListener() {
            @Override
            public void onSuccessResponse(JSONObject jsonObject) {
                try {
                    productModelList.clear();
                    setListItemsInModel(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
    }

    //    ---------------------------------------------------------  Product Adapter ---------------------------------------------------------------------------
    public class ProductAdapter extends RecyclerView
            .Adapter<RecyclerView.ViewHolder> {
        ArrayList<ProductModel> mDataset = new ArrayList<>();
        Context context;

        public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title, price;
            ImageView imageView;
            FloatingActionButton btn_cart;
            CardView cvProduct;
            FrameLayout btn_order;
            EditText edit_qty;

            public DataObjectHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.posted_image);
                title = (TextView) itemView.findViewById(R.id.title_id);
                price = (TextView) itemView.findViewById(R.id.detail_price);
                cvProduct = (CardView) itemView.findViewById(R.id.cv);
                btn_cart = (FloatingActionButton) itemView.findViewById(R.id.btn_cart);
                btn_order = (FrameLayout) itemView.findViewById(R.id.btn_order);
                edit_qty = (EditText) itemView.findViewById(R.id.qty_number);
                btn_cart.setOnClickListener(this);
                btn_order.setOnClickListener(this);
                cvProduct.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                Log.v("onClick ", " getAdapterPosition() " + getAdapterPosition());
                final int position = getAdapterPosition();

                switch (view.getId()) {
                    case R.id.btn_cart:
                        Map<String, String> paramsCart = new HashMap<String, String>();
                        paramsCart.put("pid", mDataset.get(position).getpID());
                        analytics.track("cart", paramsCart);
                        getAnalyticAPICall();

                        GlobalClass.arrayListCart.add(mDataset.get(position));
                        Intent nCart = new Intent(MainActivity.this, CartActivity.class);
                        nCart.putExtra("pTitle", mDataset.get(position).getTitle());
                        startActivity(nCart);
                        break;
                    case R.id.cv:
                        Map<String, String> paramsClick = new HashMap<String, String>();
                        paramsClick.put("pid", mDataset.get(position).getpID());
                        analytics.track("click", paramsClick);
                        getAnalyticAPICall();

                        Intent n = new Intent(MainActivity.this, SingleProduct.class);
                        n.putExtra("title", mDataset.get(position).getTitle());
                        n.putExtra("pid", mDataset.get(position).getpID());
                        n.putExtra("price", mDataset.get(position).getPrice() + "");
                        n.putExtra("imageUrl", mDataset.get(position).getImageURL());
                        startActivity(n);
                        break;
                    case R.id.btn_order:
                        Map<String, String> paramsOrder = new HashMap<String, String>();
                        paramsOrder.put("pid", mDataset.get(position).getpID());
                        paramsOrder.put("price", mDataset.get(position).getPrice() + "");
                        String qty_text = edit_qty.getText().toString();
                        Log.e("qty: ", qty_text);
                        if (!qty_text.equals("")) {
                            paramsOrder.put("qty", qty_text);
                        } else {
                            paramsOrder.put("qty", "1");
                        }
                        analytics.track("order", paramsOrder);
                        getAnalyticAPICall();

                        Toast.makeText(MainActivity.this, "Order Placed & tracking: " + mDataset.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }


        public ProductAdapter(Context context, ArrayList<ProductModel> myDataset) {
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
                    .inflate(R.layout.card_view, parent, false);
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Map<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("map");

        if (hashMap != null) {
            globalIntentReceived = true;
            UnbxdSearch.filterParams(hashMap);
            UnbxdSearch.searchFor(MainActivity.this, "Sportcoat");
            getSearchAPICall();
        }

        if (!globalIntentReceived) {
            UnbxdSearch.searchFor(MainActivity.this, "Sportcoat");
            UnbxdSearch.setOnAPICallbackListener(new APICallBackListener.OnAPICallbackListener() {
                @Override
                public void onSuccessResponse(JSONObject jsonObject) {
                    try {
                        setListItemsInModel(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
        }


    }

    void setListItemsInModel(JSONObject jsonObject) throws JSONException {
        String blank = "";
        JSONArray jsonArray = null;
        if (jsonObject.has("response") && !jsonObject.isNull("response")) {
            JSONObject jsonObj = jsonObject.getJSONObject("response");
            if (jsonObj.has("products") && !jsonObj.isNull("products")) {
                jsonArray = jsonObj.getJSONArray("products");
            }

        }

        try {
            Log.e("g", jsonArray + "");
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    ProductModel productModel = new ProductModel();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    if (jsonObject1.has("imageUrl") && !jsonObject1.isNull("imageUrl")) {
                        JSONArray jArray = jsonObject1.getJSONArray("imageUrl");
                        String id = jArray.getString(0);
                        productModel.setImageURL(id);
                    } else
                        productModel.setImageURL(blank);

                    if (jsonObject1.has("title") && !jsonObject1.isNull("title")) {
                        productModel.setTitle(jsonObject1.getString("title"));
                    } else
                        productModel.setTitle(blank);

                    if (jsonObject1.has("description") && !jsonObject1.isNull("description"))
                        productModel.setDescription(jsonObject1.getString("description"));
                    else
                        productModel.setDescription(blank);

                    if (jsonObject1.has("uniqueId") && !jsonObject1.isNull("uniqueId"))
                        productModel.setpID(jsonObject1.getString("uniqueId"));
                    else
                        productModel.setpID(blank);

                    if (jsonObject1.has("price") && !jsonObject1.isNull("price"))
                        productModel.setPrice(jsonObject1.getInt("price"));
                    else
                        productModel.setPrice(0);

                    productModelList.add(productModel);

                }
            }
            rAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        UnbxdSearch.searchFor(MainActivity.this, query);
        getSearchAPICall();

        Map<String, String> params = new HashMap<String, String>();
        params.put("query", query);
        analytics.track("search", params);
        getAnalyticAPICall();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText != null) {
            productAutoSuggestList.clear();
            UnbxdAutosuggest.autoSuggest(MainActivity.this, newText, 2, 3, 2, 2);
            getAutoSuggestAPICall();
        } else {
            UnbxdSearch.searchFor(MainActivity.this, "Sportcoat");
        }
        getSearchAPICall();
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.search_group);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        sv = (SearchView) item.getActionView();

        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) sv.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setTextColor(Color.WHITE);
        autoSuggAdapter = new AutoSuggestAdapter(context, R.layout.list_item_autosuggest, productAutoSuggestList);
        searchAutoComplete.setAdapter(autoSuggAdapter);

        sv.setQueryHint("Search..");
        sv.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        sv.setSubmitButtonEnabled(true);
        sv.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search_group), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                UnbxdSearch.searchFor(MainActivity.this, "Sportcoat");
                getSearchAPICall();
                return true;
            }
        });

        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ProductModel searchModel = (ProductModel) parent.getItemAtPosition(position);
                String searchString = searchModel.getAutoSuggest();
                searchAutoComplete.setText(searchString);
                UnbxdSearch.searchFor(MainActivity.this, searchString);
                getSearchAPICall();
                Toast.makeText(MainActivity.this, "you clicked " + searchString, Toast.LENGTH_LONG).show();
            }
        });

        return true;
    }

    //    ---------------------------------------------------------  AutoSuggest FilterAdapter ---------------------------------------------------------------------------
    public class AutoSuggestAdapter extends ArrayAdapter<ProductModel> {
        Context mContext;
        private final int mLayoutResourceId;
        private final ArrayList<ProductModel> mProduct;
        private final ArrayList<ProductModel> mProduct_All;
        private final ArrayList<ProductModel> mProduct_Suggest;

        public AutoSuggestAdapter(Context context, int resource, ArrayList<ProductModel> productAutoSuggestList) {
            super(context, resource, productAutoSuggestList);
            this.mContext = context;
            this.mLayoutResourceId = resource;
            this.mProduct = productAutoSuggestList;
            this.mProduct_All = productAutoSuggestList;
            this.mProduct_Suggest = new ArrayList<ProductModel>();

        }

        public int getCount() {
            return mProduct.size();
        }

        public ProductModel getItem(int position) {
            return mProduct.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                    convertView = inflater.inflate(mLayoutResourceId, parent, false);
                }
                ProductModel productModel = getItem(position);
                TextView name = (TextView) convertView.findViewById(R.id.text_auto_suggest);
                name.setText(mProduct.get(position).getAutoSuggest());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                public String convertResultToString(Object resultValue) {
                    return ((ProductModel) resultValue).getAutoSuggest();
                }

                @Override
                protected Filter.FilterResults performFiltering(CharSequence constraint) {
                    if (constraint != null) {
                        mProduct_Suggest.clear();
                        for (ProductModel pSuggest : mProduct_Suggest) {
                            if (pSuggest.getAutoSuggest().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                                mProduct_Suggest.add(pSuggest);
                            }
                        }
                        FilterResults filterResults = new FilterResults();
                        filterResults.values = mProduct_Suggest;
                        filterResults.count = mProduct_Suggest.size();
                        return filterResults;
                    } else {
                        return new FilterResults();
                    }
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    mProduct.clear();
                    if (results != null && results.count > 0) {
                        ArrayList<?> result = (ArrayList<?>) results.values;
                        for (Object object : result) {
                            if (object instanceof ProductModel) {
                                mProduct.add((ProductModel) object);
                            }
                        }
                    } else if (constraint == null) {
                        mProduct.addAll(mProduct_All);
                    }
                    notifyDataSetChanged();
                }
            };
        }
    }

    //    ---------------------------------------------------------  Get Autosuggest API ---------------------------------------------------------------------------
    private void getAutoSuggestAPICall() {
        UnbxdAutosuggest.setOnAPICallbackListenerLocal(new APICallBackListener.OnAPICallbackListener() {
            @Override
            public void onSuccessResponse(JSONObject jsonObject) {
                Log.e("autosuggest: ", jsonObject + "");
                try {
                    productAutoSuggestList.clear();
                    setAutoSuggestListModel(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyError", volleyError + "");
            }
        });
    }

    //    ---------------------------------------------------------  Set Autosuggest List Model ---------------------------------------------------------------------------
    private void setAutoSuggestListModel(JSONObject jsonObject) throws JSONException {
        String blank = "";
        JSONArray jsonArray = null;
        if (jsonObject.has("response") && !jsonObject.isNull("response")) {
            JSONObject jsonObj = jsonObject.getJSONObject("response");
            if (jsonObj.has("products") && !jsonObj.isNull("products")) {
                jsonArray = jsonObj.getJSONArray("products");
            }
        }
        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    ProductModel productModel = new ProductModel();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    if (jsonObject1.has("autosuggest") && !jsonObject1.isNull("autosuggest")) {
                        productModel.setAutoSuggest(jsonObject1.getString("autosuggest"));
                    } else
                        productModel.setAutoSuggest(blank);

                    if (jsonObject1.has("imageUrl") && !jsonObject1.isNull("imageUrl")) {
                        JSONArray jArray = jsonObject1.getJSONArray("imageUrl");
                        String id = jArray.getString(0);
                        productModel.setImageURL(id);
                    } else
                        productModel.setImageURL(blank);

                    if (jsonObject1.has("title") && !jsonObject1.isNull("title")) {
                        productModel.setTitle(jsonObject1.getString("title"));
                    } else
                        productModel.setTitle(blank);

                    if (jsonObject1.has("description") && !jsonObject1.isNull("description"))
                        productModel.setDescription(jsonObject1.getString("description"));
                    else
                        productModel.setDescription(blank);

                    if (jsonObject1.has("uniqueId") && !jsonObject1.isNull("uniqueId"))
                        productModel.setpID(jsonObject1.getString("uniqueId"));
                    else
                        productModel.setpID(blank);

                    if (jsonObject1.has("price") && !jsonObject1.isNull("price"))
                        productModel.setPrice(jsonObject1.getInt("price"));
                    else
                        productModel.setPrice(0);

                    productAutoSuggestList.add(productModel);
                }
            }
            autoSuggAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
