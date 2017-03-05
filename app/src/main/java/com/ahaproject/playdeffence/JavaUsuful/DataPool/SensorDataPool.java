package com.ahaproject.playdeffence.JavaUsuful.DataPool;

import com.ahaproject.playdeffence.Velocity.Vector3;

/**
 * Created by akihiro on 2017/02/28.
センサー情報を、ID＝センサーの振り分けをint型

 */

public class SensorDataPool {
    SensorDataPool my = new SensorDataPool();

    private final int POOL_DATA_MAX = 5;//扱えるデータ種類のMAX
    private final int SAVE_DATA_MAX = 5;//1種のデータを蓄えるMAX
    private final int EMPTY_BOX = -2;

    private int[] data_type = new int [SAVE_DATA_MAX];//5こまでセンサーなどのデータをプールできる
    private Vector3[][] datas = new Vector3[SAVE_DATA_MAX][POOL_DATA_MAX];
    //========================================
    //記憶媒体を初期化
    //========================================
    private SensorDataPool()
    {
        //初期化
        for(int i = 0;i<SAVE_DATA_MAX;i++)
        {
            for(int y = 0;y<POOL_DATA_MAX;y++)
            {
                datas[i][y].Zero();//初期化
            }
            data_type[i] = EMPTY_BOX;
        }
    }
    //=================================================
    //箱の初期化　しないとアクセスできない　　使用する際は、これを一回かならずおこなう
    //=================================================
    public boolean InitalisedBox(int set_box_no)
    {

        int cnt = 0;
        //アクセスできる場所を探す
        for(int i = 0;i<SAVE_DATA_MAX;i++)
        {
            if(data_type[i] == EMPTY_BOX)
            {
                data_type[i] = set_box_no;
                break;
            }
            //すでに登録したものがあった場合
            if(data_type[i] == set_box_no)
            {
                return false;//すでにあるので、いらない。
            }
            cnt++;
        }
        //初期化失敗
        if(cnt >=SAVE_DATA_MAX)
            return false;
        return true;//初期化成功
    }

    //値を検索して、セットする。
    public void SetValue3(int access_no,Vector3 value)
    {
        int cnt = access_map(access_no);
        UpdateDatas(cnt,value);
    }
    //値を取得する
    public Vector3 GetValue(int access_no)
    {
        Vector3 rtn_data = new Vector3();
        rtn_data.Zero();
        int cnt = access_map(access_no);
        rtn_data = datas[cnt][0];
        return rtn_data;
    }
    //アクセスする場所の値を返す
    private int access_map(int value)
    {
        int access_no = 0;
        for(int i = 0;i<SAVE_DATA_MAX;i++)
        {
            if(access_no == data_type[i])
            {
                access_no =i;
                break;
            }
        }
        return access_no;
    }
    //データを更新する。　（センサーの種類、値）
    private void UpdateDatas(int access_no,Vector3 value)
    {
        //先頭から順番に更新していく ただし、最後の項目は自分で更新
        for(int i = 0;i<POOL_DATA_MAX-1;i++)
        {
            datas[access_no][i] = datas[access_no][i+1];
        }
        datas[access_no][5] = value;
    }









}
