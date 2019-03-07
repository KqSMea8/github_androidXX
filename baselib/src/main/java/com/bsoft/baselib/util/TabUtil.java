package com.bsoft.baselib.util;

import android.content.res.Resources;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import io.reactivex.annotations.NonNull;

public class TabUtil {


    public static void setIndicatorWidth(@NonNull final TabLayout tabLayout, final int margin) {

    //    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {//  compileSdkVersion版本升级至28后
            // 拿到tabLayout的slidingTabIndicator属性
            try {
                Field slidingTabIndicatorField = tabLayout.getClass().getDeclaredField("slidingTabIndicator");
                slidingTabIndicatorField.setAccessible(true);
                LinearLayout mTabStrip = (LinearLayout) slidingTabIndicatorField.get(tabLayout);
                int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, Resources.getSystem().getDisplayMetrics());
                int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, Resources.getSystem().getDisplayMetrics());

                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    View tabView = mTabStrip.getChildAt(i);
                    //拿到tabView的mTextView属性
                    Field textViewField = tabView.getClass().getDeclaredField("textView");
                    textViewField.setAccessible(true);
                    TextView mTextView = (TextView) textViewField.get(tabView);
                    tabView.setPadding(0, 0, 0, 0);
                    // 因为想要的效果是字多宽线就多宽，所以测量mTextView的宽度
                    int width = mTextView.getWidth();
                    if (width == 0) {
                        mTextView.measure(0, 0);
                        width = mTextView.getMeasuredWidth();
                    }
                    // 设置tab左右间距,注意这里不能使用Padding,因为源码中线的宽度是根据tabView的宽度来设置的
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                    params.width = width;
                    params.leftMargin = left;
                    params.rightMargin = right;
                    tabView.setLayoutParams(params);
                    tabView.invalidate();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

//        } else {
//
//            try {
//                Field tabStrip = tabLayout.getClass().getDeclaredField("mTabStrip");
//                tabStrip.setAccessible(true);
//                LinearLayout llTab = (LinearLayout) tabStrip.get(tabLayout);
//                int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, Resources.getSystem().getDisplayMetrics());
//                int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, Resources.getSystem().getDisplayMetrics());
//
//                for (int i = 0; i < llTab.getChildCount(); i++) {
//                    View child = llTab.getChildAt(i);
//                    child.setPadding(0, 0, 0, 0);
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//                    params.leftMargin = left;
//                    params.rightMargin = right;
//                    child.setLayoutParams(params);
//                    child.invalidate();
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            }
//        }
    }

}