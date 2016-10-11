package com.example.huzheyuan.scout;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.app.Activity;
import android.widget.TextView;


public class MainActivity extends Activity
{

    TextView cX ;
    TextView cY ;
    Button bClear;

    String strCX;
    String strCY;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cX = (TextView) findViewById(R.id.coordinateX);
        cY = (TextView) findViewById(R.id.coordinateY);
        bClear = (Button) findViewById(R.id.btnClear);

        FrameLayout frame = (FrameLayout) findViewById(R.id.mylayout);
        final GirlView girl = new GirlView(MainActivity.this);
        //为我们的萌妹子添加触摸事件监听器
        girl.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent event)
            {
                bClear.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        girl.bitmapX = 0;
                        girl.bitmapY = 0;

                        girl.invalidate();

                        strCX = Float.toString(girl.bitmapX);
                        strCY = Float.toString(girl.bitmapY);

                        cX.setText("x-axis: " + strCX);
                        cY.setText("y-axis: " + strCY);
                    }
                });

                //设置妹子显示的位置
                girl.bitmapX = event.getX() -75;
                girl.bitmapY = event.getY() -90;
                //调用重绘方法
                girl.invalidate();

                strCX = Float.toString(event.getX());
                strCY = Float.toString(event.getY());


                cX.setText("x-axis: " + strCX);
                cY.setText("y-axis: " + strCY);

                return true;
            }
        });


        frame.addView(girl);
    }
}