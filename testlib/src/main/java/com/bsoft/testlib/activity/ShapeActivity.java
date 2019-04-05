package com.bsoft.testlib.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.baselib.base.BaseActivity;
import com.bsoft.commonlib.RouterPath;
import com.bsoft.testlib.R;
import com.noober.background.drawable.DrawableCreator;

import static com.bsoft.baselib.util.DensityUtil.dip2px;

@Route(path = RouterPath.SHAPE)
public class ShapeActivity  extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
    }

    @Override
    public void findView() {


        TextView tvTest4 = findViewById(R.id.tvTest4);
        Drawable drawable4 = new DrawableCreator.Builder().setCornersRadius(dip2px(20))
                .setPressedDrawable(ContextCompat.getDrawable(this, R.drawable.circle_like_pressed))
                .setUnPressedDrawable(ContextCompat.getDrawable(this, R.drawable.circle_like_normal))
                .build();
        tvTest4.setClickable(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            tvTest4.setBackground(drawable4);
        }else {
            tvTest4.setBackgroundDrawable(drawable4);
        }
    }
}
