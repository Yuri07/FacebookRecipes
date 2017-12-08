package edu.edx.yuri.facebookrecipes.recipelist.ui.adapters;

import edu.edx.yuri.facebookrecipes.entities.Recipe;

/**
 * Created by yuri_ on 06/12/2017.
 */

public interface OnItemClickListener {
    void onFavoriteClick(Recipe recipe);//esse recipe e de quem implementa o onitemclicklistener?
                            // ou alguem que tem o recipe vai chamar esse metodo passando o recipe?
    void onItemClick(Recipe recipe);
    void onDeleteClick(Recipe recipe);
}
