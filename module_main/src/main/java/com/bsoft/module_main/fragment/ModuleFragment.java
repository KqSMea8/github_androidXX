package com.bsoft.module_main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.baselib.util.EffectUtil;
import com.bsoft.commonlib.Constants;
import com.bsoft.commonlib.recyleviewadapter.CommonAdapter;
import com.bsoft.commonlib.recyleviewadapter.MultiItemTypeAdapter;
import com.bsoft.commonlib.recyleviewadapter.base.ViewHolder;
import com.bsoft.commonlib.util.ImageSizeUtil;
import com.bsoft.commonlib.util.ImageUrlUtil;
import com.bsoft.commonlib.util.ImageUtil;
import com.bsoft.commonlib.util.RecyclerViewUtil;
import com.bsoft.commonlib.util.StringUtil;
import com.bsoft.module_main.R;
import com.bsoft.module_main.bean.ModuleVo;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class ModuleFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ModuleVo> dataList;
    ModuleAdapter adapter;

    LayoutInflater mLayoutInflater;
    
    public static ModuleFragment newInstance(ArrayList<ModuleVo> moduleVos){
        ModuleFragment fragment = new ModuleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("datas", moduleVos);
        fragment.setArguments(bundle);
        return fragment;
    } 
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            dataList = (ArrayList<ModuleVo>) bundle.getSerializable("datas");
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recyclerView = new RecyclerView(getActivity());

        return recyclerView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mLayoutInflater = (LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        RecyclerViewUtil.initGrid(getActivity(), recyclerView, 4, R.color.transparent, R.dimen.dp0);
        adapter = new ModuleAdapter(dataList);
        adapter.setContext(getContext());
        adapter.setOnItemClickListener(moduleAdapterListener);
        recyclerView.setAdapter(adapter);
    }

    MultiItemTypeAdapter.OnItemClickListener moduleAdapterListener
            = new MultiItemTypeAdapter.OnItemClickListener<ModuleVo>() {
        @Override
        public void onItemClick(ViewGroup parent, View view, ViewHolder holder, List<ModuleVo> datas, int position) {
            ModuleVo item = datas.get(position);
            Intent intent = null;
            Toast.makeText(getActivity().getApplicationContext(), "暂未开放", Toast.LENGTH_SHORT).show();
//            ARouter.getInstance()
//                    .build(RouterPath.PAY_HOME_ACTIVITY)
//                    .navigation(getActivity(), new LoginNavigationCallBack());

           // EToastUtils.show("暂未开放");
            //// TODO: 2017/9/17 wait complete 
//            if(TextUtils.equals("预约挂号", item.menuName)){
//                intent = AppointModuleUtil.getStartAct(getActivity());
//                startActivity(intent);
//            }else if(TextUtils.equals("医学功能", item.menuName)) {
//                Intent web = new Intent(getActivity(), WebActivity.class);
//                web.putExtra("title", "健康百科");
//                web.putExtra("url", "http://115.236.19.147:14188/ckbserver/view/index");
//                startActivity(web);
//            }else {
//                    Toast.makeText(getActivity().getApplicationContext(), "暂未开放", Toast.LENGTH_SHORT).show();
//            }
            
//            if(indexInfo ==null){
//                intent=ServiceUtil.getIntent(baseContext, item);
//            }else{
//                intent=ServiceUtil.getIntent(baseContext, item, indexInfo.user.mpiId);
//            }
//            if (intent != null) {
//                if(loginUser.isVisitor()){
//                    showToast("游客不能使用该服务");
//                }else if(indexInfo.memberType == IndexVo.TYPE_REGISTER){
//                    showToast("请先完善信息");
//                }else {
//                    startActivity(intent);
//                }
//            }else{
//                showToast("服务暂未开放");
//            }
        }

        @Override
        public void onItemViewClick(View view, ViewHolder holder, ModuleVo item, int position, int tPos) {

        }

        @Override
        public boolean onItemLongClick(ViewGroup parent, View view, ViewHolder holder, List<ModuleVo> datas, int position) {
            return false;
        }
    };

    static class ModuleAdapter extends CommonAdapter<ModuleVo> {

    Context context;
        public ModuleAdapter(ArrayList<ModuleVo> datas) {
            super(R.layout.item_home_module, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ModuleVo item, int position) {
//           
            SimpleDraweeView dvHeader = holder.getView(R.id.dvHeader);
            TextView tvName = holder.getView(R.id.tvName);
            //url表示加载图片地址，first表示的是显示图片的imageview
            //

            ImageUtil.showNetWorkImage(dvHeader,null,item.menuIconId);
//            ImageUtil.showNetWorkImage(dvHeader,
//                    ImageSizeUtil.getHeaderUrl(
//                            ImageUrlUtil.getUrl(Constants.HttpImgUrl ,item.menuIconId),
//                            dvHeader.getLayoutParams().width),R.drawable.avatar_none);
            tvName.setText(StringUtil.notNull(item.menuName));

            EffectUtil.addClickEffect(holder.getConvertView());

        }

        public void setContext(Context mContext) {
            this.context=mContext;
    }
    }
}
