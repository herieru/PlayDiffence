package com.ahaproject.playdeffence.TouchController;

/**
 * Created by akihiro on 2016/12/11.
 * タッチの情報の受け渡しする為の構造体クラス
 */

class TouchInfoStruct {
    protected float touch_x,touch_y;       //タッチしている位置
    protected float press_z;               //タッチしている加減
    protected float prev_x,prev_y;        //一つ前座標
    protected TouchState state;             //タッチの状態
    protected boolean use;                  //このクラスを使用しているか？
    protected int p_index;                  //ポインターINDEX　　タッチした時に割り振られる　離すまで保持

    //コンストラクタ
    protected TouchInfoStruct(){
        //初期化
        touch_x = 0;
        touch_y = 0;
        press_z = 0;
        prev_x = 0;
        prev_y = 0;
        state = TouchState.OnDoNotTouch;//最初は割り振られていない状態。
        p_index = -1;
    }


}
