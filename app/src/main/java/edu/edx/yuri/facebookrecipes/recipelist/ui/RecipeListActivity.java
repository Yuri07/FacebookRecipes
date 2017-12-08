package edu.edx.yuri.facebookrecipes.recipelist.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.edx.yuri.facebookrecipes.FacebookRecipesApp;
import edu.edx.yuri.facebookrecipes.R;
import edu.edx.yuri.facebookrecipes.entities.Recipe;
import edu.edx.yuri.facebookrecipes.libs.GlideImageLoader;
import edu.edx.yuri.facebookrecipes.libs.base.ImageLoader;
import edu.edx.yuri.facebookrecipes.recipelist.RecipeListPresenter;
import edu.edx.yuri.facebookrecipes.recipelist.events.RecipeListEvent;
import edu.edx.yuri.facebookrecipes.recipelist.ui.adapters.OnItemClickListener;
import edu.edx.yuri.facebookrecipes.recipelist.ui.adapters.RecipeAdapter;
import edu.edx.yuri.facebookrecipes.recipemain.ui.RecipeMainActivity;

public class RecipeListActivity extends AppCompatActivity implements RecipeListView, OnItemClickListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;//como nao usamos snackbars aqui nao pegamos o R.id.coordinatorlayout

    RecipeAdapter adapter;
    RecipeListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);
        setupInjection();
        setupToolBar();
        setupRecyclerView();
        presenter.onCreate();
        presenter.getRecipes();

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipes_list,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_main){
            navigateToMainScreen();
        }else if(id==R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToMainScreen() {

        Intent intent = new Intent(this, RecipeMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    private void logout() {
        FacebookRecipesApp app = (FacebookRecipesApp) getApplication();
        app.logout();
    }

    private void setupInjection() {

        ImageLoader loader = new GlideImageLoader(Glide.with(this));
        Recipe recipe = new Recipe();
        recipe.setFavorite(false);
        recipe.setTitle("Prueba");
        recipe.setSourceURL("http://static.food2fork.com/icedcoffee5766.jpg");
        recipe.setImageURL("http://static.food2fork.com/icedcoffee5766.jpg");
        adapter = new RecipeAdapter(Arrays.asList(recipe), loader, this);
        presenter = new RecipeListPresenter() {
            @Override
            public void onCreate() {

            }

            @Override
            public void onDestroy() {

            }

            @Override
            public void getRecipes() {

            }

            @Override
            public void removeRecipe(Recipe recipe) {

            }

            @Override
            public void toggleFavorite(Recipe recipe) {

            }

            @Override
            public void onEveentMainThread(RecipeListEvent event) {

            }

            @Override
            public RecipeListView getView() {
                return null;
            }
        };

    }

    private void setupRecyclerView() {

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.toolbar)
    public void onTollbarClick(){
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void setRecipes(List<Recipe> data) {
        adapter.setRecipeList(data);
    }

    @Override
    public void recipeUpdate() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void recipeDeleted(Recipe recipe) {
        adapter.removeRecipe(recipe);
    }

    @Override
    public void onFavoriteClick(Recipe recipe) {
        presenter.toggleFavorite(recipe);
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getSourceURL()));
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Recipe recipe) {
        presenter.removeRecipe(recipe);
    }
}
