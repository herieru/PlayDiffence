package com.ahaproject.playdeffence.Velocity;

/**
 * Created by akihiro on 2016/12/10.
 */

public class Vector3 extends Position3 {

    public Vector3()
    {
        x = 0;
        y = 0;
        z = 0;
    }

    //零を入れる。
    public void Zero()
    {
        x = 0;
        y = 0;
        z = 0;
    }

    //値をセットする。
    public void SetVector3(float s_x,float s_y,float s_z)
    {
        x = s_x;
        y = s_y;
        z = s_z;
    }

    //ベクトル自身が持っているものを長さ１のものにする
    public void InsideNormalize()
    {
        float length = InsideLength();
        if(x != 0)
            x = x / length;
        if(y != 0)
            y = y / length;
        if(z != 0)
            z = z / length;
    }

    //ベクトル自身が長さを求める
    private float InsideLength()
    {
        //doubleで合わして計算し、その結果をfloatに戻して、格納
        float ans  =0;
        ans= (float)(Math.sqrt((double)(x * x + y * y + z * z)));

        return ans;
    }

    //正規化
    public Vector3 Normalize(Vector3 vec)
    {
        Vector3 rtn_vec = new Vector3();
        rtn_vec = vec;
        rtn_vec.InsideNormalize();//正規化
        return rtn_vec;
    }

    public float Dot(Vector3 vec1,Vector3 vec2)
    {
        float rad = 0;//角度が入る
        Vector3 tmp1 = vec1,tmp2 = vec2;
        tmp1.InsideNormalize();
        tmp2.InsideNormalize();
        rad = (tmp1.x *tmp2.x) + (tmp1.y *tmp2.y) + (tmp1.z *tmp2.z);
        return rad;
    }



}
