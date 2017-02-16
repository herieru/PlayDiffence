package com.ahaproject.playdeffence;

import android.opengl.*;

import com.ahaproject.playdeffence.Geometry.MySamplePolygon;
import com.ahaproject.playdeffence.Geometry.Polygon;
import com.ahaproject.playdeffence.Geometry.Triangle;
import com.ahaproject.playdeffence.JavaUsuful.Singleton.GLManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/12/10.
 */

public class GLRender implements GLSurfaceView.Renderer{

    Triangle triangle;
    Polygon polygon;
    MySamplePolygon orijin_polygon;

    public GLRender()
    {

    }

    //最初の作成時
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        triangle = new Triangle();
        polygon = new Polygon();
        //orijin_polygon = new MySamplePolygon();
    }

    //画面サイズ等が変わった時
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        GLManager.GetInstance().SetWindowSize(width, height);
    }

    //描画
    @Override
    public void onDrawFrame(GL10 gl) {
        //背景色
        GLES20.glClearColor(0.0f,0.0f,1.0f,1);
        //バッファーをきれいに？
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        polygon.draw();
        triangle.draw();
        //orijin_polygon.draw();
    }
}
