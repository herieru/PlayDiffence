package com.ahaproject.playdeffence;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.ahaproject.playdeffence.TouchController.TouchManager;

class MainActivity extends Activity{

    private MyGLSurfaceView mGLView;
    private TouchManager touch;
    GLRender mRenderer;
    GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new MyGLSurfaceView(this);//Contextを渡す
        touch = TouchManager.getInstance();
        setContentView(mGLView);//描画で使うものの設定
    }

    //OnPauseの状態から戻ってきたときなど。
    @Override
    protected void onResume() {
        super.onResume();
    }


    //同じアプリケーションから別のアプリケーションが呼ばれた場合
    @Override
    protected  void onPause(){
        super.onPause();
    }


    //アクティビティが表示されていない状態の時
    @Override
    protected  void onStop(){
        super.onStop();
    }

    //Stop状態から戻ってきたとき
    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    //アクティビティが死んだ時　　セーブなどの時に実装
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
    //イベントドリスナ
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        touch.SetTouchEvent(event);

        return true;//常に消化したとする
    }



}
