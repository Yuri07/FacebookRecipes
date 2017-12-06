package edu.edx.yuri.facebookrecipes.libs;

import android.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;

import edu.edx.yuri.facebookrecipes.libs.base.ImageLoader;


/**
 * Created by yuri_ on 23/11/2017.
 */

public class GlideImageLoader implements ImageLoader {

    private RequestManager glideRequestManager;
    private RequestListener onFinishedLoadingListener;
    private static final RequestOptions requestDiskCacheStragegy = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL);

    public GlideImageLoader(RequestManager glideRequestManager){//usando fragment pq trabalharemos com tabs//RequestManager glideRequestManager){
        this.glideRequestManager = glideRequestManager;//Glide.with(fragment);//glideRequestManager;
    }

    @Override
    public void load(ImageView imageView, String URL) {
        if(onFinishedLoadingListener != null) {

            glideRequestManager
                    .load(URL)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .listener(onFinishedLoadingListener)
                    .into(imageView);
        }else{
            glideRequestManager
                    .load(URL)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .into(imageView);
        }
    }

    @Override
    public void setOnFinishedImageLoadingListener(Object listener) {
        if(listener instanceof  RequestListener) {
            this.onFinishedLoadingListener = (RequestListener) listener;
        }
    }
}
