package com.ahaproject.playdeffence.Geometry;




import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;


import com.ahaproject.playdeffence.JavaUsuful.ResourceControll.ContextHave;
import com.ahaproject.playdeffence.JavaUsuful.Singleton.GLManager;
import com.ahaproject.playdeffence.JavaUsuful.Text.TextFileRead;
import com.ahaproject.playdeffence.R;
import com.ahaproject.playdeffence.Velocity.Vector3;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

/**
 * Created by akihiro on 2016/12/18.
 * ポリゴンを出す為のもの
 */

public class Polygon extends C_Geometry{

    private int shaderProgram;
    int[] tex_id = new int [1];

    //頂点
    float vertices[] = {
            -1.0f, 0.0f, 0.0f,//三角形の点A(x,y,z) 左上
            -1.0f, -1.0f, 0.0f,//三角形の点B(x,y,z)　左下
            0.0f, 0.0f, 0.0f,//右上
            0.0f, -1.0f, 0.0f,//三角形の点C(x,y,z)　右下
    };
    //テクスチャUV
    float tex_uv[] = {
            0.0f,0.0f,//左上
            0.0f,1.0f,//左下
            1.0f,0.0f,//右上
            1.0f,1.0f//右下
    };

    private int m_Postion_p;    //
    private int m_TexCoord_p;   //
    private int m_Texture_p;    //

    //コンストラクタ
    public Polygon() {
        //uniformはCPUから定数
        //vertexshader　　　attribute　は　頂点情報であるということの宣言
        //コメント文を除いたシェーダーソースを読み込み
        vertexShaderCode =null;
        TextFileRead textread = new TextFileRead();
        vertexShaderCode = textread.GetShaderSourceforTextFile("Shader/vertex_shader","vertex_plas_tex.txt");
        textread.ResetinString();
        fragmentShaderCode = textread.GetShaderSourceforTextFile("Shader/flagment_shader","flag,emt_plas_tex.txt");
        //コンパイルしているIDをもらっている。
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        //シェーダーのプログラムオブジェクト生成？
        shaderProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        GLES20.glLinkProgram(shaderProgram);

        //スクリーンサイズ
        Vector3 scren = GLManager.GetInstance().GetWindowSize();
        //Redo the Viewport,making it fullscreen
        GLES20.glViewport(0,0,(int)scren.x,(int)scren.y);
        //Clear our matrices
        Matrix.setIdentityM(mat,0);

        //texture reading
        AssetManager asset = ContextHave.getInstance().GetContext().getAssets();
        String[] tex_file_list = null;
        try{
            tex_file_list = asset.list("texture");
        }catch (IOException e)//入力（ファイル、キーボードやネットワーク接続など）
        {
            e.printStackTrace();//エラーメッセージ？
        }
        Bitmap bitmap = null;
        BufferedInputStream bis = null;
        try{
            bis = new BufferedInputStream(asset.open("texture/"+tex_file_list[0].toString()));
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

        /*GLES20.glGenTextures
        * GL sizei n 生成するテクスチャオブジェクト数
        *テクスチャオブジェクトの格納先のid先
        * offset
        * */
        GLES20.glGenTextures(1,tex_id,0);

        /*GLES20.glPixelStorei
        * commnad UNPACK…テクスチャをピクセルにアップ PACLK…テクスチャからテクセルをダウンロード
        * 何バイトごとの区切りか
        * */
        GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT,1);
        //Bind texture   +???
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        //ここで初めてテクスチャのメモリ確保
        /*
        メモリの利用方法　target
        * */
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,m_Texture_p);
        //VRAMへピクセル情報をコピー
        //GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,GLES20.GL_RGBA,bitmap,0);

        //SetFilterring
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
        //Load the bitmap into the bound texture


        //bye bitmap
        bitmap.recycle();


