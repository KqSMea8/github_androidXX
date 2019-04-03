# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


####################################################################################################
#关键字说明：
#-libraryjars class_path //应用的依赖包，如Android-support-v4
#-keep [,modifier,...] class_specification //这里的keep就是保持的意思，意味着不混淆某些类
#-keepclassmembers [,modifier,...] class_specification //同样的保持，不混淆类的成员
#-keepclasseswithmembers [,modifier,...] class_specification //不混淆类及其成员
#-keepnames class_specification //不混淆类及其成员名
#-keepclassmembernames class_specification //不混淆类的成员名
#-keepclasseswithmembernames class_specification //不混淆类及其成员名
#-assumenosideeffects class_specification //假设调用不产生任何影响，在proguard代码优化时会将该调用remove掉。如system.out.println和Log.v等等
#-dontwarn [class_filter] //不提示warnning

####################################################################################################
#不能混淆的项:
#1、在AndroidManifest中配置的类，比如四大组件
#2、JNI调用的方法
#3、反射用到的类
#4、WebView中JavaScript调用的方法
#5、Layout文件引用到的自定义View
#6、一些引入的第三方库

#注意，混淆后，测试不充分可能导致某些功能不能使用
####################################################################################################


#1、基本指令区
####################################################################################################

# 代码混淆压缩比，在0和7之间，默认为5，一般不需要改
-optimizationpasses 5
# 混淆时不使用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames
# 不混淆第三方引用的库
-dontskipnonpubliclibraryclasses
# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers
# 不做预校验，加快混淆速度
-dontpreverify
# 忽略警告
-ignorewarning

# 使混淆后生成映射文件，包含有类名->混淆后类名的映射关系
-verbose
#指定映射文件的名称
-printmapping proguardMapping.txt
#抛出异常时保留代码行号，在异常分析中可以方便定位
-keepattributes SourceFile,LineNumberTable

# 指定混淆时采用的算法，后面的参数是一个过滤器，这个过滤器是谷歌推荐的算法，一般不改变
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

####################################################################################################


#2、需要保留的
####################################################################################################

# 保持基本组件类不需要混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.multidex.MultiDexApplication
-keep class android.support.** {*;}
#保持这个v4、v7包下面的所有的类里面的所有内容都不混淆
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v7.app.AppCompatActivity
# 对于R（资源）下的所有类及其方法，都不能被混淆
-keep class **.R$* {
    *;
}

# 保持所有的本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview { #JS功能
    public *;
}

-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}

-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}


#保护代码中的Annotation注解不被混淆
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation {*;}

# 避免混淆泛型与反射
-keepattributes Signature
-keepattributes EnclosingMethod

# 不混淆内部类
-keepattributes InnerClasses

# 枚举类不能被混淆
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

# 保留自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View {
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留Parcelable序列化的类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# 保留在Activity中的方法参数是view的方法，
# 从而我们在layout里面编写onClick就不会被影响
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

#自己包下
#不要混淆该类所有子类的属性与方法
-keepclasseswithmembers class * extends com.bsoft.baselib.bean.AbsBaseVo{
    <fields>;
    <methods>;
}

####################################################################################################




#3、App本身的
####################################################################################################

#baidu
-keep class com.baidu.** {*;}
-keep class mapsdkvi.com.** {*;}
-dontwarn com.baidu.**

#RXJAVA
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent

#AVLoadingIndicatorView
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }


#liveDataBus混淆
-dontwarn android.arch.lifecycle.LiveData
-keep class android.arch.lifecycle.LiveData { *; }

#arouter阿里路由
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}


#fastjson解析
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *;}
-keep class com.alibaba.fastjson.*.*



#Fresco图片加载
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
@com.facebook.common.internal.DoNotStrip *;
}
-keepclassmembers class * {
native <methods>;
}
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**

#Fresco GIF图片加载
-keep class com.facebook.imagepipeline.animated.factory.AnimatedFactoryImpl {
public AnimatedFactoryImpl(com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory,com.facebook.imagepipeline.core.ExecutorSupplier);
}


#Bugly热修复support
-dontwarn com.tencent.bugly.**support
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}
# tinker混淆规则
-dontwarn com.tencent.tinker.**
-keep class com.tencent.tinker.** { *; }




















#//tinkerMultidexKeep.pro
#//和proguard-rules.pro混淆文件同级

#tinker multidex keep patterns:
-keep public class * implements com.tencent.tinker.loader.app.ApplicationLifeCycle {
    <init>(...);
    void onBaseContextAttached(android.content.Context);
}

-keep public class * extends com.tencent.tinker.loader.TinkerLoader {
    <init>(...);
}

-keep public class * extends android.app.Application {
     <init>();
     void attachBaseContext(android.content.Context);
}

-keep class com.tencent.tinker.loader.TinkerTestAndroidNClassLoader {
    <init>(...);
}

#your dex.loader patterns here
#注意 AndroidManifest.xml中的applicaion
-keep class com.unionpay.base.UPTinkerApplication {
    <init>(...);
}

-keep class com.tencent.tinker.loader.** {
    <init>(...);
}

# ***************** Tinker 混淆
-keepattributes *Annotation*
-dontwarn com.tencent.tinker.anno.AnnotationProcessor
-keep @com.tencent.tinker.anno.DefaultLifeCycle public class *

-keep public class * extends android.app.Application {
    *;
}

-keep public class com.tencent.tinker.loader.app.ApplicationLifeCycle {
    *;
}
-keep public class * implements com.tencent.tinker.loader.app.ApplicationLifeCycle {
    *;
}

-keep public class com.tencent.tinker.loader.TinkerLoader {
    *;
}
-keep public class * extends com.tencent.tinker.loader.TinkerLoader {
    *;
}

-keep public class com.tencent.tinker.loader.TinkerTestDexLoad {
    *;
}

#your dex.loader pattern here
-keep class com.tencent.tinker.loader.**
#注意 AndroidManifest.xml中的applicaion
-keep class com.unionpay.base.UPTinkerApplication

# ***************** Tinker patch包
# 打替换apk包的时候需要
#-applymapping mapping.txt
