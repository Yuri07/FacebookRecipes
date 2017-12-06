package edu.edx.yuri.facebookrecipes.entities;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.concurrent.RecursiveAction;

import edu.edx.yuri.facebookrecipes.db.RecipesDatabase;

/**
 * Created by yuri_ on 05/12/2017.
 */
@Table(database = RecipesDatabase.class)
public class Recipe extends BaseModel{

    @SerializedName("recipe_id")            //Beside that, we're going to grab it from the API but I'm going to change the name because the API brings me another name, it´s recipe_id, so that's why I´m adding this other annotation at serialized name because I'm using a different name for my table locally, and the remote data that I´m grabbing and at some moment parsing @PrimaryKey private String recipeID;
    @PrimaryKey private String recipeID;

    @Column private String title;

    @SerializedName("image_url")
    @Column private String imageURL;

    @SerializedName("source_url")
    @Column private String sourceURL;

    @Column private boolean favorite;

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean equals(Object obj){
        boolean equal = false;
        if(obj instanceof Recipe){
            Recipe recipe = (Recipe)obj;
            equal = this.recipeID.equals(recipe.getRecipeID());
        }
        return equal;
    }

}
