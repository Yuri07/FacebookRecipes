package edu.edx.yuri.facebookrecipes.recipemain;

import edu.edx.yuri.facebookrecipes.entities.Recipe;

/**
 * Created by yuri_ on 05/12/2017.
 */

public interface SaveRecipeInteractor {//So this is going to be save recipe interactor, remember the interactor is a use case, so this is for the specific case of storing the recipe, only has one method execute, and I need another one called get next recipe interactor, for that use case

    void execute(Recipe recipe);

}
