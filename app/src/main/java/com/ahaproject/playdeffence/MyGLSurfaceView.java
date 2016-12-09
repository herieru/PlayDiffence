package com.ahaproject.playdeffence;

import android.content.Context;
import android.opengl.*;

/**
 * Created by akihiro on 2016/12/10.
 */

public class MyGLSurfaceView extends GLSurfaceView{

    private GLRender m_glrender;

    public MyGLSurfaceView(Context context) {
        super(context);
        //Create an OpneGL ES 2.0 context
        setEGLContextClientVersion(2);
        m_glrender = new GLRender();
        //Set the Renderer for drawing on the GLSurfaceView
        setRenderer(m_glrender);
        //continue render
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }
}
