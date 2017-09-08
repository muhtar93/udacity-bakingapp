package org.saungit.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saungit.bakingapp.adapter.IngredientsAdapter;
import org.saungit.bakingapp.model.Baking;

import java.util.ArrayList;
import java.util.List;

public class DetailIngredientsActivity extends AppCompatActivity {
    private List<Baking> bakingList = new ArrayList<>();
    private RecyclerView recyclerViewBaking;
    private IngredientsAdapter ingredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ingredients);

        Intent intent = getIntent();
        String ingredients = intent.getStringExtra("ingredients");

        recyclerViewBaking = (RecyclerView) findViewById(R.id.recyclerViewBaking);

        ingredientsAdapter = new IngredientsAdapter(bakingList, DetailIngredientsActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailIngredientsActivity.this);
        recyclerViewBaking.setLayoutManager(layoutManager);
        recyclerViewBaking.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBaking.setAdapter(ingredientsAdapter);

        showJSON(ingredients);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ingredients");
    }

    private void showJSON(String response) {
        try {
            JSONArray jsonArrayBaking = new JSONArray(response);
            for (int i = 0; i < jsonArrayBaking.length(); i++){
                JSONObject jsonObjectBaking = jsonArrayBaking.getJSONObject(i);

                Baking baking = new Baking();

                String quantity = jsonObjectBaking.getString("quantity");
                String measure = jsonObjectBaking.getString("measure");
                String ingredient = jsonObjectBaking.getString("ingredient");

                baking.setQuantity(quantity);
                baking.setMeasure(measure);
                baking.setIngredient(ingredient);

                bakingList.add(baking);
            }
            ingredientsAdapter.notifyDataSetChanged();
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
