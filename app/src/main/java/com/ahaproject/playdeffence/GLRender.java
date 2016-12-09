package com.ahaproject.playdeffence;

import android.opengl.*;

import com.ahaproject.playdeffence.Geometry.Triangle;

import javax.microedition.khronos.egl.*;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/12/10.
 */

public class GLRender implements GLSurfaceView.Renderer{

    Triangle triangle;

    public GLRender()
    {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        triangle = new Triangle();
    }

    //画面サイズ等が変わった時
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    //描画
    @Override
    public void onDrawFrame(GL10 gl) {
        //背景色
        GLES20.glClearColor(0.0f,0.0f,1.0f,1);
        //バッファーをきれいに？
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        triangle.draw();
    }
}
