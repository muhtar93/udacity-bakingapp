package org.saungit.bakingapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saungit.bakingapp.R;
import org.saungit.bakingapp.adapter.BakingAdapter;
import org.saungit.bakingapp.model.Baking;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerViewBaking) RecyclerView recyclerViewBaking;
    @BindView(R.id.frameLayout) FrameLayout frameLayout;

    private List<Baking> bakingList = new ArrayList<>();

    private BakingAdapter bakingAdapter;

    private ProgressDialog progressDialog;

    private String name;
    private String servings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        bakingAdapter = new BakingAdapter(bakingList, MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewBaking.setLayoutManager(layoutManager);
        recyclerViewBaking.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBaking.setAdapter(bakingAdapter);

        getSupportActionBar().setTitle("Baking Time");

        fetchDataCooking();
    }

    public void fetchDataCooking() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        StringRequest request =
                new StringRequest(Request.Method.GET,
                        "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                showJSON(response);
                                progressDialog.dismiss();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                String errorMessage="";
                                if (volleyError instanceof NetworkError) {
                                    errorMessage="check your internet connection";
                                } else if (volleyError instanceof ServerError) {
                                    errorMessage="server not found";
                                } else if (volleyError instanceof AuthFailureError) {
                                    errorMessage="check your internet connection";
                                } else if (volleyError instanceof ParseError) {
                                    errorMessage="fetching data failed";
                                } else if (volleyError instanceof NoConnectionError) {
                                    errorMessage="check your internet connection";
                                } else if (volleyError instanceof TimeoutError) {
                                    errorMessage="timeout connection";
                                }
                                Snackbar snackbar = Snackbar
                                        .make(frameLayout, errorMessage, Snackbar.LENGTH_LONG);
                                snackbar.show();
                                progressDialog.dismiss();
                            }
                        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

    private void showJSON(String response) {
        try {
            JSONArray jsonArrayBaking = new JSONArray(response);
            for (int i = 0; i < jsonArrayBaking.length(); i++){
                JSONObject jsonObjectBaking = jsonArrayBaking.getJSONObject(i);

                Baking baking = new Baking();

                name = jsonObjectBaking.getString("name");
                servings = jsonObjectBaking.getString("servings");
                String image = jsonObjectBaking.getString("image");

                JSONArray jsonArrayIngredients = jsonObjectBaking.getJSONArray("ingredients");
                String ingredients = jsonArrayIngredients.toString();

                JSONArray jsonArraySteps = jsonObjectBaking.getJSONArray("steps");
                String steps = jsonArraySteps.toString();

                baking.setName(name);
                baking.setServings(servings);
                baking.setImage(image);
                baking.setIngredients(ingredients);
                baking.setSteps(steps);

                bakingList.add(baking);
            }
            bakingAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
