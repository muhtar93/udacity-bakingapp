package org.saungit.bakingapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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
import org.saungit.bakingapp.adapter.BakingAdapter;
import org.saungit.bakingapp.model.Baking;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Baking> bakingList = new ArrayList<>();
    private RecyclerView recyclerViewBaking;
    private BakingAdapter bakingAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewBaking = (RecyclerView) findViewById(R.id.recyclerViewBaking);

        bakingAdapter = new BakingAdapter(bakingList, MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewBaking.setLayoutManager(layoutManager);
        recyclerViewBaking.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBaking.setAdapter(bakingAdapter);

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
                                if (volleyError instanceof NetworkError) {
                                    Toast.makeText(MainActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else if (volleyError instanceof ServerError) {
                                    Toast.makeText(MainActivity.this, "server not found", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else if (volleyError instanceof AuthFailureError) {
                                    Toast.makeText(MainActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else if (volleyError instanceof ParseError) {
                                    Toast.makeText(MainActivity.this, "fetching data failed", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else if (volleyError instanceof NoConnectionError) {
                                    Toast.makeText(MainActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else if (volleyError instanceof TimeoutError) {
                                    Toast.makeText(MainActivity.this, "timeout connection", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
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

                String name = jsonObjectBaking.getString("name").toString();
                String servings = jsonObjectBaking.getString("servings").toString();

                baking.setName(name);
                baking.setServings(servings);

                bakingList.add(baking);
            }
            bakingAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
