package com.ahaproject.playdeffence.JavaUsuful.Text;

import android.opengl.GLES20;

import org.w3c.dom.Text;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by akihiro on 2017/01/30.
 */

public class TextManager {
   /* private static final float RI_TEXT_UV_BOX_WIDTH = 0.125f;//横8文字縦8もじのテクスチャの場合
    private static final float RI_TEXT_WIDTH = 32.0f;       //文字列幅
    private static final float RI_TEXT_SPACESIZE = 20f;     //＋スペースサイズ？

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private FloatBuffer colorBuffer;
    private ShortBuffer drawListBuffer;

    private float[] vecs;
    private float[] uvs;
    private short[] indices;
    private float[] colors;

    private int index_vecs;
    private int index_indices;
    private int index_uvs;
    private int index_colors;

    private int texturenr;          //テクスチャ

    private float uniformscale;

    public static int sp_Text;

    //読み込む文字テクスチャに対しての文字の横幅
    public static int[] l_size = {36,29,30,34,25,25,34,33,
            11,20,31,24,48,35,39,29,
            42,31,27,31,34,35,46,35,
            31,27,30,26,28,26,31,28,
            28,28,29,29,14,24,30,18,
            26,14,14,14,25,28,31,0,
            0,38,39,12,36,34,0,0,
            0,38,0,0,0,0,0,0};

    private Vector<TextObject> txtcollection;

    //かならず必要
    public TextManager()
    {
        //初期設定でデータをいれれるように
        txtcollection  =new Vector<TextObject>();
        //配列の初期化
        vecs = new float[3*10];
        colors = new float[4*10];
        uvs = new float [2*10];
        indices = new short[10];
        //init as 0 as default
        texturenr = 0;//テキストの数？
    }


    public void addText(TextObject obj)
    {
        //Add text object to our collection
        txtcollection.add(obj);
    }

    public void setTextureId(int val)
    {
        texturenr = val;
    }

//描画情報の追加
    public void AddCharRenderInformation(float[] vec ,float[] cs,float[] uv,short[] indi)
    {
        short base  = (short)(index_vecs/3);//頂点数の配列番号？

        for(int i = 0; i<vec.length;i++)
        {
            vecs[index_vecs] = vec[i];
            index_vecs++;
        }

        for(int i = 0; i < cs.length;i++)
        {
            colors[index_colors] = cs[i];
            index_colors++;
        }

        for(int i=0;i<uv.length;i++)
        {
            uvs[index_uvs]= uv[i];
            index_uvs++;
        }

        for(int i= 0;i<indi.length;i++)
        {
            indices[index_indices] = (short)(base  +indi[i]);
            index_indices++;
        }
    }

    //??
    public void PreparerawInfo()
    {
        //Reset the indices
        index_vecs = 0;
        index_indices = 0;
        index_uvs = 0;
        index_colors = 0;


        //Get the total amout of characters
        int charcount = 0;
        for(TextObject txt:txtcollection)
        {
            if(txt!=null)
            {
             if(txt.text == null)
             {
                 charcount += txt.text.length();
             }
            }
        }

        vecs = null;
        colors = null;
        uvs = null;
        indices = null;

        vecs = new float[charcount * 12];
        colors = new float[charcount * 16];
        uvs = new float[charcount * 8];
        indices = new short[charcount * 6];
    }

    public void PrepareDraw()
    {
        //Setup all the arrays
        PreparerawInfo();

        //Using the iterator protects for problems with concurrency
        for(Iterator<TextObject> it = txtcollection.iterator();it.hasNext();)
        {
            TextObject txt = it.next();
            if(txt!=null)
            {
                if(!(txt.text == null))
                {
                   // convertTextToTringleInfo();
                }
            }
        }
    }

    public void Draw(float[] m)
    {
        //用意したコンパイル済み、合算済みのシェーダーをセットする。
        GLES20.glUseProgram(riGraphicTools.sp_Text);
    }
*/
}
