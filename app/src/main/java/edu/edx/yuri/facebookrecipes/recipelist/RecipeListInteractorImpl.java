package edu.edx.yuri.facebookrecipes.recipelist;

/**
 * Created by yuri_ on 08/12/2017.
 */

public class RecipeListInteractorImpl implements RecipeListInteractor {

    RecipeListRepository repository;

    public RecipeListInteractorImpl(RecipeListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getSavedRecipes();
    }
}