      /*  Bitmap bits = BitmapFactory.decodeResource(ContextHave.getInstance().GetContext().getResources(), R.drawable.tex_sample);
        GLES20.glGenTextures(1,tex_id,0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,m_Texture_p);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bits,0);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
        bits.recycle();
*/
    }

    @Override
    public int LoadShaderFile(String vertex, String fragment) {
        //未実装
        return 0;
    }

    @Override
    public int loadShader(int type, String shaderCode) {
        //シェーダーオブジェクトの生成
        int shader = GLES20.glCreateShader(type);
        //シェーダーオブジェクトとソースコードを結びつける
        GLES20.glShaderSource(shader, shaderCode);
        //コンパイル
        GLES20.glCompileShader(shader);
        return shader;
    }

    @Override
    public void draw() {
        //使うシェーダープログラム　適用

        GLES20.glUseProgram(shaderProgram);




        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        //ブレンド方法
        //GLES20.glEnable(GLES20.GL_BLEND);
      //  GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);	// 単純なアルファブレンド

        //最後の４はfloat型故
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());//ハードウェアと同じバイト順に並び替え
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        //バッファの現在位置に指定されたbyteを書き込み　頂点数分書き込み
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        //シェーダープログラムの　vPositionの位置？を取得する　その値へのアクセスするためのIDが返ってくる
        int positionAttrib = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionAttrib);//その情報へのアクセスを有効にする。
        //対応付け終了。
        //GLES20.glVertexAttribPointer(positionAttrib,vertices.length, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        //関連付けている値のところにデータを渡す。 ポジション（ｘ、ｙ、ｚ,w）で渡す
        GLES20.glVertexAttribPointer(positionAttrib,        //シェーダー変数の位置
                3,                      //頂点一つ当たりの要素数
                GLES20.GL_FLOAT,        //データのフォーマット
                false,                  //正規化するかどうかのフラグ
                12,       //頂点データの先頭から次の頂点データまでのバイト数 (1頂点当たりのバイト数)
                vertexBuffer);                              //頂点データのポインタ
        int mtrxhandle = GLES20.glGetUniformLocation(shaderProgram,"uMVPMatrix");
        GLES20.glEnableVertexAttribArray(mtrxhandle);//その情報へのアクセスを有効にする。
        //Apply the Projection and view transformation matrix
        GLES20.glUniformMatrix4fv(mtrxhandle,1,false,mat,0);

        //テクスチャの指定
        int m_texture = GLES20.glGetUniformLocation(shaderProgram,"texture");
        GLES20.glEnableVertexAttribArray(m_texture);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);//どのテクスチャのGPUを使うか？？？
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,m_Texture_p);//どのテクスチャを使うか？
        GLES20.glUniform1i(m_texture,0);


        int m_texcoord = GLES20.glGetAttribLocation(shaderProgram,"texcoord");
        GLES20.glEnableVertexAttribArray(m_texcoord);
        //値セット
        ByteBuffer TexCoordBuffer = ByteBuffer.allocateDirect(tex_uv.length * 4);
        TexCoordBuffer.order(ByteOrder.nativeOrder());//ハードウェアと同じバイト順に並び替え
        FloatBuffer uploadTexcoordBuffer = TexCoordBuffer.asFloatBuffer();
        uploadTexcoordBuffer.put(tex_uv);//現在の場所に書き込み
        uploadTexcoordBuffer.position(0);

        GLES20.glVertexAttribPointer(m_texcoord,2,GLES20.GL_FLOAT,false,8,uploadTexcoordBuffer);


        //描画の仕方指定　GL_TRIANGLES トライアングルファン　頂点数　
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertices.length/3);

        //アクセスを無効にする？
      //  GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_TEXTURE_2D);
    }

    public void drawatonce(GL10 gl)
    {
        gl.glColor4x(0x10000, 0x10000, 0x10000, 0x10000);
        gl.glActiveTexture(GL10.GL_TEXTURE0);
        //テクスチャIDに対応するテクスチャをバインドする
        gl.glBindTexture(GL10.GL_TEXTURE_2D, tex_id[0]);
        //テクスチャの座標と幅と高さを指定
        int rect[] = { 0,128,128,-128};
        //テクスチャ画像のどの部分を使うかを指定

        ((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D,GL11Ext.GL_TEXTURE_CROP_RECT_OES, rect, 0);

        ((GL11Ext) gl).glDrawTexfOES( 0,0,0,50,50);
    }


}
