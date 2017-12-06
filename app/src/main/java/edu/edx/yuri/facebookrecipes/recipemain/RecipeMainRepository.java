package edu.edx.yuri.facebookrecipes.recipemain;

import edu.edx.yuri.facebookrecipes.entities.Recipe;

/**
 * Created by yuri_ on 05/12/2017.
 */

public interface RecipeMainRepository {

    public final static int COUNT = 1;
    public final static String RECENT_SORT = "r";//most recent first
    public final static int RECIPE_RANGE = 100000;

    void getNextRecipe();
    void saveRecipe(Recipe recipe);
    void setRecipePage(int recipePage);

}
