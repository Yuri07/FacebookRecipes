package edu.edx.yuri.facebookrecipes.libs.di;



import android.app.Activity;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.edx.yuri.facebookrecipes.libs.GlideImageLoader;
import edu.edx.yuri.facebookrecipes.libs.GreenRobotEventBus;
import edu.edx.yuri.facebookrecipes.libs.base.EventBus;
import edu.edx.yuri.facebookrecipes.libs.base.ImageLoader;


/**
 * Created by yuri_ on 23/11/2017.
 */
@Module
public class LibsModule {

    private Activity activity;

    public LibsModule(Activity activity){
        this.activity = activity;
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(Activity activity){
        return Glide.with(activity);
    }

    @Provides
    @Singleton
    Activity providesFragment(){
        return this.activity;
    }

    @Provides
    @Singleton
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus){
        return new GreenRobotEventBus(eventBus);
    }

    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus providesLibraryEventBus(){
        return org.greenrobot.eventbus.EventBus.getDefault();
    }



}
