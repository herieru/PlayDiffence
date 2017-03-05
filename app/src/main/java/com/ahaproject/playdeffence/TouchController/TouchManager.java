package com.ahaproject.playdeffence.TouchController;

import android.opengl.GLES20;
import android.util.Log;
import android.view.MotionEvent;

import com.ahaproject.playdeffence.Velocity.Vector3;

/**
 * Created by akihiro on 2016/12/13.
 * This class singleton  androidfhon touch manager
 */
public class TouchManager {

    //this class instance
    private static TouchManager ourInstance = new TouchManager();
    //device info
    private final int MAX_TOUCH_DEVEICE = 4;
    public TouchInfoAccess access_device[] = new TouchInfoAccess[MAX_TOUCH_DEVEICE];

    public static TouchManager getInstance() {
        return ourInstance;
    }

    public boolean g = false;

    private TouchManager() {

    }

    public boolean SetTouchEvent(MotionEvent event)
    {
        boolean flg = false;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN://おろしたとき
                Log.d("TouchON","X:"+event.getX()+"Y:"+event.getY()+"Z:" + event.getPressure());
                flg = true;
                //ID追加　　
                //検索　空いている所を取得
                break;
            case MotionEvent.ACTION_MOVE://移動したとき
                Log.d("TouchMOVE","X:"+event.getX()+"Y:"+event.getY()+"Z:" + event.getPressure());
                flg =  true;
                break;
            case MotionEvent.ACTION_UP://あげたとき。
                Log.d("TouchUP","X:"+event.getX()+"Y:"+event.getY()+"Z:" + event.getPressure());
                flg = false;//ここがちょい微妙
                //検索　削除
                break;
            default:
                Log.d("Default","Switch Default TouchManager");
                flg =  true;
                break;
        }
        g = flg;
        //情報を収めるのに成功したらtrue
        if(flg)
            return true;
        return false;
    }

    public boolean NowTouchFlg()
    {
        return g;
    }

    //最初のIDでとられているタッチの位置を取得
    public Vector3 GetTouchPositionDefault()
    {
        Vector3 pos = new Vector3();
        //アクセスしたIDの箇所を流す。



        return pos;
    }






}
