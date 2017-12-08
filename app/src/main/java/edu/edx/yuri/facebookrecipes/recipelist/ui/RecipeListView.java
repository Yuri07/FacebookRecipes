package edu.edx.yuri.facebookrecipes.recipelist.ui;

import java.util.List;

import edu.edx.yuri.facebookrecipes.entities.Recipe;

/**
 * Created by yuri_ on 06/12/2017.
 */

public interface RecipeListView {
    void setRecipes(List<Recipe> data);
    void recipeUpdate();
    void recipeDeleted(Recipe recipe);
}
