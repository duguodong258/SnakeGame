package max.com.snakegame.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import max.com.snakegame.R;


/**
 * @author jelly
 * @TIME 2018/1/2
 * @DES 贪吃蛇
 */

public class SnakeView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint mHeadPaint;//画头的画笔
    private Paint mEyesPaint;//画眼睛的画笔

    float positionX = 100;//舌头的x坐标
    float positionY = 100;//舌头的y坐标


    int start = 1;//嘴巴的下嘴唇角度
    int end = 359;//嘴巴的上嘴唇角度
    int fudu = 5;

    boolean isToBig;//是否快要扯嘴

    private int mScreenWidth;
    private int mScreenHeight;
    private SurfaceHolder mHolder;

    public SnakeView(Context context) {
        super(context);
        init(context);
    }

    public SnakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        mHolder = getHolder();
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.addCallback(this);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        initSnake();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }


    private void initSnake() {
        mHeadPaint = new Paint();
        mHeadPaint.setStyle(Paint.Style.FILL);
        mHeadPaint.setColor(getResources().getColor(R.color.colorAccent));

        mEyesPaint = new Paint();
        mEyesPaint.setColor(getResources().getColor(R.color.GREEN));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mouthAnim();
                translate();
                postDelayed(this,10);
                invalidate();

            }
        },2500);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        float x = (getWidth()- getHeight()/2) / 2;
//        float y = getHeight() / 4;
        //画头
        drawHead(canvas);
        //画眼睛
        drawEyes(canvas);
    }


    private void drawEyes(Canvas canvas) {
        canvas.drawCircle(positionX + 30,positionY + 30,10,mEyesPaint);
    }

    private void drawHead(Canvas canvas) {
        RectF rectF = new RectF(positionX, positionY, positionX + 100, positionY + 100);
        canvas.drawArc(rectF,start,end,true, mHeadPaint);
    }


    //控制蛇头移动移动
    private void translate() {
        if(positionX + 100 < mScreenWidth){
            //如果是向左 需要再加个boolean值判断
            positionX +=5;
        }else{
            if(positionY + 180 > mScreenHeight){
                positionY = mScreenHeight - 180;
                fudu += 2;
                positionX -= fudu;
            }else{
                positionY += 15;
            }
            Log.i("GD>>>", "positionY: " + positionY + "  mScreenHeight: " + mScreenHeight);
        }

    }


    //控制嘴巴的张闭
    private void mouthAnim() {
        if(isToBig){
            start -=5;
            end +=10;
        }else{
            start +=5;
            end -=10;
        }
        if(start >= 45){
            isToBig = true;
        }
        if(start <= 0){
            isToBig = false;
        }
    }

}
