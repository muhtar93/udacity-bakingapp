package org.saungit.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saungit.bakingapp.R;
import org.saungit.bakingapp.model.Baking;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class BakingRemoteViewsFactory implements RemoteViewsFactory {
    private Context mContext;
    private ArrayList<Baking> bakingArrayList = new ArrayList<>();

    public BakingRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        StringRequest request =
                new StringRequest(Request.Method.GET,
                        "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArrayBaking = new JSONArray(response);
                                    for (int i = 0; i < jsonArrayBaking.length(); i++){
                                        JSONObject jsonObjectBaking = jsonArrayBaking.getJSONObject(i);
                                        Baking baking = new Baking();
                                        baking.setName(jsonObjectBaking.getString("name"));
                                        baking.setServings(jsonObjectBaking.getString("servings"));
                                        baking.setImage(jsonObjectBaking.getString("image"));
                                        bakingArrayList.add(baking);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.d("widget response",response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(mContext, volleyError.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return bakingArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_row_widget);
        try {
            rv.setImageViewBitmap(R.id.icon, BitmapFactory.decodeStream(new URL(bakingArrayList.get(position).getImage()).openConnection().getInputStream()));
        } catch (IOException e) {
        }
        rv.setTextViewText(R.id.name, bakingArrayList.get(position).getName());
        rv.setTextViewText(R.id.servings, bakingArrayList.get(position).getServings());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
