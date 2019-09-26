package cn.noname.app.loader;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import com.bumptech.glide.Glide;


import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.youth.banner.loader.ImageLoader;

import cn.noname.app.widget.GlideCircleWithBorder;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class GlideImageLoader extends ImageLoader {
    private int loadingType;
    public static int ROUNDED=1;
    public GlideImageLoader() {
    }
    public GlideImageLoader(int loadingType) {
        this.loadingType=loadingType;
    }

    DrawableCrossFadeFactory factory =new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
    RequestOptions requestOptions = RequestOptions.circleCropTransform();
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if(loadingType==1) {
            //Glide.with(context).load(path).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
            //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
            Glide.with(context.getApplicationContext())
                    .load(path)

                    //.error(R.drawable.ic_default_color)//
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)//
                    //.crossFade()//
                    .transition(withCrossFade())
                    .transform(new GlideCircleWithBorder(2, Color.parseColor("#ccffffff")))
                    //.apply(requestOptions)
                    .into(imageView);
        }
        else
        {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    //.placeholder(R.drawable.icon_image_holder)
                    .transition(withCrossFade())
                    .into(imageView);
        }
    }

}
