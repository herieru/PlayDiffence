package com.ahaproject.playdeffence.Geometry;

import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.ahaproject.playdeffence.JavaUsuful.GLMatrix.GLESMatrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/12/10.
 */

public class Triangle extends C_Geometry {


    private int shaderProgram;


    //コンストラクタ
    public Triangle()
    {
        //vertexshader　　　attribute　は　頂点情報であるということの宣言
        vertexShaderCode ="attribute  vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = vPosition;" +
                "}";
        //picell shader
        fragmentShaderCode =//mediump は　演算制度
                "precision mediump float;" +
                        "void main() {" +
                        "  gl_FragColor =vec4(1.0, 0.0, 0.0, 1.0);" +
                        "}";
        //コンパイルしているIDをもらっている。
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        //シェーダーのプログラムオブジェクト生成？
        shaderProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        GLES20.glLinkProgram(shaderProgram);

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
                0.0f, 0.5f, 0.0f,//三角形の点A(x,y,z)
                -0.5f, -0.5f, 0.0f,//三角形の点B(x,y,z)
                0.5f, -0.5f, 0.0f//三角形の点C(x,y,z)
        };
        //最後の４はfloat型故
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
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
                12,       //頂点データの先頭から次の頂点データまでのバイト数
                vertexBuffer);                              //頂点データのポインタ

        //描画の仕方指定　GL_TRIANGLES トライアングルファン　頂点数　
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertices.length/3);
        //アクセスを無効にする？
        //GLES20.glDisableVertexAttribArray(positionAttrib);



    }
}
