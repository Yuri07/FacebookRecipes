package edu.edx.yuri.facebookrecipes.libs.base;

import android.widget.ImageView;

/**
 * Created by yuri_ on 23/11/2017.
 */

public interface ImageLoader {
    void load(ImageView imageView, String URL);
    void setOnFinishedImageLoadingListener(Object listener);//listener, the idea here is that when the image finishes loading, I'm going to have a callback available have a callback available, and I'm receiving a Glide, this is generic but I have a glide specific object, that's why on the specific implementation I'm going to use it, but in case I change from glide to another library, I can change that to a different callback
}
