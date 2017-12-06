package edu.edx.yuri.facebookrecipes.recipemain.ui;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.edx.yuri.facebookrecipes.FacebookRecipesApp;
import edu.edx.yuri.facebookrecipes.R;
import edu.edx.yuri.facebookrecipes.entities.Recipe;
import edu.edx.yuri.facebookrecipes.libs.base.ImageLoader;
import edu.edx.yuri.facebookrecipes.recipelist.ui.RecipeListActivity;
import edu.edx.yuri.facebookrecipes.recipemain.RecipeMainPresenter;
import edu.edx.yuri.facebookrecipes.recipemain.di.RecipeMainComponent;
import edu.edx.yuri.facebookrecipes.recipemain.events.RecipeMainEvent;

public class RecipeMainActivity extends AppCompatActivity implements RecipeMainView, SwipeGestureListener {

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
    private RecipeMainComponent component;//nao estamos fazendo injecao de dep. implicimente por razoes de testes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main);
        ButterKnife.bind(this);
        setupInjection();
        setupImageLoader();
        setupGestureDetector();
        presenter.onCreate();
        presenter.getNextRecipe();
    }

    private void setupGestureDetector() {

        final GestureDetector gestureDetector = new GestureDetector(this, new SwipeGestureDetector(this));
        View.OnTouchListener gestureOnTouchListener = new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent event){

                return gestureDetector.onTouchEvent(event);
            }
        };

        imgRecipe.setOnTouchListener(gestureOnTouchListener);

    }

    private void setupImageLoader() {
        RequestListener glideRequestListener = new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                presenter.imageError(e.getLocalizedMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                presenter.imageReady();//poderia ser feito  aqui mesmo na activity, professor edx optou por usar presenter
                return false;
            }
        };
        imageLoader.setOnFinishedImageLoadingListener(glideRequestListener);
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
    @Override
    public void onKeep(){
        if(currentRecipe!=null){
            presenter.saveRecipe(currentRecipe);
        }
    }

    @OnClick(R.id.imgDismiss)
    @Override
    public void onDismiss(){

        presenter.dismissRecipe();

    }

    private void setupInjection() {

        FacebookRecipesApp app = (FacebookRecipesApp) getApplication();
        component = app.getRecipeMainComponent(this, this);
        imageLoader = getImageLoader();
        presenter = getPresenter();

    }

    private ImageLoader getImageLoader() {
        return component.getImageLoader();
    }

    private RecipeMainPresenter getPresenter() {
        return component.getPresenter();
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

    private void clearImage(){
        imgRecipe.setImageResource(0);
    }

    @Override
    public void saveAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.save_animation);
        animation.setAnimationListener(getAnimationListener());
        imgRecipe.startAnimation(animation);
    }

    @Override
    public void dismissAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.dismiss_animation);
        animation.setAnimationListener(getAnimationListener());
        imgRecipe.startAnimation(animation);
    }

    private Animation.AnimationListener getAnimationListener(){
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearImage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
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
