package com.ahaproject.playdeffence.GLESUsuful.Shader;

import android.opengl.GLES20;

import com.ahaproject.playdeffence.JavaUsuful.Text.TextFileRead;

/**
 * Created by akihiro on 2017/02/27.
 */

public class ShaderObj {
    private String vertexShaderCode;        //シェーダーコード
    private String fragmentShaderCode;      //フラグメントシェーダーコード

    private int compilevertex;
    private int compilefragment;

    private int shaderProgram;              //シェーダープログラム


    public ShaderObj(String vertex_file_name,String fragment_file_name)
    {
        TextFileRead textread = new TextFileRead();
        //( your settings shadercode file path,file_name)
        vertexShaderCode = textread.GetShaderSourceforTextFile("Shader/vertex_shader",vertex_file_name);
        textread.ResetinString();
        fragmentShaderCode = textread.GetShaderSourceforTextFile("Shader/flagment_shader",fragment_file_name);

        compilevertex = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        compilefragment = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        //シェーダーのプログラムオブジェクト生成？
        shaderProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderProgram, compilevertex);
        GLES20.glAttachShader(shaderProgram,compilefragment);
        GLES20.glLinkProgram(shaderProgram);
    }
    private int loadShader(int type,String shaderCode){
        //シェーダーオブジェクトの生成
        int shader = GLES20.glCreateShader(type);
        //シェーダーオブジェクトとソースコードを結びつける
        GLES20.glShaderSource(shader, shaderCode);
        //コンパイル
        GLES20.glCompileShader(shader);
        return shader;
    }
    public int GetShaderProgram(){return shaderProgram;}
}
