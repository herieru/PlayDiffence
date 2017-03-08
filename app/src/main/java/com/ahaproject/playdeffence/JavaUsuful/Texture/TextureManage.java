package com.ahaproject.playdeffence.JavaUsuful.Texture;

/**
 * Created by akihiro on 2017/01/17.
 * テクスチャを取り出すためのもの　また、読み込むためのテクスチャもここで編集等をおこなう
 */

public class TextureManage {

    private static TextureManage my_instance = new TextureManage();

    //enum群
    public final int TEX_SAMPLE = 0;
    public final int TEX_MARU = 1;

    //ファイル名
    private String file_name[] ={"tex_sample.bmp","sample.bmp"};
    //テクスチャを格納
    private TextureObj[] tex_obj = new TextureObj[file_name.length];


    public static TextureManage GetInstance()
    {
        return my_instance;
    }

    public void AHA()
    {}



    private TextureManage()
    {
        for(int i = 0; i < file_name.length;i++)
        {
            tex_obj[i] = new TextureObj("texture",file_name[i]);
        }
    }


    public int GetTextureID(int texture_no)
    {
        return tex_obj[texture_no].GetTextureId();
    }


}
