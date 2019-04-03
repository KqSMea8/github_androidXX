package com.bsoft.componentXX;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class  SampleApplication extends TinkerApplication {
    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.bsoft.componentXX.SampleApplicationLike",//这里xx对应的是你的SampleApplicationLike所在的包名。例如："com.laer.tinkerdome.SampleApplicationLike"
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
