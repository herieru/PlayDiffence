package com.ahaproject.playdeffence.TouchController;

/**
 * Created by akihiro on 2016/12/11.
 */

public enum TouchState {
    OnTouchStart,//タッチしました。
    OnTouchMove,//タッチした状態で、移動
    OnTouchSlash,//一定距離を一瞬で動いた。
    OnTouchRemove,//タッチを離した。
    OnDoNotTouch,// 何もしていない状態。
}
