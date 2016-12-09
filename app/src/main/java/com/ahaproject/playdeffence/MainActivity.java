package com.ahaproject.playdeffence;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

class MainActivity extends Activity{

    private MyGLSurfaceView mGLView;
    GLRender mRenderer;
    GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new MyGLSurfaceView(this);//Contextを渡す
        setContentView(mGLView);//描画で使うものの設定
    }


}
