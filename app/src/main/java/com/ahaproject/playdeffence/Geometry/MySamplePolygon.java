package com.ahaproject.playdeffence.Geometry;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.ahaproject.playdeffence.JavaUsuful.Singleton.GLManager;
import com.ahaproject.playdeffence.Velocity.Vector3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by akihiro on 2017/02/12.
 */

public class MySamplePolygon {
    public static int sp_SplidColor;

    public static final String vs_SolidColor =
                    //"uniform mat4 uMVPMatrix;"+
                    //"attribute vec4 vPosition;"+
                    //"void main(){"+
                    //" gl_Position = uMVPMatrix * vPosition;+
                    //"}";
                    "attribute  vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";
    public static final String fs_SolidColor =
                    "precision mediump float;"+
                    "void main(){"+
                    " gl_FragColor = vec4(0.0,0.7,0.3,1.0);"+
                    "}";

    //Our matrices
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] getMtrxProjectionAndView = new float[16];

    //Geometric variables
    public static float vertices[];
    public static short indices[];
    public FloatBuffer vertexBuffer;
    public ShortBuffer drawListBuffer;

    int m_Program;

    public MySamplePolygon()
    {
        //スクリーンサイズ
        Vector3 scren = GLManager.GetInstance().GetWindowSize();
        //Redo the Viewport,making it fullscreen
        GLES20.glViewport(0,0,(int)scren.x,(int)scren.y);
        //Clear our matrices
        Matrix.setIdentityM(mtrxProjection,0);
        Matrix.setIdentityM(mtrxView,0);
        Matrix.setIdentityM(getMtrxProjectionAndView,0);


        Matrix.orthoM(mtrxProjection,0,0f,scren.x,0f,scren.y,0,50);
        Matrix.setLookAtM(mtrxView,0,
                0f,0f,1f,
                0f,0f,0f,
                0f,1.0f,0.0f);
        Matrix.multiplyMM(getMtrxProjectionAndView,0,getMtrxProjectionAndView,0,mtrxView,0);

        //SetUpTriangle
        vertices = new float[]
                {10.0f,200f,0.0f,
                10.0f,100f,0.0f,
                100f,100f,0.0f,};
        indices = new short[]{0,1,2};
        //The vertex buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        //initialize byte buffer for the drw list
        ByteBuffer dlb  = ByteBuffer.allocateDirect(indices.length * 2);//＊2＝short byte
        drawListBuffer =  dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);

        GLES20.glClearColor(0.0f,0.0f,0.0f,1);
        //shader set
        int vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertexShader,vs_SolidColor);
        GLES20.glCompileShader(vertexShader);
        int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragmentShader,fs_SolidColor);
        GLES20.glCompileShader(fragmentShader);
        sp_SplidColor = GLES20.glCreateProgram();
        //add vertex shader
        GLES20.glAttachShader(sp_SplidColor,vertexShader);
        //add fragment shader
        GLES20.glAttachShader(sp_SplidColor,fragmentShader);
        GLES20.glUseProgram(sp_SplidColor);

    }



    public void draw()
    {
        GLES20.glUseProgram(sp_SplidColor);




        int mPositionHandle = GLES20.glGetAttribLocation(sp_SplidColor,"vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle,3,GLES20.GL_FLOAT,false,0,vertexBuffer);
        //Get handle to Shape's transformation matrix
        /*int mtrxhandle = GLES20.glGetUniformLocation(sp_SplidColor,"uMVPMatrix");
        //Apply the Projection and view transformation matrix
        GLES20.glUniformMatrix4fv(mtrxhandle,1,false,getMtrxProjectionAndView,0);*/
        //Draw the triangle
        //GLES20.glDrawElements(GLES20.GL_TRIANGLES,indices.length,GLES20.GL_UNSIGNED_SHORT,drawListBuffer);

        //Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);

    }











}
