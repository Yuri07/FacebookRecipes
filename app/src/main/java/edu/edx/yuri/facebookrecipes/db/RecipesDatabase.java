package edu.edx.yuri.facebookrecipes.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by yuri_ on 05/12/2017.
 */
@Database(name = RecipesDatabase.NAME, version = RecipesDatabase.VERSION)
public class RecipesDatabase  {
    public static final int VERSION = 2;
    public static final String NAME = "Recipes";
}
