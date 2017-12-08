package edu.edx.yuri.facebookrecipes.recipelist;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Arrays;
import java.util.List;

import edu.edx.yuri.facebookrecipes.entities.Recipe;
import edu.edx.yuri.facebookrecipes.libs.base.EventBus;
import edu.edx.yuri.facebookrecipes.recipelist.events.RecipeListEvent;

/**
 * Created by yuri_ on 08/12/2017.
 */

public class RecipeListRepositoryImpl implements RecipeListRepository {

    private EventBus eventBus;

    public RecipeListRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getSavedRecipes() {
        /*FlowCursorList<Recipe> storedRecipes = new FlowCursorList<Recipe>(true, Recipe.class);//como nao temos nao temos uma condicao ou uma classe de trabalho estamos pegando tudo, que é o que preciamos para essa query.
        FlowCursorList<Recipe> storedRecipes = new FlowCursorList(SQLite.select().from(Recipe.class));//erro('FlowCursorList(com.raizlabs.android.dbflow.list.FlowCursorList.Builder)' has private access in 'com.raizlabs.android.dbflow.list.FlowCursorList')
        post(RecipeListEvent.READ_EVENT,storedRecipes.getAll());
        storedRecipes.close();*/
        List<Recipe> recipes = SQLite.select().from(Recipe.class).queryList();//https://guides.codepath.com/android/DBFlow-Guide#querying-rows
        post(RecipeListEvent.READ_EVENT,recipes);


    }

    @Override
    public void updateRecipe(Recipe recipe) {
        recipe.update();
        post();
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        recipe.delete();
        post(RecipeListEvent.DELETE_EVENT, Arrays.asList(recipe));

    }

    private void post(int type, List<Recipe> recipeList){
        RecipeListEvent event = new RecipeListEvent();
        event.setType(type);
        event.setRecipeList(recipeList);
        eventBus.post(event);
    }

    private void post(){
        post(RecipeListEvent.UPDATE_EVENT,null);
    }

}
