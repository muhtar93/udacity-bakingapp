package org.saungit.bakingapp.model;

/**
 * Created by muhtar on 9/8/17.
 */

public class Baking {
    public String name;
    public String servings;

    public Baking(){}

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
}
