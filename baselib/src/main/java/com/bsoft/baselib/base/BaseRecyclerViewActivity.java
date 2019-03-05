package com.bsoft.baselib.base;

/**
 * Created by diwang on 2018/6/14.
 */

import android.view.View;
import android.widget.Toast;


import com.bsoft.baselib.R;
import com.bsoft.baselib.util.DensityUtil;
import com.bsoft.baselib.widget.loading.LoadViewHelper;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * 下拉刷新
 */
public abstract class BaseRecyclerViewActivity extends BaseActivity {

    public PtrFrameLayout frame;
    String loadingText = "Loading...";
    protected static final int FIRST_PAGE = 1;
    protected int pageNo = FIRST_PAGE;
    protected int pageSize = 20;

    public void initPtrFrameLayout(){
        frame = (PtrFrameLayout) findViewById(R.id.ptrFrameLayout);
        if(frame != null) {
            final StoreHouseHeader header = new StoreHouseHeader(baseContext);
            header.setPadding(0, DensityUtil.dip2px(baseContext, 15), 0, 0);
            header.setTextColor(R.color.gray);

            /**
             * using a string, support: A-Z 0-9 - .
             * you can add more letters by {@link in.srain.cube.views.ptr.header.StoreHousePath#addChar}
             */
            header.initWithString(loadingText);

            if (frame != null) {
                frame.addPtrUIHandler(new PtrUIHandler() {


                    @Override
                    public void onUIReset(PtrFrameLayout frame) {
                        header.initWithString(loadingText);
                    }

                    @Override
                    public void onUIRefreshPrepare(PtrFrameLayout frame) {


                    }

                    @Override
                    public void onUIRefreshBegin(PtrFrameLayout frame) {

                    }

                    @Override
                    public void onUIRefreshComplete(PtrFrameLayout frame) {

                    }

                    @Override
                    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

                    }
                });

                frame.setHeaderView(header);
                frame.addPtrUIHandler(header);
                frame.setPtrHandler(new PtrHandler() {
                    @Override
                    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                    }

                    @Override
                    public void onRefreshBegin(final PtrFrameLayout frame) {
                        onRefresh();
                    }
                });
            }
        }
        View base=findViewById(R.id.loadLay);
        if (base!=null){
            viewHelper = new LoadViewHelper(base);
            viewHelper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefresh();
                }
            });
        }
    }

    public void setEnable(boolean flag){
        if(frame != null) {
            frame.setEnabled(flag);
        }
    }

    public void refreshComplete(){
        if(frame != null){
            frame.refreshComplete();
        }
    }

    public boolean isFirstPage(){
        return pageNo == FIRST_PAGE;
    }

    @Override
    public void showErrorView() {
        if(pageNo != FIRST_PAGE){
            pageNo --;
        }
        if(isEmpty()){
            super.showErrorView();
        }else{
            Toast.makeText(getApplicationContext(), "加载失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErrorView(String error) {
        if(pageNo != FIRST_PAGE){
            pageNo --;
        }
        if(isEmpty()){
            super.showErrorView(error);
        }else{
            Toast.makeText(getApplicationContext(),error, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void showEmptyView() {
        if(isEmpty()){
            super.showEmptyView();
        }else{
          //  EToastUtils.show("数据为空");
            Toast.makeText(getApplicationContext(),"数据为空", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void showEmptyView(View view){
        if(isEmpty()){
            super.showEmptyView(view);
        }else{
          //  EToastUtils.show("数据为空");
            Toast.makeText(baseContext,"数据为空", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void showLoadingView() {
        if(isEmpty()){
            super.showLoadingView();
        }
    }

    //刷新操作
    public abstract void onRefresh();
    //加载更多操作
    protected void onLoadMore(){};

    //是否已经有数据
    public abstract boolean isEmpty();



}
