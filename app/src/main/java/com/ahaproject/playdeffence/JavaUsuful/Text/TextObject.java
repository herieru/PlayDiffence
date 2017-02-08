package com.ahaproject.playdeffence.JavaUsuful.Text;

/**
 * Created by akihiro on 2017/01/30.
 * 人の流用http://androidblog.reindustries.com/a-real-opengl-es-2-0-2d-tutorial-part-8-rendering-text/
 */

public class TextObject {
    public String text;             //文字列
    public float x;                 //X座標
    public float y;                 //Y座標
    public float[] color;           //色

    public TextObject()
    {
        text = "default";
        x = 0f;
        y = 0f;
        color = new float[] {1f, 1f, 1f, 1.0f};
    }

    public TextObject(String txt, float xcoord, float ycoord)
    {
        text = txt;
        x = xcoord;
        y = ycoord;
        color = new float[] {1f, 1f, 1f, 1.0f};
    }

}
