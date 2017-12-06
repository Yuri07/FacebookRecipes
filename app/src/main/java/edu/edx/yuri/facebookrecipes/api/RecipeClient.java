package edu.edx.yuri.facebookrecipes.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yuri_ on 05/12/2017.
 */

public class RecipeClient {
    private Retrofit retrofit;
    private final static String BASE_URL = "http://food2fork.com/api/";

    public RecipeClient(){
        this.retrofit = new Retrofit.Builder()//nao passamos dependencia pelo construtor conscientes de que perderemos os beneficios de teste da injecao de dependencia dagger2
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RecipeService getRecipeService(){
        return this.retrofit.create(RecipeService.class);
    }

}
