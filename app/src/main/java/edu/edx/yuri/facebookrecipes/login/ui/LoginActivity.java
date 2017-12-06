package edu.edx.yuri.facebookrecipes.login.ui;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.edx.yuri.facebookrecipes.R;
import edu.edx.yuri.facebookrecipes.recipemain.ui.RecipeMainActivity;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.container)
    RelativeLayout container;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        if(AccessToken.getCurrentAccessToken()!=null){
            navigateToMainScreen();
        }

        callbackManager = CallbackManager.Factory.create();
        loginButton.setPublishPermissions(Arrays.asList("publish_actions"));
        //loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                navigateToMainScreen();
            }

            @Override
            public void onCancel() {
                //Log.d("tag2", "nao deu certo (oncancel) o login");
                Snackbar.make(container, R.string.login_cancel_error, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                //Log.d("tag3", "nao deu certo (onError) o login");
                String msgError = String.format(getString(R.string.login_error),
                                                    error.getLocalizedMessage());
                Snackbar.make(container, msgError, Snackbar.LENGTH_SHORT).show();
            }
        });

        //LoginManager.getInstance().registerCallback();

    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, RecipeMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //AppEventsLogger.activateApp(this);//deprecated
    }

    @Override
    protected void onPause() {
        super.onPause();
        //AppEventsLogger.deactivateApp(this);//deprecated

    }
}
