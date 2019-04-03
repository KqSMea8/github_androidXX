package com.bsoft.commonlib.util;


import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bsoft.baselib.AppContext;
import com.bsoft.baselib.util.DensityUtil;
import com.bsoft.commonlib.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

public class FrescoImageLoader implements AlbumLoader {


    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {


        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_preview_image)	//加载成功之前占位图
                .error(R.drawable.ic_preview_image)	//加载错误之后的错误图
           //     .override(400,400)	//指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                .fitCenter()
//指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop()
              //  .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
                .skipMemoryCache(true)	//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
                .diskCacheStrategy(DiskCacheStrategy.NONE)	//跳过磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片
                ;
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);




//
//        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(AppContext.getContext().getResources())
//                .setFadeDuration(300)
//                .setPlaceholderImage(R.drawable.ic_preview_image)   // 占位图
//                .setFailureImage(R.drawable.ic_preview_image)       // 加载失败图
//                .setProgressBarImage(new ProgressBarDrawable())     // loading
//                .build();
//
//        final DraweeHolder<GenericDraweeHierarchy> draweeHolder = DraweeHolder.create(hierarchy, AppContext.getContext());
//        Drawable drawable = draweeHolder.getHierarchy().getTopLevelDrawable();
//        if (drawable == null) {
//            imageView.setImageResource(R.drawable.ic_preview_image);
//        } else {
//            imageView.setImageDrawable(drawable);
//        }
//
//        Uri uri = new Uri.Builder()
//                .scheme(UriUtil.LOCAL_FILE_SCHEME)
//                .path(url)
//                .build();
//        ImageRequest imageRequest = ImageRequestBuilder
//                .newBuilderWithSource(uri)
//                .setResizeOptions(new ResizeOptions(100, 100))
//                .build();
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setOldController(draweeHolder.getController())
//                .setImageRequest(imageRequest)
//                .build();
//        draweeHolder.setController(controller);
//
//

    }
}

