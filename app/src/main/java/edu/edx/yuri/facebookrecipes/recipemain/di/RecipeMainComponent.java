package edu.edx.yuri.facebookrecipes.recipemain.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.edx.yuri.facebookrecipes.libs.base.ImageLoader;
import edu.edx.yuri.facebookrecipes.libs.di.LibsModule;
import edu.edx.yuri.facebookrecipes.recipemain.RecipeMainPresenter;
import edu.edx.yuri.facebookrecipes.recipemain.ui.RecipeMainActivity;

/**
 * Created by yuri_ on 06/12/2017.
 */
@Singleton
@Component(modules = {RecipeMainModule.class, LibsModule.class})
public interface RecipeMainComponent {

    //void inject(RecipeMainActivity activity);

    ImageLoader getImageLoader();
    RecipeMainPresenter getPresenter();

}
