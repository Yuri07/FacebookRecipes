package edu.edx.yuri.facebookrecipes.recipelist.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.edx.yuri.facebookrecipes.entities.Recipe;
import edu.edx.yuri.facebookrecipes.libs.base.EventBus;
import edu.edx.yuri.facebookrecipes.libs.base.ImageLoader;
import edu.edx.yuri.facebookrecipes.recipelist.RecipeListInteractor;
import edu.edx.yuri.facebookrecipes.recipelist.RecipeListInteractorImpl;
import edu.edx.yuri.facebookrecipes.recipelist.RecipeListPresenter;
import edu.edx.yuri.facebookrecipes.recipelist.RecipeListPresenterImpl;
import edu.edx.yuri.facebookrecipes.recipelist.RecipeListRepository;
import edu.edx.yuri.facebookrecipes.recipelist.RecipeListRepositoryImpl;
import edu.edx.yuri.facebookrecipes.recipelist.StoredRecipesInteractor;
import edu.edx.yuri.facebookrecipes.recipelist.StoredRecipesInteractorImpl;
import edu.edx.yuri.facebookrecipes.recipelist.ui.RecipeListView;
import edu.edx.yuri.facebookrecipes.recipelist.ui.adapters.OnItemClickListener;
import edu.edx.yuri.facebookrecipes.recipelist.ui.adapters.RecipeAdapter;

/**
 * Created by yuri_ on 06/12/2017.
 */
@Module
public class RecipeListModule {

    RecipeListView view;
    OnItemClickListener clickListener;

    public RecipeListModule(RecipeListView view, OnItemClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @Provides @Singleton
    RecipeListView providesRecipeListView(){
        return this.view;
    }

    @Provides @Singleton
    RecipeAdapter providesRecipeAdapter(List<Recipe> recipeList, ImageLoader imageLoader, OnItemClickListener onItemClickListener){//retornado por RecipeListComponent
        return new RecipeAdapter(recipeList, imageLoader, onItemClickListener);
    }

    @Provides @Singleton
    List<Recipe> providesEmptyListRecipes(){
        return new ArrayList<Recipe>();
    }

    @Provides @Singleton
    OnItemClickListener providesOnItemClickListener(){
        return this.clickListener;
    }

    @Provides @Singleton
    RecipeListPresenter providesRecipeListPresenter(EventBus eventBus, RecipeListView view, RecipeListInteractor listInteractor,
                                                                        StoredRecipesInteractor storedInteractor){//retornado por RecipeListComponent
        return new RecipeListPresenterImpl(eventBus, view, listInteractor, storedInteractor);
    }

    @Provides @Singleton
    RecipeListInteractor providesRecipeListInteractor(RecipeListRepository repository){
        return new RecipeListInteractorImpl(repository);
    }

    @Provides @Singleton
    StoredRecipesInteractor providesStoredRecipesInteractor(RecipeListRepository repository){
        return new StoredRecipesInteractorImpl(repository);
    }

    @Provides @Singleton
    RecipeListRepository providesRecipeListRepository(EventBus eventBus){
        return new RecipeListRepositoryImpl(eventBus);
    }

}
