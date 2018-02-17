package com.xyy.simplehomework.view;

import android.app.Application;

import com.xyy.simplehomework.entity.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class App extends Application {

    public static App instance;
    private BoxStore boxStore;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        boxStore = MyObjectBox.builder().androidContext(this).build();

        new AndroidObjectBrowser(boxStore).start(this);
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

}
