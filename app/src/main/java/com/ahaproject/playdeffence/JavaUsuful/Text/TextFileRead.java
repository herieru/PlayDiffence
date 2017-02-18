package com.ahaproject.playdeffence.JavaUsuful.Text;

import android.content.res.AssetManager;

import com.ahaproject.playdeffence.JavaUsuful.ResourceControll.ContextHave;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by akihiro on 2017/02/19.
 * This class get  textfile  in the  android assetFolder
 */

public class TextFileRead {

    private String read_str;

    //file_name is that you want the file_name in file_path
    TextFileRead(String file_path,String file_name)
    {
        //call this line is need ContextHave class to set Context values
        AssetManager asset = ContextHave.getInstance().GetContext().getAssets();

        //
        String[] file_list = null;
        try{
            file_list = asset.list(file_path.toString());
        }catch(IOException e)//例外が発生時のシグナルこれは入出力系
        {
            e.printStackTrace();//？？？
        }

        //指定したテキストファイルの位置検索
        int num = file_list.length;
        int cnt = 0;
        while(cnt < num)
        {
            if(file_list[cnt].toString() == file_name)
                break;
            cnt++;
        }



        //テキストの読み込み
        BufferedReader br;
        StringBuilder sb = new StringBuilder();
        String strs = null;
        String text = null;
        try {
            InputStream is = asset.open("Shader/" + file_list[cnt].toString());
            br = new BufferedReader(new InputStreamReader(is));
            while ((strs = br.readLine()) != null) {
                sb.append(strs);
            }
            br.close();
            text = sb.toString();
        }catch (IOException es)
        {
            text = "io error";

        }
    }



}
