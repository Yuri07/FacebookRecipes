package edu.edx.yuri.facebookrecipes.recipelist;

import edu.edx.yuri.facebookrecipes.entities.Recipe;

/**
 * Created by yuri_ on 06/12/2017.
 */

public interface RecipeListRepository {
    void getSavedRecipes();
    void updateRecipe(Recipe recipe);
    void removeRecipe(Recipe recipe);
}
