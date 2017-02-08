package com.ahaproject.playdeffence.Geometry;

/**
 * Created by akihiro on 2016/12/10.
 */

//図形の基底クラス
public class C_Geometry implements  BaseGeometry {

    //シェーダーコードを格納する
    public String vertexShaderCode;             //頂点シェーダーの格納
    public String fragmentShaderCode;           //ピクセルシェーダーの格納
    public int shaderProgram;                   //頂点、ピクセルシェーダーの合算結果
    public float[] mat = new float[16];         //行列計算

    //コンストラクタ
    public C_Geometry()
    {

    }
    @Override
    public int LoadShaderFile(String vertex, String fragment) {
        return 0;
    }

    @Override
    public int loadShader(int type, String shadeCode) {
        return 0;
    }

    @Override
    public void draw() {

    }
}
