package com.ahaproject.playdeffence.JavaUsuful.Loader;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ahaproject.playdeffence.JavaUsuful.ResourceControll.ContextHave;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Created by akihiro on 2017/02/24.
 * アセットから指定したものを読み込み
 * */

public class AssetLoader {

    private AssetLoader() {}

    /*リソースのファイル名とリソースのファイル名を見てそのBitmapを作成する。*/
    public Bitmap BitmapLoader(String SetAssetFilePath,String GetResouceFileName)
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
            break;
        }

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
            }
        }
        return bitmap;
    }

    public Bitmap[] GetBitmapsLoader(String SetAssetFilePath,String GetResouceFileName)
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





}
