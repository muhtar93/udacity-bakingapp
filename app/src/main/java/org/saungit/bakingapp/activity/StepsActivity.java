package org.saungit.bakingapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saungit.bakingapp.R;
import org.saungit.bakingapp.adapter.StepsAdapter;
import org.saungit.bakingapp.model.Baking;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity {
    @BindView(R.id.recyclerViewBaking) RecyclerView recyclerViewBaking;
    @BindView(R.id.linearLayout) LinearLayout linearLayout;
    private StepsAdapter stepsAdapter;

    private List<Baking> bakingList = new ArrayList<>();

    private String name;
    private String ingredients;
    private String steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        ingredients = intent.getStringExtra("ingredients");
        steps = intent.getStringExtra("steps");
        name = intent.getStringExtra("name");

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIngredients = new Intent(StepsActivity.this, DetailIngredientsActivity.class);
                detailIngredients.putExtra("ingredients", ingredients);
                startActivity(detailIngredients);
            }
        });

        stepsAdapter = new StepsAdapter(bakingList, StepsActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(StepsActivity.this);
        recyclerViewBaking.setLayoutManager(layoutManager);
        recyclerViewBaking.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBaking.setAdapter(stepsAdapter);

        showJSON(steps);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);
    }

    private void showJSON(String response) {
        try {
            JSONArray jsonArrayBaking = new JSONArray(response);
            for (int i = 0; i < jsonArrayBaking.length(); i++){
                JSONObject jsonObjectBaking = jsonArrayBaking.getJSONObject(i);

                Baking baking = new Baking();

                String shortDescription = jsonObjectBaking.getString("shortDescription");
                String description = jsonObjectBaking.getString("description");
                String videoURL = jsonObjectBaking.getString("videoURL");
                String thumbnailURL = jsonObjectBaking.getString("thumbnailURL");

                baking.setShortDescription(shortDescription);
                baking.setDescription(description);
                baking.setThumbnailURL(thumbnailURL);
                baking.setVideoURL(videoURL);
                baking.setName(name);

                bakingList.add(baking);
            }
            stepsAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
