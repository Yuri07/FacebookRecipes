package edu.edx.yuri.facebookrecipes;

import android.app.Application;
import android.content.Intent;

import com.facebook.login.LoginManager;
import com.raizlabs.android.dbflow.config.FlowManager;

import edu.edx.yuri.facebookrecipes.libs.di.LibsModule;
import edu.edx.yuri.facebookrecipes.login.ui.LoginActivity;
import edu.edx.yuri.facebookrecipes.recipelist.di.DaggerRecipeListComponent;
import edu.edx.yuri.facebookrecipes.recipelist.di.RecipeListComponent;
import edu.edx.yuri.facebookrecipes.recipelist.di.RecipeListModule;
import edu.edx.yuri.facebookrecipes.recipelist.ui.RecipeListActivity;
import edu.edx.yuri.facebookrecipes.recipelist.ui.RecipeListView;
import edu.edx.yuri.facebookrecipes.recipelist.ui.adapters.OnItemClickListener;
import edu.edx.yuri.facebookrecipes.recipemain.di.DaggerRecipeMainComponent;
import edu.edx.yuri.facebookrecipes.recipemain.di.RecipeMainComponent;
import edu.edx.yuri.facebookrecipes.recipemain.di.RecipeMainModule;
import edu.edx.yuri.facebookrecipes.recipemain.ui.RecipeMainActivity;
import edu.edx.yuri.facebookrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by yuri_ on 04/12/2017.
 */

public class FacebookRecipesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initDatabase();

    }

    private void initDatabase() {
        FlowManager.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DatabseTearDown();
    }

    private void DatabseTearDown() {
        FlowManager.destroy();
    }


    public void logout() {

        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public RecipeMainComponent getRecipeMainComponent(RecipeMainActivity activity, RecipeMainView view){
        return DaggerRecipeMainComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .recipeMainModule(new RecipeMainModule(view))
                .build();
    }

    public RecipeListComponent getRecipeListComponent(RecipeListActivity activity, RecipeListView view, OnItemClickListener listener){
        return DaggerRecipeListComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .recipeListModule(new RecipeListModule(view, listener))
                .build();
    }

}
