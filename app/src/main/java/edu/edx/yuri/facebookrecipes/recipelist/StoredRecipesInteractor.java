package edu.edx.yuri.facebookrecipes.recipelist;

import edu.edx.yuri.facebookrecipes.entities.Recipe;

/**
 * Created by yuri_ on 06/12/2017.
 */

public interface StoredRecipesInteractor {//interactors sao para casos de uso(poderiam ficar em interactos diferentes mas foi escolhido agrupar esses dois metodos  )
    void executeUpdate(Recipe recipe);
    void executeDelete(Recipe recipe);
}
