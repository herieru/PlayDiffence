package com.ahaproject.playdeffence.JavaUsuful.ResourceControll;

import android.content.Context;

/**
 * Created by akihiro on 2017/01/17.
 * this class singleton androidhon
 */

public class ContextHave {
    private Context context;
    private static ContextHave MyInstance = new ContextHave();//自身が持っているインスタンス
    public static ContextHave  getInstance(){return MyInstance;}
    private ContextHave()
    {
        context = null;
    }
    public boolean SetContext(Context context)
    {
        this.context = context;
        return true;//仮
    }
    public Context GetContext()
    {
        return context;
    }
}
