package edu.edx.yuri.facebookrecipes.recipemain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.edx.yuri.facebookrecipes.FacebookRecipesApp;
import edu.edx.yuri.facebookrecipes.R;
import edu.edx.yuri.facebookrecipes.entities.Recipe;
import edu.edx.yuri.facebookrecipes.libs.base.ImageLoader;
import edu.edx.yuri.facebookrecipes.recipelist.ui.RecipeListActivity;
import edu.edx.yuri.facebookrecipes.recipemain.RecipeMainPresenter;
import edu.edx.yuri.facebookrecipes.recipemain.events.RecipeMainEvent;

public class RecipeMainActivity extends AppCompatActivity implements RecipeMainView{

    @BindView(R.id.imgRecipe)
    ImageView imgRecipe;
    @BindView(R.id.imgDismiss)
    ImageButton imgDismiss;
    @BindView(R.id.imgKeep)
    ImageButton imgKeep;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.layoutContainer)
    RelativeLayout layoutContainer;

    private RecipeMainPresenter presenter;
    private Recipe currentRecipe;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main);
        ButterKnife.bind(this);
        setInjection();
        presenter.onCreate();
        presenter.getNextRecipe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipes_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_list){
            navigateToListScreen();
        }else if(id==R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FacebookRecipesApp app = (FacebookRecipesApp) getApplication();
        app.logout();
    }

    private void navigateToListScreen() {

        startActivity(new Intent(this, RecipeListActivity.class));

    }

    @OnClick(R.id.imgKeep)
    public void onKeep(){
        if(currentRecipe!=null){
            presenter.saveRecipe(currentRecipe);
        }
    }

    @OnClick(R.id.imgDismiss)
    public void onDismiss(){

        presenter.dismissRecipe();

    }

    private void setInjection() {

        presenter = new RecipeMainPresenter() {
            @Override
            public void onCreate() {

            }

            @Override
            public void onDestroy() {

            }

            @Override
            public void dismissRecipe() {

            }

            @Override
            public void getNextRecipe() {

            }

            @Override
            public void saveRecipe(Recipe recipe) {

            }

            @Override
            public void enEventMainThread(RecipeMainEvent event) {

            }

            @Override
            public RecipeMainView getView() {
                return null;
            }
        };

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();//unbind the view
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showUIElements() {
        imgKeep.setVisibility(View.VISIBLE);
        imgDismiss.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUIElements() {
        imgKeep.setVisibility(View.GONE);
        imgDismiss.setVisibility(View.GONE);
    }

    @Override
    public void saveAnimation() {

    }

    @Override
    public void dismissAnimation() {

    }

    @Override
    public void onRecipeSaved() {
        Snackbar.make(layoutContainer, R.string.recipemain_notice_saved, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setRecipe(Recipe recipe) {
        this.currentRecipe = recipe;
        imageLoader.load(imgRecipe, recipe.getImageURL());
    }

    @Override
    public void onGetRecipeError(String error) {
        String msgError = String.format(getString(R.string.recipemain_error));
        Snackbar.make(layoutContainer, msgError, Snackbar.LENGTH_SHORT).show();
    }
}
