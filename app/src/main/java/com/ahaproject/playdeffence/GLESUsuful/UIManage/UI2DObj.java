package com.ahaproject.playdeffence.GLESUsuful.UIManage;

import com.ahaproject.playdeffence.GLESUsuful.Shader.ShaderObj;
import com.ahaproject.playdeffence.JavaUsuful.Singleton.GLManager;
import com.ahaproject.playdeffence.TouchController.TouchManager;
import com.ahaproject.playdeffence.Velocity.Vector3;

/**
 * Created by akihiro on 2017/03/04.
 * 画面上に表jするUIのもの　別のManaggerによって描画を最後にする。
 * このクラスは、UI自身を表すもので、それらを表示する。
 * またタッチに関しての領域等の情報を保持しており、それらの判定を返す。
 */

public class UI2DObj {

    float m_ui_width, m_ui_height;//このUIの保持している
    float m_center_x, m_center_y;
    int m_depth;                  //このUIの表示順番ーが奥、＋が手前という感じに
    boolean mb_display;         //表示するものかどうか？


    UI2DObj anather_obj;                //連結でつながっているものがあれば
    ShaderObj shaderobj;    //シェーダーでの描画のためのオブジェクト

    int shaderprogram;

    float[] vertecis = new float[12];//4角形表示のための頂点情報
    float[] tex_uv = new float[8];  //テクスチャUV

    //作成時にそれを表示するかどうか
    public UI2DObj(boolean set_display, int set_depth,
                   float set_width, float set_heigth,
                   float set_center_x, float set_center_y) {
        m_depth = set_depth;
        //ディスプレイするか否か
        mb_display = set_display;
        //高さと中心点　読み込み
        m_ui_width = set_width;         //-1~1
        m_ui_height = set_heigth;       //-1~1
        m_center_x = set_center_x;      //-1~1
        m_center_y = set_center_y;      //-1~1
        anather_obj = null;
        //頂点情報の挿入
        SetVertices(set_width,set_heigth,set_center_x,set_center_y);
        //UV座標の仮挿入
        SetUpTexUV(0.0f,0.0f,1.0f,1.0f);
        //シェーダーの読み込み（シェーダーファイル未完成） 読み込みはファイル名指定
        shaderobj = new ShaderObj("vertex_plas_tex.txt","flag,emt_plas_tex.txt");
        shaderprogram = shaderobj.GetShaderProgram();
    }

    //このボタンを押したことによるオブジェクトの生成
    public boolean CreateObj(float set_width, float set_heigth,
                             float set_center_x, float set_center_y)
    {
        if(anather_obj == null)
        {
            anather_obj = new UI2DObj(true,m_depth+1,set_width,set_heigth,set_center_x,set_center_y);
            return true;
        }
        return false;
    }


    public boolean IsDisplayed()
    {
        return mb_display;//ディスプレイに表示されているか？
    }


    public boolean HitUI()
    {
        //1.現在のタッチしている位置を取得して、それらをー１～１の間になおす
        //2.その間に入っているかを判断する。
        //3.入っていたらHit
        Vector3 screen = GLManager.GetInstance().GetWindowSize();
        //各頂点の部分を取得 0~2.0fにしたあと上限を1.0fの間にする
        float poly_sx = (m_center_x - m_ui_width  + 1.0f)/2;
        float poly_sy = (m_center_y - m_ui_height + 1.0f)/2;
        float poly_ex = (m_center_x + m_ui_width  + 1.0f)/2;
        float poly_ey = (m_center_y + m_ui_height + 1.0f)/2;

        //タッチの場所を取得
        TouchManager.getInstance().GetTouchPositionDefault();
        //タッチの場所が上記のものを変換した範囲内か？
        //結果を返す。





        return true;
    }

    //表示
    public void draw()
    {
        if(mb_display)
        {
            //表示
        }

    }

    //頂点情報をセットする　　ただし、マトリックスによる移動が実現したら問題ない。
    //マトリックス移動が可能になったらら、大きさのみでいける
    private void SetVertices( float set_width, float set_heigth,
                              float set_center_x, float set_center_y)
    {
        float s_x,s_y,e_x,e_y;//ポリゴンの端っこを格納
        //計算して、頂点情報を代入
        s_x = set_center_x - (set_width / 2);
        s_y = set_center_y - (set_heigth /2);
        e_x = set_center_x + (set_width / 2);
        e_y = set_center_y + (set_heigth /2);
        //左上
        vertecis[0] = s_x;
        vertecis[1] = s_y;
        vertecis[2] = 0.0f;
        //左下
        vertecis[3] = s_x;
        vertecis[4] = e_y;
        vertecis[5] = 0.0f;
        //右上
        vertecis[6] = e_x;
        vertecis[7] = s_y;
        vertecis[8] = 0.0f;
        //右下
        vertecis[9] = e_x;
        vertecis[10] = e_y;
        vertecis[11] = 0.0f;

    }
    //テクスチャUVを付け替える
    private void SetUpTexUV(float set_sx,float set_sy,float set_ex,float set_ey)
    {
        tex_uv[0] = set_sx;
        tex_uv[1] = set_sy;

        tex_uv[2] = set_sx;
        tex_uv[3] = set_ey;

        tex_uv[4] = set_ex;
        tex_uv[5] = set_sy;

        tex_uv[6] = set_ex;
        tex_uv[7] = set_ey;

    }

}
