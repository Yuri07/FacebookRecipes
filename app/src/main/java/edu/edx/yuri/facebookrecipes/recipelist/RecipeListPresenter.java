package edu.edx.yuri.facebookrecipes.recipelist;

import edu.edx.yuri.facebookrecipes.entities.Recipe;
import edu.edx.yuri.facebookrecipes.recipelist.events.RecipeListEvent;
import edu.edx.yuri.facebookrecipes.recipelist.ui.RecipeListView;

/**
 * Created by yuri_ on 06/12/2017.
 */

public interface RecipeListPresenter {

    void onCreate();
    void onDestroy();

    void getRecipes();
    void removeRecipe(Recipe recipe);
    void toggleFavorite(Recipe recipe);

    void onEveentMainThread(RecipeListEvent event);

    RecipeListView getView();//para proposito de testes

}
