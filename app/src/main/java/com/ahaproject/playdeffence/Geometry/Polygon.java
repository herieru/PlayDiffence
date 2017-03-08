package com.ahaproject.playdeffence.Geometry;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.ahaproject.playdeffence.GLESUsuful.Shader.ShaderObj;
import com.ahaproject.playdeffence.JavaUsuful.Texture.TextureManage;
import com.ahaproject.playdeffence.JavaUsuful.Texture.TextureObj;
import com.ahaproject.playdeffence.TouchController.TouchManager;
import com.ahaproject.playdeffence.Velocity.Vector3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by akihiro on 2016/12/18.
 * ポリゴンを出す為のもの
 */
public class Polygon extends C_Geometry{

    private int shaderProgram;
    int[] tex_id = new int [1];
    //頂点
    float vertices[] = {
            -1.0f, 0.0f, 0.0f,//三角形の点A(x,y,z) 左上
            -1.0f, -1.0f, 0.0f,//三角形の点B(x,y,z)　左下
            0.0f, 0.0f, 0.0f,//右上
            0.0f, -1.0f, 0.0f,//三角形の点C(x,y,z)　右下
    };
    //テクスチャUV
    float tex_uv[] = {
            0.0f,0.0f,//左上
            0.0f,1.0f,//左下
            1.0f,0.0f,//右上
            1.0f,1.0f//右下
    };

    private int m_Postion_p;    //ポジションのアクセス
    private int m_TexCoord_p;   //テクスチャコードのアクセス

    TextureObj textureobj;//テクスチャの保存オブジェクト
    ShaderObj shaderObj;//シェーダーオブジェクト


    //コンストラクタ
    public Polygon() {
        shaderObj =new ShaderObj("vertex_plas_tex.txt","flag,emt_plas_tex.txt");
        shaderProgram = shaderObj.GetShaderProgram();
        //Clear our matrices
        Matrix.setIdentityM(mat,0);
        textureobj = new TextureObj("texture","tex_sample.bmp");
    }

    public void Update()
    {
        Vector3 pos = TouchManager.getInstance().GetTouchPositionDefault();
        Log.d("RESULUTLOG:","X:"+pos.x+"Y:" + pos.y + "Z:" + pos.z);
    }


    @Override
    public void draw() {
        //使うシェーダープログラム　適用

        GLES20.glUseProgram(shaderProgram);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES20.glDisable(GLES20.GL_BLEND);

        //最後の４はfloat型故
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());//ハードウェアと同じバイト順に並び替え
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        //バッファの現在位置に指定されたbyteを書き込み　頂点数分書き込み
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        //シェーダープログラムの　vPositionの位置？を取得する　その値へのアクセスするためのIDが返ってくる
        int positionAttrib = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionAttrib);//その情報へのアクセスを有効にする。
        //対応付け終了。
        //GLES20.glVertexAttribPointer(positionAttrib,vertices.length, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        //関連付けている値のところにデータを渡す。 ポジション（ｘ、ｙ、ｚ,w）で渡す
        GLES20.glVertexAttribPointer(positionAttrib,        //シェーダー変数の位置
                3,                      //頂点一つ当たりの要素数
                GLES20.GL_FLOAT,        //データのフォーマット
                false,                  //正規化するかどうかのフラグ
                12,       //頂点データの先頭から次の頂点データまでのバイト数 (1頂点当たりのバイト数)
                vertexBuffer);                              //頂点データのポインタ
        int mtrxhandle = GLES20.glGetUniformLocation(shaderProgram,"uMVPMatrix");
        GLES20.glEnableVertexAttribArray(mtrxhandle);//その情報へのアクセスを有効にする。
        //Apply the Projection and view transformation matrix
        GLES20.glUniformMatrix4fv(mtrxhandle,1,false,mat,0);

        //テクスチャの指定
        int m_texture = GLES20.glGetUniformLocation(shaderProgram,"texture");
        GLES20.glEnableVertexAttribArray(m_texture);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);//どのテクスチャのGPUを使うか？？？
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,TextureManage.GetInstance().GetTextureID(TextureManage.GetInstance().TEX_SAMPLE));//どのテクスチャを使うか？
        GLES20.glUniform1i(m_texture,0);
        //textureobj.GetTextureId()

        int m_texcoord = GLES20.glGetAttribLocation(shaderProgram,"texcoord");
        GLES20.glEnableVertexAttribArray(m_texcoord);
        //値セット
        ByteBuffer TexCoordBuffer = ByteBuffer.allocateDirect(tex_uv.length * 4);
        TexCoordBuffer.order(ByteOrder.nativeOrder());//ハードウェアと同じバイト順に並び替え
        FloatBuffer uploadTexcoordBuffer = TexCoordBuffer.asFloatBuffer();
        uploadTexcoordBuffer.put(tex_uv);//現在の場所に書き込み
        uploadTexcoordBuffer.position(0);

        GLES20.glVertexAttribPointer(m_texcoord,2,GLES20.GL_FLOAT,false,8,uploadTexcoordBuffer);


        //描画の仕方指定　GL_TRIANGLES トライアングルファン　頂点数　
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertices.length/3);

        //アクセスを無効にする？
        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_TEXTURE_2D);


        //以下関係ない実験のため最終的に削除

        Vector3 pos = TouchManager.getInstance().GetTouchPositionDefault();
        Log.d("RESULUTLOG:","X:"+pos.x+"Y:" + pos.y + "Z:" + pos.z);



    }
}
