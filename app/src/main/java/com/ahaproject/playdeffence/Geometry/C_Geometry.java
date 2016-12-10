package com.ahaproject.playdeffence.Geometry;

/**
 * Created by akihiro on 2016/12/10.
 */

//図形の基底クラス
public class C_Geometry implements  BaseGeometry {

    //シェーダーコードを格納する
    public String vertexShaderCode;
    public String fragmentShaderCode;
    public int shaderProgram;

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
