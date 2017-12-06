package edu.edx.yuri.facebookrecipes;

import android.app.Application;
import android.content.Intent;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.raizlabs.android.dbflow.config.FlowManager;

import edu.edx.yuri.facebookrecipes.login.ui.LoginActivity;

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
}
