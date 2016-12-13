package com.ahaproject.playdeffence.TouchController;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by akihiro on 2016/12/13.
 * This class singleton  androidfhon touch manager
 */
public class TouchManager {

    //this class instance
    private static TouchManager ourInstance = new TouchManager();
    //device info
    private final int MAX_TOUCH_DEVEICE = 4;
    //public TouchInfoAccess access_device[] = new TouchInfoAccess[MAX_TOUCH_DEVEICE];

    public static TouchManager getInstance() {
        return ourInstance;
    }


    private TouchManager() {

    }

    public boolean SetTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN://おろしたとき
                Log.d("TouchON","X:"+event.getX()+"Y:"+event.getY()+"Z:" + event.getPressure());
                break;
            case MotionEvent.ACTION_MOVE://移動したとき
                Log.d("TouchMOVE","X:"+event.getX()+"Y:"+event.getY()+"Z:" + event.getPressure());
                break;
            case MotionEvent.ACTION_UP://あげたとき。
                Log.d("TouchUP","X:"+event.getX()+"Y:"+event.getY()+"Z:" + event.getPressure());
                break;
        }

        return false;
    }



}
