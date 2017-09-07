package org.saungit.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchDataCooking();
    }

    public void fetchDataCooking() {
        StringRequest request =
                new StringRequest(Request.Method.GET,
                        "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //showJSON(response);
                                Log.d("response",response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                if (volleyError instanceof NetworkError) {
                                    Toast.makeText(MainActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                                } else if (volleyError instanceof ServerError) {
                                    Toast.makeText(MainActivity.this, "server not found", Toast.LENGTH_SHORT).show();
                                } else if (volleyError instanceof AuthFailureError) {
                                    Toast.makeText(MainActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                                } else if (volleyError instanceof ParseError) {
                                    Toast.makeText(MainActivity.this, "fetching data failed", Toast.LENGTH_SHORT).show();
                                } else if (volleyError instanceof NoConnectionError) {
                                    Toast.makeText(MainActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                                } else if (volleyError instanceof TimeoutError) {
                                    Toast.makeText(MainActivity.this, "timeout connection", Toast.LENGTH_SHORT).show();
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
}
