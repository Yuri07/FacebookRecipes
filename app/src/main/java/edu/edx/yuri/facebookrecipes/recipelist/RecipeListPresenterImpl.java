package edu.edx.yuri.facebookrecipes.recipelist;

import org.greenrobot.eventbus.Subscribe;

import edu.edx.yuri.facebookrecipes.entities.Recipe;
import edu.edx.yuri.facebookrecipes.libs.base.EventBus;
import edu.edx.yuri.facebookrecipes.recipelist.events.RecipeListEvent;
import edu.edx.yuri.facebookrecipes.recipelist.ui.RecipeListView;

/**
 * Created by yuri_ on 08/12/2017.
 */

public class RecipeListPresenterImpl implements RecipeListPresenter {

    private EventBus eventBus;
    private RecipeListView view;
    private RecipeListInteractor listInteractor;//pegar as receitas salvas localmente
    private StoredRecipesInteractor storedRecipesInteractor;//atualizar ou remover receitas salvas localmente

    public RecipeListPresenterImpl(EventBus eventBus, RecipeListView view, RecipeListInteractor listInteractor, StoredRecipesInteractor storedRecipesInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.listInteractor = listInteractor;
        this.storedRecipesInteractor = storedRecipesInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);

    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view=null;
    }

    @Override
    public void getRecipes() {
        listInteractor.execute();
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        storedRecipesInteractor.executeDelete(recipe);
    }

    @Override
    public void toggleFavorite(Recipe recipe) {
        boolean fav = recipe.isFavorite();
        recipe.setFavorite(!fav);
        storedRecipesInteractor.executeUpdate(recipe);
    }

    @Override
    @Subscribe
    public void onEveentMainThread(RecipeListEvent event) {

        if(this.view!=null){
            switch (event.getType()){
                case RecipeListEvent.READ_EVENT:
                    view.setRecipes(event.getRecipeList());
                    break;
                case RecipeListEvent.UPDATE_EVENT:
                    view.recipeUpdate();
                    break;
                case RecipeListEvent.DELETE_EVENT:
                    Recipe recipe = event.getRecipeList().get(0);
                    view.recipeDeleted(recipe);
            }
        }

    }

    @Override
    public RecipeListView getView() {
        return this.view;
    }
}
