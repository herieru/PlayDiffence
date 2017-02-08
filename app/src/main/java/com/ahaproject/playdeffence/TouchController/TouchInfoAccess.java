package com.ahaproject.playdeffence.TouchController;

import android.view.MotionEvent;

/**
 * Created by akihiro on 2016/12/13.
 * タッチイベントに対して、アクセスする関数群
 *
 */

class TouchInfoAccess {


    //タッチに関する情報集
    private TouchInfoStruct have_touchinfo;

    public class TOUCH_POS{
        public float pos_x,pos_y;
        public TouchState now_state;
    }

    public TouchInfoAccess()
    {
      super();
    }

    //インデックスポインタを返却
    public int GetID(){
     return have_touchinfo.p_index;
    }

    //現在のタッチしている場所を取得
    public TOUCH_POS GetTouchPos(){
        TOUCH_POS pos = new TOUCH_POS();
        pos.pos_x = have_touchinfo.touch_x;
        pos.pos_y = have_touchinfo.touch_y;
        pos.now_state = have_touchinfo.state;
        return pos;
    }

    //現在の位置をセット
    public void SetTouchInfo(
        float pos_x,            //位置
        float pos_y,            //位置
        float press_z,          //加重
        int alloc_index,         //割り振られているID
        int state               //状態で来る
    ){
        //今まで割り振られていたものと違うIDが来た。
        if(alloc_index != have_touchinfo.p_index)
        {//新しい割り振り
            have_touchinfo.touch_x = pos_x;
            have_touchinfo.touch_y = pos_y;
            have_touchinfo.press_z = press_z;
            have_touchinfo.p_index = alloc_index;//新しく割り当てられたものを
            have_touchinfo.use = true;
        }
        else if(alloc_index == have_touchinfo.p_index)
        {//継続中
            have_touchinfo.prev_x = have_touchinfo.touch_x;
            have_touchinfo.prev_y = have_touchinfo.touch_y;
            have_touchinfo.touch_x = pos_x;
            have_touchinfo.touch_y = pos_y;
            have_touchinfo.press_z = press_z;
        }

        //状態の管理
        if(have_touchinfo.state != TouchState.OnTouchSlash)
        {
            WhatStatetoInt(state);//状態の挿入
        }


    }

    //離したので、情報いらなくなったので、中身をリセット。
    public void ResetPointer(
            float pos_x,            //位置
            float pos_y            //位置
    )
    {
        have_touchinfo.use = false;

        //これはUpdateにて更新する
        //have_touchinfo.p_index = -1;
    }


    //毎回のメインフレーム時に更新
    public void Update()
    {

    }



//タッチした情報を基に、何か
    private  void WhatStatetoInt(int set_state)
    {
        switch (set_state)
        {
            case MotionEvent.ACTION_DOWN:
                have_touchinfo.state =  TouchState.OnTouchStart;
            case MotionEvent.ACTION_MOVE:
                have_touchinfo.state = TouchState.OnTouchMove;
            case MotionEvent.ACTION_UP:
                have_touchinfo.state =  TouchState.OnTouchRemove;
        }
        have_touchinfo.state =TouchState.OnDoNotTouch;
    }





}
