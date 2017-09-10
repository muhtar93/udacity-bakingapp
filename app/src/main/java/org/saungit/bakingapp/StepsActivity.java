package org.saungit.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saungit.bakingapp.adapter.IngredientsAdapter;
import org.saungit.bakingapp.adapter.StepsAdapter;
import org.saungit.bakingapp.model.Baking;

import java.util.ArrayList;
import java.util.List;

public class StepsActivity extends AppCompatActivity {
    private List<Baking> bakingList = new ArrayList<>();
    private RecyclerView recyclerViewBaking;
    private StepsAdapter stepsAdapter;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        Intent intent = getIntent();
        final String ingredients = intent.getStringExtra("ingredients");
        final String steps = intent.getStringExtra("steps");
        name = intent.getStringExtra("name");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIngredients = new Intent(StepsActivity.this, DetailIngredientsActivity.class);
                detailIngredients.putExtra("ingredients", ingredients);
                startActivity(detailIngredients);
            }
        });

        recyclerViewBaking = (RecyclerView) findViewById(R.id.recyclerViewBaking);

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

                baking.setShortDescription(shortDescription);
                baking.setDescription(description);
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
