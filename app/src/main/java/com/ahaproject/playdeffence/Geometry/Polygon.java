package com.ahaproject.playdeffence.Geometry;



import android.opengl.GLES20;
import android.opengl.Matrix;

import com.ahaproject.playdeffence.JavaUsuful.Singleton.GLManager;
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
    private float[] mtrxProjection = new float[16];
    private float[] mtrxView = new float[16];
    private float[] MtrixProgectionAndView = new float[16];



    //コンストラクタ
    public Polygon() {
        //uniformはCPUから定数
        //vertexshader　　　attribute　は　頂点情報であるということの宣言
        vertexShaderCode ="attribute  vec4 vPosition;" +
                          //"attribute  vec4 num;"+
                "uniform  mat4 uMVPMatrix;" +
                "void main() {" +
                "  gl_Position =  uMVPMatrix * vPosition;" +
                "}";



        //picell shader
        fragmentShaderCode =//mediump は　演算制度
                "precision mediump float;" +
                        "void main() {" +
                        "  gl_FragColor =vec4(0.0, 1.0, 0.0, 1.0);" +
                        "}";
        //コンパイルしているIDをもらっている。
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        //シェーダーのプログラムオブジェクト生成？
        shaderProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        GLES20.glLinkProgram(shaderProgram);

        //スクリーンサイズ
        Vector3 scren = GLManager.GetInstance().GetWindowSize();
        //Redo the Viewport,making it fullscreen
        GLES20.glViewport(0,0,(int)scren.x,(int)scren.y);
        //Clear our matrices
        Matrix.setIdentityM(mat,0);
    }

    @Override
    public int LoadShaderFile(String vertex, String fragment) {
        //未実装
        return 0;
    }

    @Override
    public int loadShader(int type, String shaderCode) {
        //シェーダーオブジェクトの生成
        int shader = GLES20.glCreateShader(type);
        //シェーダーオブジェクトとソースコードを結びつける
        GLES20.glShaderSource(shader, shaderCode);
        //コンパイル
        GLES20.glCompileShader(shader);
        return shader;
    }

    @Override
    public void draw() {
        //使うシェーダープログラム　適用

        GLES20.glUseProgram(shaderProgram);

        float vertices[] = {
                -1.0f, 0.0f, 0.0f,//三角形の点A(x,y,z)
                -1.0f, -1.0f, 0.0f,//三角形の点B(x,y,z)
                0.0f, -1.0f, 0.0f,//三角形の点C(x,y,z)
                0.0f,0.0f,0.0f,
        };






        //最後の４はfloat型故
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        //バッファの現在位置に指定されたbyteを書き込み　頂点数分書き込み
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        //int num_attr = GLES20.glGetAttribLocation(shaderProgram, "num");
        //GLES20.glEnableVertexAttribArray(num_attr);//その情報へのアクセスを有効にする。
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



        //描画の仕方指定　GL_TRIANGLES トライアングルファン　頂点数　
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertices.length/3);

        //アクセスを無効にする？
        GLES20.glDisableVertexAttribArray(positionAttrib);
  //      GLES20.glDisableVertexAttribArray(mtrxhandle);




    }


}
