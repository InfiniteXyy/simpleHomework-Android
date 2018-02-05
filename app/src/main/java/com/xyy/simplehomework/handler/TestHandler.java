package com.xyy.simplehomework.handler;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by xyy on 2018/2/5.
 */

public class TestHandler {
    private Context mContext;
    public TestHandler(Context context) {
        mContext = context;
    }
    public void onClickDemo(View view) {
        Toast.makeText(mContext, "test", Toast.LENGTH_SHORT).show();
    }
}
