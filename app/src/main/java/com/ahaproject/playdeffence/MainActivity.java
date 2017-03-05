package com.ahaproject.playdeffence;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.ahaproject.playdeffence.JavaUsuful.ResourceControll.ContextHave;
import com.ahaproject.playdeffence.JavaUsuful.Singleton.GLManager;
import com.ahaproject.playdeffence.TouchController.TouchManager;

import java.util.List;

class MainActivity extends Activity implements SensorEventListener{

    private MyGLSurfaceView mGLView;
    private TouchManager touch;
    private SensorManager m_Sensor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //画面をフルスクリーンにする。
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mGLView = new MyGLSurfaceView(this);//Contextを渡す
        touch = TouchManager.getInstance();
        setContentView(mGLView);//描画で使うものの設定
        int width,height;
        //ディスプレイの大きさをもっておく
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        width = point.x;
        height = point.y;
        GLManager.GetInstance().SetWindowSize(width,height);
        //コンテキスト保持
        ContextHave.getInstance().SetContext(this);//コンテキストをグローバルに保持
        //センサーを有効にするためのもの 引数は、呼び出すマネージャの種類
        m_Sensor = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

    }

    //OnPauseの状態から戻ってきたときなど。
    @Override
    protected void onResume() {
        super.onResume();
        //センサーの使用に当たり登録
        List<Sensor> sensors = m_Sensor.getSensorList(Sensor.TYPE_GYROSCOPE);
        if(sensors.size() > 0){
            Sensor s = sensors.get(0);//??
            //取得速度をゲームレベル50ms程度で取得
            m_Sensor.registerListener(this,s,SensorManager.SENSOR_DELAY_GAME);
        }
        //エラーチェックしない
        Sensor velocity = m_Sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        m_Sensor.registerListener(this,velocity,SensorManager.SENSOR_DELAY_GAME);

    }


    //同じアプリケーションから別のアプリケーションが呼ばれた場合
    @Override
    protected  void onPause(){
        super.onPause();
        if(m_Sensor != null)
            m_Sensor.unregisterListener(this);
    }


    //アクティビティが表示されていない状態の時
    @Override
    protected  void onStop(){
        super.onStop();
    }

    //Stop状態から戻ってきたとき
    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    //アクティビティが死んだ時　　セーブなどの時に実装
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }



    //イベントドリスナ
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        touch.SetTouchEvent(event);

        return true;//常に消化したとする
    }


    //ここに値が送られてくる
    @Override
    public void onSensorChanged(SensorEvent event) {
        //  Auto-generated method stub
        switch( event.sensor.getType() ) {
            case Sensor.TYPE_ACCELEROMETER: // 加速度　かたむきを取得
                System.out.println("Veloc   X?:"+event.values[0]+"Y?:"+event.values[1]+"Z?:"+event.values[2]);
                break;
            case Sensor.TYPE_GYROSCOPE: // ジャイロ
               // System.out.println("GYAIRO    X?:"+event.values[0]+"Y?:"+event.values[1]+"Z?:"+event.values[2]);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD: // 磁力

                break;
        }

    }

    //センサーの精度
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
