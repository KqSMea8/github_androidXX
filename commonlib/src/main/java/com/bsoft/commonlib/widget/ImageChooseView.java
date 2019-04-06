package com.bsoft.commonlib.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bsoft.baselib.util.EffectUtil;
import com.bsoft.baselib.util.LiveDataBus;
import com.bsoft.baselib.util.StringUtil;
import com.bsoft.commonlib.R;
import com.bsoft.commonlib.recyleviewadapter.CommonAdapter;
import com.bsoft.commonlib.recyleviewadapter.MultiItemTypeAdapter;
import com.bsoft.commonlib.recyleviewadapter.base.ViewHolder;
import com.bsoft.commonlib.util.RecyclerViewUtil;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

public class ImageChooseView extends RelativeLayout {
    
    private LayoutInflater mMInflater;
    private RelativeLayout contentView;
    private Context mContext;
    private TextView mTvChooseName;
    private RecyclerView mRecyclerviewImage;
    private TextView mTvCount;
    int MINCOUNT =0;
    int MAXCOUNT =4;
    int NUM=3;
    private PicAdapter mAdapter;
    private ArrayList<AlbumFile> mAlbumFiles=new ArrayList<>();
    private ArrayList<AlbumFile> mAlbumFilesCache=new ArrayList<>();
    public ImageChooseView(Context context) {
        super(context);
        this.mContext=context;
        init(context);
    }

    public ImageChooseView(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.mContext=context;
        init(context);
    }

    public ImageChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        init(context);
    }
    private void init(Context context) {
        mMInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = (RelativeLayout) mMInflater.inflate(R.layout.layout_image_choose, null);
        addView(contentView);
        mTvChooseName = (TextView) contentView.findViewById(R.id.tvChooseName);
        mRecyclerviewImage = (RecyclerView) contentView.findViewById(R.id.recyclerviewImage);
        mTvCount = (TextView) contentView.findViewById(R.id.tvCount);
    }

    public void initView(){
        RecyclerViewUtil.initGrid(mContext, mRecyclerviewImage, NUM, R.color.transparent, R.dimen.dip_15);
        mAlbumFilesCache.add(new AlbumFile());
        mAdapter = new PicAdapter(mAlbumFilesCache);
        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerviewImage.setAdapter(mAdapter);
    }
    //设置初始值
    public void setTittleAndMaxCount(String str, int num, int minNum, int naxNum){
        this.MINCOUNT=minNum;
        this.MAXCOUNT=naxNum;
        this.NUM=num;
        mTvChooseName.setText(str);
        mTvCount.setText("0/"+MAXCOUNT);
        initView();
    }
    //设置图片选择个数
    public void setImageCount(int num){
        mTvCount.setText(num+"/"+MAXCOUNT);
    }
    /**
     * 获取图片集合
     * @return
     */
    public ArrayList<String> getLocalPaths(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < mAlbumFiles.size(); i++){
            list.add(mAlbumFiles.get(i).getPath());
        }
        return list;
    }

    MultiItemTypeAdapter.OnItemClickListener<AlbumFile> onItemClickListener = new MultiItemTypeAdapter.OnItemClickListener<AlbumFile>() {
        @Override
        public void onItemClick(ViewGroup parent, View view, ViewHolder holder, List<AlbumFile> datas, int position) {
            if(StringUtil.isEmpty(datas.get(position).getThumbPath())){
                selectAlbum();
            }else{
                previewImage(position);
            }
        }

        @Override
        public void onItemViewClick(View view, ViewHolder holder, AlbumFile item, int position, int tPos) {
            if (view.getId() == R.id.iv_delete) {
                deletItem(item,position);

            }
        }

        @Override
        public boolean onItemLongClick(ViewGroup parent, View view, ViewHolder holder, List<AlbumFile> datas, int position) {
            return false;
        }
    };

    private void deletItem(AlbumFile item, int position) {
        if(endPicHasData()){
            mAlbumFiles.remove(item);
            mAlbumFilesCache.remove(item);
            mAdapter.remove(position);
            mAdapter.setDatas(mAlbumFilesCache);
            mAdapter.notifyDataSetChanged();

        }else {
            mAlbumFiles.remove(item);
            mAdapter.remove(position);
            mAdapter.notifyDataSetChanged();
        }

     //   EventBus.getDefault().post(new Event());
        LiveDataBus.get().with("key_name").setValue("");
        setImageCount(mAlbumFiles.size());
    }

    /**
     * Select picture, from album.
     */
    private void selectAlbum() {
        mAlbumFilesCache.clear();
        Album.album(mContext).multipleChoice().columnCount(MINCOUNT).selectCount(MAXCOUNT)
                .camera(true).cameraVideoQuality(1).cameraVideoLimitDuration(Integer.MAX_VALUE)
                .cameraVideoLimitBytes(Integer.MAX_VALUE).checkedList(mAlbumFiles)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        putData(mAlbumFiles);

                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        putData(mAlbumFiles);//把原先数据再重新设置一遍
                        Toast.makeText(mContext, "取消", Toast.LENGTH_LONG).show();
                    }
                })
                .start();


