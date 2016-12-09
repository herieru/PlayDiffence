package com.ahaproject.playdeffence.Geometry;

/**
 * Created by akihiro on 2016/12/10.
 */

public interface BaseGeometry {

    //頂点シェーダーとピクセルシェーダーを読み込む
    public int LoadShaderFile(String vertex,String fragment);
    //読み込むシェーダーコードを指定
    public int loadShader(int type,String shadeCode);

    //描画
    public void draw();



}
