package com.bsoft.module_main.bean.banner;


import com.bsoft.baselib.bean.BaseVo;

/**
 * 广告
 * Created by Administrator on 2016/12/26.
 */
public class BannerVo extends BaseVo implements Comparable<BannerVo> {

    /**
     * fileName : file
     * linkType : 00
     * description : 患者App-Android-首页-广告1
     * path : /coms/f8b7a89c-fb1e-43fc-8356-aa5675f02532/20161223/1482470584098.
     * tenantPId : 2
     * fileSize : 165846
     * name : 患者App-Android-首页-广告1
     * picture : 54
     * position : 0102
     * orderNo : 1
     */

    public String fileName;
    /*
    00	无链接
    01	外链接
    02	内链接
    03	文字
     */
    public String linkType;
    public String linkAddress;//外连接时为广告链接地址，内链接时为模块code
    public String description;//广告描述
    public String path;//广告图片文件路径
    public int tenantPId;
    public int fileSize;
    public String name;
    public String linkText;
    public int picture;//广告图片文件ID(真正地址)
    public String position;
    public int orderNo;//广告排序

    /** 本地图片资源文件id **/
    public int resId=-1;
    @Override
    public int compareTo(BannerVo o) {
        return this.orderNo - o.orderNo;
    }

    @Override
    public boolean equals(Object obj) {
        BannerVo vo = (BannerVo) obj;
        if(vo == null) {return false;}
      //  return TextUtils.equals(picture, vo.picture);
        return picture==vo.picture;
    }
}
