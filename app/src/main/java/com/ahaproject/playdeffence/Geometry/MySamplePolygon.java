package com.ahaproject.playdeffence.Geometry;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import com.ahaproject.playdeffence.GLESUsuful.MyGLES20Utiles;
import com.ahaproject.playdeffence.GLESUsuful.Shader.ShaderObj;
import com.ahaproject.playdeffence.JavaUsuful.ResourceControll.ContextHave;
import com.ahaproject.playdeffence.JavaUsuful.Singleton.GLManager;
import com.ahaproject.playdeffence.JavaUsuful.Text.TextFileRead;
import com.ahaproject.playdeffence.R;
import com.ahaproject.playdeffence.Velocity.Vector3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by akihiro on 2017/02/12.
 */

public class MySamplePolygon  extends C_Geometry{
    private int shaderProgram;

    //頂点座標
    private static final float VERTEXS[]= {
            0.0f,1.0f,0.0f,        //左上
            0.0f,0.0f,0.0f,       //左下
            1.0f,1.0f,0.0f,         //右上
            1.0f,0.0f,0.0f         //右下
    };
    //テクスチャUV値
    private static final float TEXCOORDS[] = {
            0.0f, 0.0f,	// 左上
            0.0f, 1.0f,	// 左下
            1.0f, 0.0f,	// 右上
            1.0f, 1.0f	// 右下
    };

    //頂点バッファ保持
    private final FloatBuffer mVertexBuffer = MyGLES20Utiles.createBuffer(VERTEXS);

    //UVバッファ保持
    private final FloatBuffer mTexcoordBuffer = MyGLES20Utiles.createBuffer(TEXCOORDS);

    private int mPositionp;
    private int mTexcoodp;
    private int mTexturep;
    private int mMatp;

    private int mTextureId;

    ShaderObj shaderObj;

    //コンストラクタ
    public MySamplePolygon() {
        //uniformはCPUから定数
        //vertexshader　　　attribute　は　頂点情報であるということの宣言
        //コメント文を除いたシェーダーソースを読み込み
        shaderObj =new ShaderObj("vertex_plas_tex.txt","flag,emt_plas_tex.txt");
        shaderProgram = shaderObj.GetShaderProgram();
        //シェーダーで使用するハンドルを取ってくる。
        mPositionp = GLES20.glGetAttribLocation(shaderProgram,"vPosition");
        MyGLES20Utiles.checkGlError("glGeAttribLocation position");
        if(mPositionp == -1)throw  new IllegalStateException("Could not get attrib location fotr position");
        GLES20.glEnableVertexAttribArray(mPositionp);

        mTexcoodp = GLES20.glGetAttribLocation(shaderProgram,"texcoord");
        MyGLES20Utiles.checkGlError("glGeAttribLocation texcoord");
        if(mTexcoodp == -1)throw  new IllegalStateException("Could not get attrib location fotr mtexcoord");
        GLES20.glEnableVertexAttribArray(mTexcoodp);

        mTexturep = GLES20.glGetUniformLocation(shaderProgram,"texture");
        MyGLES20Utiles.checkGlError("glGeAttribLocation texture");
        if(mTexturep == -1)throw  new IllegalStateException("Could not get attrib location fotr texture");
        GLES20.glEnableVertexAttribArray(mTexturep);

        mMatp = GLES20.glGetUniformLocation(shaderProgram,"uMVPMatrix");
        if(mMatp == -1)throw new IllegalStateException("Could not get uniform  location uMVPMatrix");
        GLES20.glEnableVertexAttribArray(mMatp);

        final Bitmap bitmap = BitmapFactory.decodeResource(ContextHave.getInstance().GetContext().getResources(),R.drawable.tex_sample);
        mTextureId = MyGLES20Utiles.loadTexture(bitmap);
        bitmap.recycle();

        Matrix.setIdentityM(mat,0);

    }

    @Override
    public void draw() {
        //使うシェーダープログラム　適用

        GLES20.glUseProgram(shaderProgram);
        // 背景とのブレンド方法を設定します。
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
      //  GLES20.glEnable(GLES20.GL_BLEND);
       // GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);	// 単純なアルファブレンド

        // テクスチャの指定
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glUniform1i(mTexturep, 0);
        GLES20.glUniformMatrix4fv(mMatp,1,false,mat,0);
        GLES20.glVertexAttribPointer(mTexcoodp, 2, GLES20.GL_FLOAT, false, 0, mTexcoordBuffer);
        GLES20.glVertexAttribPointer(mPositionp, 3, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

      //  GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_TEXTURE_2D);

    }

}
