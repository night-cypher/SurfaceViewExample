package com.questdot.surfaceviewexample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    RectangleView myview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myview = new RectangleView(this);
        setContentView(myview);
    }


    @Override
    protected void onPause() {
        super.onPause();
        myview.stopTimer();
    }



    public class RectangleView extends SurfaceView implements SurfaceHolder.Callback {
        private float x = 0, y = 0;
        private Paint paint = null;

        public RectangleView(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.RED);
            getHolder().addCallback(this);
        }

        public void draw() {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.save();
            canvas.translate(x, y);
            canvas.drawRect(0, 0, 100, 100, paint);
            canvas.restore();
            getHolder().unlockCanvasAndPost(canvas);
            y++;

        }

        private Timer timer = null;
        private TimerTask task = null;

        public void startTimer() {
            timer = new Timer();
            task = new TimerTask() {

                @Override
                public void run() {
                    draw();
                }
            };
            timer.schedule(task, 100, 100);
        }

        public void stopTimer() {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {


        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            startTimer();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            stopTimer();
        }


    }

}
