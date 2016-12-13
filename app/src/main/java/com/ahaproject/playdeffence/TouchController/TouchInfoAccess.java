package com.ahaproject.playdeffence.TouchController;

import android.view.MotionEvent;

/**
 * Created by akihiro on 2016/12/13.
 * タッチイベントに対して、アクセスする関数群
 *
 */

class TouchInfoAccess extends TouchInfoStruct{


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
     return p_index;
    }

    //現在のタッチしている場所を取得
    public TOUCH_POS GetTouchPos(){
        TOUCH_POS pos = new TOUCH_POS();
        pos.pos_x = super.touch_x;
        pos.pos_y = super.touch_y;
        pos.now_state = super.state;
        return pos;
    }

    public void SetTouchInfo(
        float pos_x,            //位置
        float pos_y,            //位置
        float press_z,          //加重
        int alloc_index,         //割り振られているID
        int state               //状態で来る
    ){
        //今まで割り振られていたものと違うIDが来た。
        if(alloc_index != super.p_index)
        {//新しい割り振り
            super.touch_x = pos_x;
            super.touch_y = pos_y;
            super.press_z = press_z;
            super.p_index = alloc_index;//新しく割り当てられたものを
            super.use = true;
        }
        else if(alloc_index == super.p_index)
        {//継続中
            super.prev_x = super.touch_x;
            super.prev_y = super.touch_y;
            super.touch_x = pos_x;
            super.touch_y = pos_y;
            super.press_z = press_z;
        }

    }

    //離したので、情報いらなくなったので、中身をリセット。
    public void ResetPointer()
    {
        use = false;
        p_index = -1;
    }




}
