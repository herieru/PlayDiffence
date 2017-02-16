package com.ahaproject.playdeffence;

import android.app.Activity;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.ahaproject.playdeffence.JavaUsuful.ResourceControll.ContextHave;
import com.ahaproject.playdeffence.JavaUsuful.Singleton.GLManager;
import com.ahaproject.playdeffence.TouchController.TouchManager;

class MainActivity extends Activity{

    private MyGLSurfaceView mGLView;
    private TouchManager touch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //画面をフルスクリーンにする。
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mGLView = new MyGLSurfaceView(this);//Contextを渡す
        touch = TouchManager.getInstance();
        setContentView(mGLView);//描画で使うものの設定
        int width,height;
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        width = point.x;
        height = point.y;

        GLManager.GetInstance().SetWindowSize(width,height);
        ContextHave.getInstance().SetContext(this);


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
