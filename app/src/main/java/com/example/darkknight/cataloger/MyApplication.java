package com.example.darkknight.cataloger;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context context;
    private static String user_id;
    private static String list_name;
    /*
    private static String user_id;
    private static String user_id;*/

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();

    }

    public static String getUser_id(){
        return MyApplication.user_id;
    }

    public static String getList_name() {
        return MyApplication.list_name;
    }

    public static void setUser_id(String user_id) {
        MyApplication.user_id = user_id;
    }

    public static void setList_name(String list_name) {
        MyApplication.list_name = list_name;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}

