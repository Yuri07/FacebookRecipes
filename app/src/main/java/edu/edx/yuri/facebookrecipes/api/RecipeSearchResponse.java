package edu.edx.yuri.facebookrecipes.api;

import java.util.List;

import edu.edx.yuri.facebookrecipes.entities.Recipe;

/**
 * Created by yuri_ on 05/12/2017.
 */

public class RecipeSearchResponse {

    private int count;
    private List<Recipe> recipes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Recipe getFirstRecipe(){//metodo necessario devido a falta de opces de costumizacao de implementacao da API?
        Recipe first = null;
        if(!recipes.isEmpty()){
            first = recipes.get(0);
        }
        return first;
    }
}
