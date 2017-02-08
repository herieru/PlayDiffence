package com.ahaproject.playdeffence.JavaUsuful.Texture;

import android.opengl.GLUtils;

/**
 * Created by akihiro on 2017/01/17.
 */

public class TextureObj {

    final int TEXTURE_ERROR = -10;  //どこかにエラークラス作ったほうがいいかも？
    private int texture_id;//生成の際にOpenGLから割り振られたテクスチャのID


    TextureObj()
    {
       texture_id = -TEXTURE_ERROR;//エラー
    }

    /*一度だけ読み込みをする。*/
    public void FirstTimeOnly()
    {

    }


}
