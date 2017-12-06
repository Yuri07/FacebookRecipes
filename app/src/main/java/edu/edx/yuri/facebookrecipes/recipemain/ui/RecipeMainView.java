package edu.edx.yuri.facebookrecipes.recipemain.ui;

import edu.edx.yuri.facebookrecipes.entities.Recipe;

/**
 * Created by yuri_ on 05/12/2017.
 */

public interface RecipeMainView {

    void showProgress();
    void hideProgress();

    void showUIElements();
    void hideUIElements();

    void saveAnimation();
    void dismissAnimation();

    void onRecipeSaved();

    void setRecipe(Recipe recipe);

    void onGetRecipeError(String error);
}