//        Album.image(mContext)
//                .singleChoice()
//                .camera(true)
//                .onResult(new Action<ArrayList<AlbumFile>>() {
//                    @Override
//                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
//                        mAlbumFiles = result;
//                        putData(mAlbumFiles);
//                    }
//                })
//                .onCancel(new Action<String>() {
//                    @Override
//                    public void onAction(@NonNull String result) {
//                        putData(mAlbumFiles);//把原先数据再重新设置一遍
//                        Toast.makeText(mContext, "取消", Toast.LENGTH_LONG).show();
//                    }
//                })
//                .start();
    }

    private void putData(ArrayList<AlbumFile> list) {

        if(mAlbumFiles.size()<MAXCOUNT){
            mAlbumFilesCache.addAll(0,list);
            mAlbumFilesCache.add(new AlbumFile());
            mAdapter.setDatas(mAlbumFilesCache);
        }else {
            mAlbumFilesCache.addAll(0,list);
            mAlbumFilesCache.add(new AlbumFile());
            mAdapter.setDatas(list);
        }
        setImageCount(mAlbumFiles.size());

     //   EventBus.getDefault().post(new Event());
        LiveDataBus.get().with("key_name").setValue("");
    }

    /**
     * Preview image, to album.
     */
    private void previewImage(int position) {
        Album.galleryAlbum(mContext).checkable(false).checkedList(mAlbumFiles).currentPosition(position)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAdapter.setDatas(mAlbumFiles);
                    }
                })
                .start();
    }

    /**
     * 判断最后一个imageview是否选择了图片
     * @return
     */
    private boolean endPicHasData(){
        return !mAlbumFiles.isEmpty() &&mAlbumFiles.size()==MAXCOUNT;
    }

    static class PicAdapter extends CommonAdapter<AlbumFile> {


        public PicAdapter(List<AlbumFile> datas) {
            super(R.layout.item_pic,datas);
        }
        @Override
        protected void convert(final ViewHolder holder, final AlbumFile item, final int position) {
//
          //  SimpleDraweeView ivPic = holder.getView(R.id.iv_pic);
            ImageView ivPic = holder.getView(R.id.iv_pic);
            ImageView ivDelete = holder.getView(R.id.iv_delete);
            if(StringUtil.isEmpty(item.getPath())){
                ivPic.setImageResource(R.drawable.pic_add);
                ivDelete.setVisibility(View.INVISIBLE);
                EffectUtil.addClickEffect(holder.getConvertView());
            }else {
                Album.getAlbumConfig().getAlbumLoader().load(ivPic, item);
                ivDelete.setVisibility(View.VISIBLE);
                EffectUtil.addClickEffect(holder.getConvertView());
                EffectUtil.addClickEffect(ivDelete);
                ivDelete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemViewClick(v, holder, item, position, -1);
                        }
                    }
                });
            }
        }
    }

}

