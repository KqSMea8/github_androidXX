package com.bsoft.commonlib.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.bsoft.commonlib.R;
import com.bsoft.commonlib.recyleviewadapter.wrapper.LoadMoreHorizontalDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;


/**
 * Created by Administrator on 2017/5/4.
 */
public class RecyclerViewUtil {

    public static void initLinearV(Context baseContext, RecyclerView recyclerview){
        initLinearV(baseContext, recyclerview, R.color.transparent, R.dimen.dp0, R.dimen.dp0, R.dimen.dp0);
    }
    public static void initLinearV(Context baseContext, RecyclerView recyclerview,
                                   int color, int size){
        initLinearV(baseContext, recyclerview, color, size, R.dimen.dp0, R.dimen.dp0);
    }

    public static void initLinearV(Context baseContext, RecyclerView recyclerview,
                                   int color, int size, int marginLeft, int marginRight){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(baseContext)
                        .color(ContextCompat.getColor(baseContext, color))
                        .sizeResId(size)
                        .marginResId(marginLeft, marginRight)
                        .build());
    }

    //add Bug_id:none 通知居民 chenkai 20170908 start
    public static void initLoadMoreLinearV(Context baseContext, RecyclerView recyclerview,
                                           int color, int size, int marginLeft, int marginRight) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(
                new LoadMoreHorizontalDecoration.Builder(baseContext)
                        .color(ContextCompat.getColor(baseContext, color))
                        .sizeResId(size)
                        .marginResId(marginLeft, marginRight)
                        .build());
    }
    //add Bug_id:none 通知居民 chenkai 20170908 end

    public static void initLinearH(Context baseContext, RecyclerView recyclerview){
        initLinearH(baseContext, recyclerview, R.color.transparent, R.dimen.dp0, R.dimen.dp0, R.dimen.dp0);
    }
    public static void initLinearH(Context baseContext, RecyclerView recyclerview,
                                   int color, int size){
        initLinearH(baseContext, recyclerview, color, size, R.dimen.dp0, R.dimen.dp0);
    }

    public static void initLinearH(Context baseContext, RecyclerView recyclerview,
                                   int color, int size, int marginLeft, int marginRight){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(
                new VerticalDividerItemDecoration.Builder(baseContext)
                        .color(ContextCompat.getColor(baseContext, color))
                        .sizeResId(size)
                        .marginResId(marginLeft, marginRight)
                        .build());
    }
    public static void initGrid(Context baseContext, RecyclerView rv, int gridColumn,
                                int color, int size, int marginLeft, int marginRight){
        initGrid(baseContext, rv, gridColumn,
            color, size, marginLeft, marginRight,
            color, size, marginLeft, marginRight
            );
    }
    public static void initGrid(Context baseContext, RecyclerView rv, int gridColumn,
                                int color, int size){
        initGrid(baseContext, rv, gridColumn,
            color, size, R.dimen.dp0, R.dimen.dp0,
            color, size, R.dimen.dp0, R.dimen.dp0
            );
    }

    /**
     *
     * @param baseContext
     * @param rv
     * @param gridColumn
     * @param colorH
     * @param sizeH
     * @param marginLeftH
     * @param marginRightH
     * @param colorV
     * @param sizeV
     * @param marginLeftV
     * @param marginRightV
     */
    public static void initGrid(Context baseContext, RecyclerView rv, int gridColumn,
                                int colorH, int sizeH, int marginLeftH, int marginRightH,
                                int colorV, int sizeV, int marginLeftV, int marginRightV
                                ){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(baseContext, gridColumn);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return 3 - position % 3;
//            }
//        });
        rv.setLayoutManager(gridLayoutManager);
        rv.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(baseContext)
                        .color(ContextCompat.getColor(baseContext, colorH))
                        .sizeResId(sizeH)
                        .marginResId(marginLeftH, marginRightH)
                        .build());
        rv.addItemDecoration(
                new VerticalDividerItemDecoration.Builder(baseContext)
                        .color(ContextCompat.getColor(baseContext, colorV))
                        .sizeResId(sizeV)
                        .marginResId(marginLeftV, marginRightV)
                        .showLastDivider()
                        .build());
    }
}
