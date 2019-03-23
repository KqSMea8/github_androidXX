package com.bsoft.module_main.bean;





import com.bsoft.baselib.bean.BaseVo;

import java.util.ArrayList;

/**
 * 模块配置
 * Created by Administrator on 2016/12/22.
 */
public class ModuleVo extends BaseVo implements Comparable<ModuleVo> {


    /**
     * menuId : 7
     * productId : 1
     * menuName : 消息
     * menuPid : -1
     * menuOrder : 2
     * menuIconId : 0
     * moduleId : 0
     * childMenus : [{"menuId":8,"productId":1,"menuName":"消息列表","menuPid":7,"menuOrder":1,"menuIconId":0,"moduleId":10,"moduleCode":"messageManage","moduleName":"消息列表","linkedType":"02"}]
     */

    public int menuId;
    public int productId;
    public String menuName;
    public int menuPid;
    public int menuOrder;
    public int menuIconId;
    public int moduleId;
    public String moduleCode;
    public ArrayList<ModuleVo> childMenus;

    public ModuleVo() {

    }

    public ModuleVo(String menuName, int menuIconId) {
        this.menuName = menuName;
        this.menuIconId = menuIconId;
    }

    @Override
    public int compareTo(ModuleVo o) {

        return this.menuOrder - o.menuOrder;
    }

}
