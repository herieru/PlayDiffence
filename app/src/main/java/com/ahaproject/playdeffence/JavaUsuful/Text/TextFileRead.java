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

    public TextFileRead()
    {
        read_str = null;
    }
    //file_name is that you want the file_name in file_path
    public TextFileRead(String file_path,String file_name)
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

    //file_name is that you want the file_name in file_path
    public String GetResourceText(String file_path,String file_name)
    {
        read_str = null;
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
            //同じファイル名かどうか
            if(file_list[cnt].equals(file_name))
                break;
            cnt++;
            break;
        }
        //テキストの読み込み
        BufferedReader br;      //文字、配列をテキストから効率よく読み込むもの
        StringBuilder sb = new StringBuilder();//文字列の可変シーケンス
        String strs = null;
        String text = null;
        try {
            InputStream is = asset.open(file_path + "/" + file_list[cnt].toString());
            br = new BufferedReader(new InputStreamReader(is));
            //テキストを行で読み込んでいる。
            while ((strs = br.readLine()) != null) {
                sb.append(strs);
            }
            br.close();
            text = sb.toString();
        }catch (IOException es)
        {
            text = "io error";

        }
        //text value is io error icoule error
        read_str = text;
        return read_str;
    }

    //this function shader txt
    public String GetShaderSourceforTextFile(String file_path,String file_name)
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
            //同じファイル名かどうか
            if(file_list[cnt].equals(file_name))
                break;
            cnt++;
            break;
        }
        //テキストの読み込み
        BufferedReader br;      //文字、配列をテキストから効率よく読み込むもの
        StringBuilder sb = new StringBuilder();//文字列の可変シーケンス
        String strs = null;
        String text = null;
        String tmp_str = null;
        try {
            InputStream is = asset.open(file_path + "/" + file_list[cnt].toString());
            br = new BufferedReader(new InputStreamReader(is));
            //テキストを行で読み込んでいる。
            while ((strs = br.readLine()) != null) {
                tmp_str = this.DeleteComment(strs);
                if(tmp_str == null)
                    continue;
                sb.append(tmp_str);
            }
            br.close();
            text = sb.toString();
        }catch (IOException es)
        {
            text = "io error";

        }
        //text value is io error icoule error
        read_str = text;


        return read_str;
    }

    private String DeleteComment(String cheak_str)
    {
        String rtn_str = null;
        boolean  comment_flg = false;
        int cnt = 0;
        int length = cheak_str.length();//文字列の長さを図る
        char c;
        while(cnt < length)
        {
            c = cheak_str.charAt(cnt);
            if('/' == c)
            {
                if(!comment_flg)//コメントの可能性がない時は、true
                {
                    comment_flg = true;
                }
                else
                {
                    break;
                }

            }
            else//コメントの可能性じゃないときの処理
            {
                if(!comment_flg) {
                    cnt++;
                }
                else
                {
                    comment_flg = false;
                    cnt = cnt +2;
                }
            }
        }

        //1文字以上だったときはnullで返す
        if(cnt == 0)
        {
            return rtn_str;
        }
        rtn_str = cheak_str.substring(0,cnt);
        return rtn_str;
    }
    public void ResetinString()
    {
        read_str = null;
    }




}
