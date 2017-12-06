package edu.edx.yuri.facebookrecipes.recipemain;

import android.content.Context;

import java.util.Random;

import edu.edx.yuri.facebookrecipes.BuildConfig;
import edu.edx.yuri.facebookrecipes.R;
import edu.edx.yuri.facebookrecipes.api.RecipeSearchResponse;
import edu.edx.yuri.facebookrecipes.api.RecipeService;
import edu.edx.yuri.facebookrecipes.entities.Recipe;
import edu.edx.yuri.facebookrecipes.libs.base.EventBus;
import edu.edx.yuri.facebookrecipes.recipemain.events.RecipeMainEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuri_ on 06/12/2017.
 */

public class RecipeMainRepositoryImpl implements RecipeMainRepository {

    private int recipePage;
    private EventBus eventBus;
    private RecipeService service;

    public RecipeMainRepositoryImpl(int recipePage, EventBus eventBus, RecipeService service) {
        this.recipePage = recipePage;
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getNextRecipe() {
        Call<RecipeSearchResponse> call = service.search("e7e905f69301ac9619cf6a28aaff8af1",
                                                                    RECENT_SORT, COUNT, recipePage);
        Callback<RecipeSearchResponse> callback = new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if(response.isSuccessful()){

                    RecipeSearchResponse recipeSearchResponse = response.body();
                    if(recipeSearchResponse.getCount()==0){//O numero que eu passei como uma pagina e invalido (Remember this is using Json, so I need to parse the response, the parsing is done by the extension of retrofit, so I'm just going to check the count of this response, if it's equal to 0, then I have a valid response, but the number that I sent as a page is invalid, so if that happens, then I'm going to generate a new random number and call to set the recipe page, recipe range, there we go and then call the method again).
                        setRecipePage(new Random().nextInt(RECIPE_RANGE));
                        getNextRecipe();
                    }else{//tenho ao menos uma receita
                        Recipe recipe = recipeSearchResponse.getFirstRecipe();//recipeSearchResponse cada atributo deve ter o mesmo nome do JSON.
                        if(recipe!=null) {
                            post(recipe);
                        }else{
                            post(response.message());
                        }
                    }


                }else{
                    post(response.message());
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                post(t.getLocalizedMessage());
            }
        };
        call.enqueue(callback);//In this way when the call is finished then they call that going to be executed and is asynchronous, the library handles its own thread pool, so I don't have to worry about that and here´s the parameter, then I can work on the onResponse and onFailure methods
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipe.save();
        post();
    }

    @Override
    public void setRecipePage(int recipePage) {
        this.recipePage = recipePage;
    }

    private void post(String error) {
        post(error, RecipeMainEvent.NEXT_EVENT, null);
    }

    private void post(Recipe recipe) {
        post(null, RecipeMainEvent.NEXT_EVENT, recipe);
    }

    private void post(String error, int type, Recipe recipe){
        RecipeMainEvent recipeMainEvent = new RecipeMainEvent();
        recipeMainEvent.setType(type);
        recipeMainEvent.setRecipe(recipe);
        recipeMainEvent.setError(error);
        eventBus.post(recipeMainEvent);
    }

    private void post(){
        post(null, RecipeMainEvent.SAVE_EVENT, null);
    }//that here, when we are saving, we don't have a an error message(por isso nao temos 'private void post(String error){...}').

}
