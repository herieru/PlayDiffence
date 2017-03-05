package com.ahaproject.playdeffence.JavaUsuful.Texture;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.ahaproject.playdeffence.JavaUsuful.Loader.AssetLoader;
import com.ahaproject.playdeffence.JavaUsuful.ResourceControll.ContextHave;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Created by akihiro on 2017/01/17.
 */

public class TextureObj {

    private int tex_id;//テクスチャの保存場所を表す。

    public TextureObj(String SetAssetFilePath,String GetResouceFileName){
        tex_id = -1;
        Bitmap bitmap = AssetLoader.BitmapLoader(SetAssetFilePath,GetResouceFileName);
        CreateTexture(bitmap);
        bitmap.recycle();
    }
    //持っているテクスチャIDを取り出す
    public  int GetTextureId()
    {
        return tex_id;
    }

    //Bitmap－＞テクスチャを作成する。
    private void CreateTexture(Bitmap bitmap)
    {
        int[] texture = new int [1];

        GLES20.glGenTextures(1,texture,0);
        //GLES20.glPixelStorei
        //commnad UNPACK…テクスチャをピクセルにアップ PACLK…テクスチャからテクセルをダウンロード
        tex_id  = texture[0];//同じ場所のコピー
        // 何バイトごとの区切りか
        GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT,1);
        //メモリの利用方法　target
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,tex_id);
        //VRAMへピクセル情報をコピー
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,GLES20.GL_RGBA,bitmap,0);
        //SetFilterring
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
    }



}
