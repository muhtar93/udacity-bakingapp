package org.saungit.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import org.json.JSONArray;
import org.saungit.bakingapp.R;
import org.saungit.bakingapp.model.Baking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class BakingRemoteViewsFactory implements RemoteViewsFactory {
    private Context mContext;
    private ArrayList<Baking> bakingArrayList = new ArrayList<>();
    private String name;

    public BakingRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        try {
            new FetchBakesTask().execute().get();
        } catch (InterruptedException | ExecutionException e) {
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        Log.d("size",""+bakingArrayList.size());
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

        Intent intent = new Intent();
        intent.putExtra("item", position);
        rv.setOnClickFillInIntent(R.id.bacckground, intent);
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

    public class FetchBakesTask extends AsyncTask<Void, Void, ArrayList<Baking>> {

        @Override
        protected ArrayList<Baking> doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                Uri builtUri = Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                        .buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                JSONArray movieArray = new JSONArray(buffer.toString());
                bakingArrayList = new ArrayList<>();
                for (int i = 0; i < movieArray.length(); i++) {
                    bakingArrayList.add(new Baking(movieArray.getJSONObject(i)));
                    Log.e("name: ", bakingArrayList.get(i).getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {
                }
                return bakingArrayList;
            }
        }
    }
}
