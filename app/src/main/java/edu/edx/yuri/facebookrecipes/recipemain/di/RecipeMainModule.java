package edu.edx.yuri.facebookrecipes.recipemain.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.edx.yuri.facebookrecipes.api.RecipeClient;
import edu.edx.yuri.facebookrecipes.api.RecipeService;
import edu.edx.yuri.facebookrecipes.libs.base.EventBus;
import edu.edx.yuri.facebookrecipes.recipemain.GetNextRecipeInteractor;
import edu.edx.yuri.facebookrecipes.recipemain.GetNextRecipeInteractorImpl;
import edu.edx.yuri.facebookrecipes.recipemain.RecipeMainPresenter;
import edu.edx.yuri.facebookrecipes.recipemain.RecipeMainPresenterImpl;
import edu.edx.yuri.facebookrecipes.recipemain.RecipeMainRepository;
import edu.edx.yuri.facebookrecipes.recipemain.RecipeMainRepositoryImpl;
import edu.edx.yuri.facebookrecipes.recipemain.SaveRecipeInteractor;
import edu.edx.yuri.facebookrecipes.recipemain.SaveRecipeInteractorImpl;
import edu.edx.yuri.facebookrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by yuri_ on 06/12/2017.
 */
@Module
public class RecipeMainModule {

    RecipeMainView view;

    public RecipeMainModule(RecipeMainView view) {
        this.view = view;
    }

    @Provides @Singleton
    RecipeMainView providesRecipeMainView(){
        return this.view;
    }

    @Provides @Singleton
    RecipeMainPresenter providesRecipeMainPresenter(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveInteractor, GetNextRecipeInteractor getNextInteractor){
        return new RecipeMainPresenterImpl(eventBus, view, saveInteractor, getNextInteractor);
    }

    @Provides @Singleton
    SaveRecipeInteractor providesSaveRecipeInteractor(RecipeMainRepository repository){
        return new SaveRecipeInteractorImpl(repository);
    }

    @Provides @Singleton
    GetNextRecipeInteractor providesGetNextRecipeInteractor(RecipeMainRepository repository){
        return new GetNextRecipeInteractorImpl(repository);
    }

    @Provides @Singleton
    RecipeMainRepository providesRecipeMainRepository(EventBus eventBus, RecipeService service){
        return new RecipeMainRepositoryImpl(eventBus, service);
    }

    @Provides @Singleton
    RecipeService providesRecipeService(){//isso esta aqui pq apenas RecipeMainActivity usa a api retrofitm caso contrario estaria melhor em libs.di
            return new RecipeClient().getRecipeService();
    }




}
