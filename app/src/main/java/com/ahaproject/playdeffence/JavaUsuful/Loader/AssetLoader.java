package com.ahaproject.playdeffence.JavaUsuful.Loader;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.ahaproject.playdeffence.JavaUsuful.ResourceControll.ContextHave;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Created by akihiro on 2017/02/24.
 * アセットから指定したものを読み込み
 * */

public class AssetLoader {
    private AssetLoader() {}

    /*リソースのファイルパス名（Asset以下）とリソースのファイル名を見てそのBitmapを作成する。*/
    public static  Bitmap BitmapLoader(String SetAssetFilePath,String GetResouceFileName)
    {
        Bitmap bitmap = null;
        //texture reading
        AssetManager asset = ContextHave.getInstance().GetContext().getAssets();
        String[] tex_file_list = null;
        try{
            tex_file_list = asset.list(SetAssetFilePath);
        }catch (IOException e)//入力（ファイル、キーボードやネットワーク接続など）
        {
            e.printStackTrace();//エラーメッセージ？
        }
        BufferedInputStream bis = null;
        //指定のファイルが何番目かを引き出す
        //指定したテキストファイルの位置検索
        int num = tex_file_list.length;
        int cnt = 0;
        while(cnt < num)
        {
            //同じファイル名かどうか
            if(tex_file_list[cnt].equals(GetResouceFileName))
                break;
            cnt++;
        }
        //エラーチェック
        if(num <= cnt) {
            System.out.println("指定したファイル名：" + GetResouceFileName + "が見つかりませんでした");
        }


        Context context = ContextHave.getInstance().GetContext();

        try{
            bis = new BufferedInputStream(asset.open(SetAssetFilePath + "/"+tex_file_list[cnt].toString()));
            bitmap = BitmapFactory.decodeStream(bis);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try{
                bis.close();
            }
            catch(Exception ex) {
                System.out.println("BufferInputStreamが正常に終了しませんでした。");
            }
        }
        return bitmap;
    }

    public static Bitmap[] GetBitmapsLoader(String SetAssetFilePath,String GetResouceFileName)
    {
        //texture reading
        AssetManager asset = ContextHave.getInstance().GetContext().getAssets();
        String[] tex_file_list = null;
        try{
            tex_file_list = asset.list(SetAssetFilePath);
        }catch (IOException e)//入力（ファイル、キーボードやネットワーク接続など）
        {
            e.printStackTrace();//エラーメッセージ？
        }
        BufferedInputStream bis = null;
        //指定のファイルが何番目かを引き出す
        //指定したテキストファイルの位置検索
        int num = tex_file_list.length;
        Bitmap[] bitmap = new Bitmap[num];
        int cnt = 0;
        while(cnt < num)
        {
            try {
                bis = new BufferedInputStream(asset.open(SetAssetFilePath + "/" + tex_file_list[cnt].toString()));
                bitmap[cnt] = BitmapFactory.decodeStream(bis);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bis.close();
                } catch (Exception ex) {
                }
            }
            cnt++;
        }
        return bitmap;
    }


    public static int CreateTexture(Bitmap bitmap)
    {
        int[] texture = new int [1];
        int tex_id;
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

        return tex_id;
    }






}
