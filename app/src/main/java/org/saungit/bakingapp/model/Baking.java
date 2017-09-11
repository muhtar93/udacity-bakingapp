package org.saungit.bakingapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by muhtar on 9/8/17.
 */

public class Baking {
    public String name;
    private ArrayList<Ingredient> ingredientsArrayList;
    private ArrayList<Step> stepsArrayList;
    public String servings;
    public String image;
    public String quantity;
    public String measure;
    public String ingredient;
    public String shortDescription;
    public String description;
    public String videoURL;
    public String ingredients;
    public String steps;
    public String thumbnailURL;

    public Baking(){}

    public Baking(JSONObject bake_jason) {
        try {
            this.name = bake_jason.getString("name");
            this.ingredientsArrayList = new ArrayList<>();
            JSONArray ingredientsJA = bake_jason.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsJA.length(); i++) {
                ingredientsArrayList.add(new Ingredient(ingredientsJA.getJSONObject(i)));
            }
            this.stepsArrayList = new ArrayList<>();
            JSONArray stepsJA = bake_jason.getJSONArray("steps");
            for (int i = 0; i < stepsJA.length(); i++) {
                stepsArrayList.add(new Step(stepsJA.getJSONObject(i)));
            }
            this.servings = bake_jason.getString("servings");
            this.image = bake_jason.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getServings() {
        return servings;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getMeasure() {
        return measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getSteps() {
        return steps;
    }
}
