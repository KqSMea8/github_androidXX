package com.bsoft.baselib.mvpbase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hugege on 2018/4/3.
 */

public abstract class BasePresenter<V extends IView> {

    private V view;
    private V proxyView;

    private boolean isNull(){
        if(this.view==null){
         return true;
        }
        return false;
    }

    public void attachView(V view) {
        this.view=view;
        ClassLoader loader  =  view.getClass().getClassLoader();//类加载器
        Class<?>[] interfaces = view.getClass().getInterfaces();//代理接口
        InvocationHandler handler= new MvpViewInvocationHandler(this.view);//方法回调
        proxyView=(V) Proxy.newProxyInstance(loader,interfaces,handler);
    }
    public class MvpViewInvocationHandler implements InvocationHandler {
     private IView view;
        MvpViewInvocationHandler(IView view){
            this.view=view;
        }
        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            if(isNull()) {
                return null;
            }
            return method.invoke(view,objects);
        }
    };

    public void detachView() {
        this.view=null;
    }
    protected V getView() {
        return proxyView;
    }

}
