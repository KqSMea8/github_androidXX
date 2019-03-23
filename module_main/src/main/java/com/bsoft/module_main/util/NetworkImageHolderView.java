package com.bsoft.module_main.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bsoft.baselib.util.ScreenUtil;
import com.bsoft.commonlib.Constants;
import com.bsoft.module_main.R;
import com.bsoft.module_main.bean.banner.BannerVo;


/**
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<BannerVo> {

    private ImageView draweeView;

    public NetworkImageHolderView(){

    }


    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页

      //  draweeView = (SimpleDraweeView) LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        draweeView = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        return draweeView;
    }

    @Override
    public void UpdateUI(Context context, final int position, BannerVo data) {


        if (data.resId!=-1){
            draweeView.setImageResource(data.resId);
            draweeView.setScaleType(ImageView.ScaleType.FIT_XY);
        }else{
//            ImageUtil.showNetWorkImage(draweeView, ImageSizeUtil.getBannerUrl(
//                    ImageUrlUtil.getUrl(Constants.HttpImgUrl, data.picture+"")
//                    , ScreenUtil.getScreenWidth()), R.drawable.pic_default);
}


    }
}
