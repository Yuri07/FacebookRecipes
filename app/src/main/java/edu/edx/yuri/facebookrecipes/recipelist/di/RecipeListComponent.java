package edu.edx.yuri.facebookrecipes.recipelist.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.edx.yuri.facebookrecipes.libs.di.LibsModule;
import edu.edx.yuri.facebookrecipes.recipelist.RecipeListPresenter;
import edu.edx.yuri.facebookrecipes.recipelist.ui.adapters.RecipeAdapter;

/**
 * Created by yuri_ on 06/12/2017.
 */
@Singleton
@Component(modules = {RecipeListModule.class, LibsModule.class})
public interface RecipeListComponent {

    //void inject(RecipeMainActivity activity);

    //ImageLoader getImageLoader();
    RecipeAdapter getAdapter();
    RecipeListPresenter getPresenter();


}
