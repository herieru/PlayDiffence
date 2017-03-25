package com.ahaproject.playdeffence.GLESUsuful.Shader;

/**
 * Created by akihiro on 2017/03/26.
 */
//シェーダーの使うものをセットする用の
// 構造体みたいなクラス

public class ShaderValue {
   enum SHADER_ACCSSESS_CODE{
       S_POSITION,      //位置
       S_OBJWORLD,      //マトリックス
       S_ANATHER_WORLD, //別のマトリックス//TODO:これもきめなきゃ
       S_TEXTURE,       //テクスチャ
       S_TEXCOODE,      //テクスチャUV
       S_VELOCITY1,     //ベクトル１　TODO:変数名決めとかなきゃ
       S_VELOCITY2,     //ベクトル２ TODO:これもきめなきゃ
       //もしかしたらもう少しあるかも？
    }
    private int shader_value;
    private String access_value;
    //どのオブジェクトにアクセスするものか
    ShaderValue(SHADER_ACCSSESS_CODE code)
    {

    }

    //どの値にアクセスするのかを判断する。
    private void Access_Position_Serach()
    {
        //ここで何の値かをスイッチ分で判断
        //デフォルトはエラー報告。

    }


}
