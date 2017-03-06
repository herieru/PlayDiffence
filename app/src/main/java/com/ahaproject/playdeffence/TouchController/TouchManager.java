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
    private int touch_cnt_now;                  //現在タッチしている数
    public TouchInfoAccess[] access_device = new TouchInfoAccess[MAX_TOUCH_DEVEICE];//タッチした情報を確保

    public static TouchManager getInstance() {
        return ourInstance;
    }

    public boolean g = false;

    private TouchManager() {
        touch_cnt_now = 0;
        for(int i = 0; i < MAX_TOUCH_DEVEICE;i++)
        {
            access_device[i] = new TouchInfoAccess();
        }
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
                //マルチ対応
                //SetActionSepalate(event.getX(),event.getY(),event,MotionEvent.ACTION_DOWN);
                //シングルのみ
                SetSimpleAction(event.getX(),event.getY(),MotionEvent.ACTION_DOWN);
                break;
            case MotionEvent.ACTION_MOVE://移動したとき
                Log.d("TouchMOVE","X:"+event.getX()+"Y:"+event.getY()+"Z:" + event.getPressure());
                flg =  true;
                //マルチ対応
                //SetActionSepalate(event.getX(),event.getY(),event,MotionEvent.ACTION_DOWN);
                //シングルのみ
                SetSimpleAction(event.getX(),event.getY(),MotionEvent.ACTION_MOVE);
                break;
            case MotionEvent.ACTION_UP://あげたとき。
                Log.d("TouchUP","X:"+event.getX()+"Y:"+event.getY()+"Z:" + event.getPressure());
                flg = false;//ここがちょい微妙
                //検索　削除
                ResetAction(0);///仮で１
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

    public boolean NowTouchFlg() {
        return g;
    }

    //最初のIDでとられているタッチの位置を取得
    public Vector3 GetTouchPositionDefault()
    {
        Vector3 poss = new Vector3();
        //アクセスしたIDの箇所を流す。
        poss = access_device[0].GetInfo();
        return poss;
    }

    private void SetActionSepalate(float x,float y,MotionEvent event,int action_style)
    {
        touch_cnt_now = event.getPointerCount();//現在のタッチの数を取得
        Log.d("COUNT","Touchcnt"+touch_cnt_now);
        boolean set_flg = false;//値が挿入できたか？
        int serch_cnt = 0;
        //タッチしている数分IDを確認
        for(int touch_cnt = 0;touch_cnt < touch_cnt_now;touch_cnt++)
        {
            //格納場所全てを検索
            for(int i = 0; i < MAX_TOUCH_DEVEICE;i++)
            {
                //同じIDだったら そこに更新
                if(touch_cnt == access_device[i].GetID())
                {
                    access_device[i].SetTouchInfo(x,y,0.0f,event.getPointerId(touch_cnt),action_style);
                    set_flg = true;
                }
            }
            serch_cnt++;
        }
        //セットが終わってたら処理終了
        if(set_flg)return;
        //すでに全部一杯だったら破棄
        if(serch_cnt > MAX_TOUCH_DEVEICE)return;
        //全て検索して存在しなかったら、０番目に格納
        SetSimpleAction(x,y,action_style);
    }

    //０番目に常に格納
    private void SetSimpleAction(float x,float y,int action)
    {
        access_device[0].SetTouchInfo(x,y,0.0f,0,action);
    }

    //使っていたポインターを解除する。
    private void ResetAction(int point)
    {
        access_device[point].ResetPointer();//情報の破棄
    }
}
