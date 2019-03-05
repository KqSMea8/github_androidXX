package com.bsoft.componentXX;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bsoft.baselib.base.BaseActivity;

import com.bsoft.baselib.widget.BsoftActionBar;

public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        findView();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("asaaaaaaaaaaaaaaa");


        actionBar.addAction(new BsoftActionBar.Action() {
            @Override
            public int getDrawable() {
                return 0;
            }

            @Override
            public String getText() {
                return "qqqqq";
            }

            @Override
            public void performAction(View view) {
                    showToast("qwqw");
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

}
