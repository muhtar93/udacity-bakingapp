package org.saungit.bakingapp.activity;

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
import org.saungit.bakingapp.R;
import org.saungit.bakingapp.adapter.IngredientsAdapter;
import org.saungit.bakingapp.model.Baking;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailIngredientsActivity extends AppCompatActivity {
    @BindView(R.id.recyclerViewBaking) RecyclerView recyclerViewBaking;

    private List<Baking> bakingList = new ArrayList<>();

    private IngredientsAdapter ingredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ingredients);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String ingredients = intent.getStringExtra("ingredients");

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
