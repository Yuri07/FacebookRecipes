package edu.edx.yuri.facebookrecipes.recipemain;

import edu.edx.yuri.facebookrecipes.entities.Recipe;
import edu.edx.yuri.facebookrecipes.recipemain.events.RecipeMainEvent;
import edu.edx.yuri.facebookrecipes.recipemain.ui.RecipeMainView;


/**
 * Created by yuri_ on 05/12/2017.
 */

public interface RecipeMainPresenter {

    void onCreate();
    void onDestroy();

    void saveRecipe(Recipe recipe);
    void dismissRecipe();
    void getNextRecipe();

    void onEventMainThread(RecipeMainEvent event);

    void imageReady();//podemos usar esses dois metodos quando a imagem esta carregando
    void imageError(String error);

    RecipeMainView getView();//Isso vai ser util para testes.na licao 5



}
