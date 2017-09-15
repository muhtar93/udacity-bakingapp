package org.saungit.bakingapp.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.saungit.bakingapp.R;
import org.saungit.bakingapp.activity.StepsActivity;
import org.saungit.bakingapp.adapter.BakesAdapter;
import org.saungit.bakingapp.model.Baking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BakesFragment extends Fragment implements BakesAdapter.ListItemClickListener {

    public static ArrayList<Baking> bakes = new ArrayList<>();
    private RecyclerView recyclerView;
    private BakesAdapter adapter;
    private View rootView;
    private boolean isMultiPane = false;

    public BakesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment, container, false);
        new FetchBakesTask(getActivity()).execute();
        return rootView;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(getActivity(), StepsActivity.class);
        intent.putExtra("item", clickedItemIndex);
        startActivity(intent);
    }

    void onPostExcute(ArrayList<Baking> bakes) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.bakeslist);
        RecyclerView.LayoutManager layoutManager;
        if (isMultiPane) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BakesAdapter(this, bakes);
        recyclerView.setAdapter(adapter);
    }

    public class FetchBakesTask extends AsyncTask<Void, Void, ArrayList<Baking>> {

        private ProgressDialog dialog;

        public FetchBakesTask(Activity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading");
            dialog.show();
        }

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
                bakes = new ArrayList<>();
                for (int i = 0; i < movieArray.length(); i++) {
                    bakes.add(new Baking(movieArray.getJSONObject(i)));
                    Log.e("name: ", bakes.get(i).getName());
                }
                return bakes;
            } catch (Exception e) {
                e.printStackTrace();
                return bakes;
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
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Baking> bakes) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            onPostExcute(bakes);
        }
    }
}
