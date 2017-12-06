package edu.edx.yuri.facebookrecipes.recipemain;

import org.greenrobot.eventbus.Subscribe;

import edu.edx.yuri.facebookrecipes.entities.Recipe;
import edu.edx.yuri.facebookrecipes.libs.base.EventBus;
import edu.edx.yuri.facebookrecipes.recipemain.events.RecipeMainEvent;
import edu.edx.yuri.facebookrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by yuri_ on 06/12/2017.
 */

public class RecipeMainPresenterImpl implements RecipeMainPresenter {

    private EventBus eventBus;
    private RecipeMainView view;
    SaveRecipeInteractor saveInteractor;
    GetNextRecipeInteractor getNextInteractor;

    public RecipeMainPresenterImpl(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveInteractor, GetNextRecipeInteractor getNextInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.saveInteractor = saveInteractor;
        this.getNextInteractor = getNextInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        if(this.view!=null){
            view.saveAnimation();
            view.showProgress();
            view.hideUIElements();
        }
        saveInteractor.execute(recipe);
    }

    @Override
    public void dismissRecipe() {
        if(this.view!=null){
            view.dismissAnimation();
        }
        getNextRecipe();
    }

    @Override
    public void getNextRecipe() {
        if(this.view!=null){
            view.showProgress();
            view.hideUIElements();
        }
        getNextInteractor.execute();
    }

    @Override
    @Subscribe
    public void onEventMainThread(RecipeMainEvent event) {

        if(this.view!=null){
            String error = event.getError();
            if(error!=null){
                view.hideProgress();
                view.showUIElements();
            }else{
                if(event.getType() == RecipeMainEvent.NEXT_EVENT){
                    view.setRecipe(event.getRecipe());
                }else if (event.getType() == RecipeMainEvent.SAVE_EVENT){
                    view.onRecipeSaved();
                    getNextInteractor.execute();//And with this, each time the user stores a recipe automatically, we're going to grab the next one.
                }
            }
        }

    }

    @Override
    public void imageReady() {
        if(this.view!=null) {
            view.hideProgress();
            view.showUIElements();
        }
    }

    @Override
    public void imageError(String error) {
        if(this.view!=null) {
            view.onGetRecipeError(error);
        }
    }

    @Override
    public RecipeMainView getView() {//metodo para proposito de testes
        return this.view;
    }
}
