package max.com.snakegame.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

import max.com.snakegame.R;
import max.com.snakegame.model.SnowFlake;


/**
 * @author jelly
 * @TIME 2018/1/5
 * @DES 下雪view
 */

public class SnowSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private int           mFlakeCount = 20;
    private int           mSpeedX     = 5;
    private int           mSpeedY     = 10;
    private int           mMaxSize    = 70;
    private int           mMinSize    = 50;
    private int           mViewWidth  = 200;
    private int           mViewHeight = 100;
    private boolean       mStart;
    private boolean       mIsRightDir;
    private SurfaceHolder mHolder;
    private SnowFlake[]   mSnowFlakes;
    private Bitmap        mSnowBitmap = null;
    public int mode = 4;  //1.春 2.夏 3.秋 4.冬

    public SnowSurfaceView(Context context) {
        this(context,null);
    }

    public SnowSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SnowSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHolder();
        setZOrderOnTop(true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Snow, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for(int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.Snow_flakeCount :
                    mFlakeCount = typedArray.getInteger(attr, 0);
                    break;
                case R.styleable.Snow_maxSize :
                    mMaxSize = typedArray.getInteger(attr, 70);
                    break;
                case R.styleable.Snow_minSize :
                    mMinSize = typedArray.getInteger(attr, 50);
                    break;
                case R.styleable.Snow_flakeSrc :
                    int srcId = typedArray.getResourceId(attr, R.drawable.snow_flake);
                    mSnowBitmap = BitmapFactory.decodeResource(getResources(), srcId);
                    break;
                case R.styleable.Snow_speedX :
                    mSpeedX = typedArray.getInteger(attr, 10);
                    break;
                case R.styleable.Snow_speedY :
                    mSpeedY = typedArray.getInteger(attr, 10);
                    break;
            }
        }
        if(mMinSize > mMaxSize){
            mMinSize = mMaxSize;
        }
        typedArray.recycle();
    }

    private void initHolder() {
        mHolder = getHolder();
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i("GD>>>", "--- surfaceCreated ---");
        initSnowFlakes();
        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.i("GD>>>", "--- surfaceChanged ---");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //--- measure the view's width
        int widthMode  = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            mViewWidth = (getPaddingStart() + mSnowBitmap.getWidth() + getPaddingEnd());
        }

        //--- measure the view's height
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            mViewHeight = (getPaddingTop() + mSnowBitmap.getHeight() + getPaddingBottom());
        }

        setMeasuredDimension(mViewWidth, mViewHeight);
    }
    @Override
    protected void onVisibilityChanged(@NonNull View changedView,  int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        mStart = (visibility == VISIBLE);
    }

    public void initSnowFlakes() {
        Log.i("GD>>>", "--- initSnowFlakes ---");
        mSnowFlakes = new SnowFlake[mFlakeCount];
        mIsRightDir = new Random().nextBoolean();
        for(int i = 0; i < mSnowFlakes.length; i++) {
            mSnowFlakes[i] = new SnowFlake();
            mSnowFlakes[i].setWidth(new Random().nextInt(mMaxSize - mMinSize) + mMinSize);
            mSnowFlakes[i].setHeight(mSnowFlakes[i].getWidth());
            mSnowFlakes[i].setX(new Random().nextInt(mViewWidth));
            mSnowFlakes[i].setY(new Random().nextInt(mViewHeight));
            mSnowFlakes[i].setSpeedY(new Random().nextInt(4) + mSpeedY);
            if(mIsRightDir){
                mSnowFlakes[i].setSpeedX(new Random().nextInt(4) + mSpeedX);
            }else{
                mSnowFlakes[i].setSpeedX(-(new Random().nextInt(4) + mSpeedX));
            }
        }
    }


    private void start() {
        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        if(mStart){
                            upDatePara();
                            drawView();
                        }
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    private void upDatePara() {
        int x;
        int y;
        for (SnowFlake snowFlake : mSnowFlakes) {
            if(snowFlake == null){
               break;
            }
            x = snowFlake.getX() + snowFlake.getSpeedX();
            y = snowFlake.getY() + snowFlake.getSpeedY();
            if((x >mViewWidth + 20 || x < 0) || y > (mViewHeight + 20)){
                x = new Random().nextInt(mViewWidth);
                y = 0;
            }
            snowFlake.setX(x);
            snowFlake.setY(y);
        }
    }

    private void drawView() {
        if(mHolder == null){
            return;
        }
        Canvas canvas = mHolder.lockCanvas();
        if(canvas == null){
            return;
        }
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        switch (mode) {
            case 1 ://春天
                drawRain(canvas);
                break;
            case 2 ://夏天
                drawRain(canvas);
                break;
            case 3 ://秋天
                drawSnow(canvas);
                break;
            case 4 ://冬天
                drawSnow(canvas);
                break;
        }
        mHolder.unlockCanvasAndPost(canvas);
    }

    /**
     * 下雪
     * @param canvas
     */
    private void drawSnow(Canvas canvas) {
        Paint paint = new Paint();
        Rect rect = new Rect();
        for (SnowFlake flake : mSnowFlakes) {
            rect.left = flake.getX();
            rect.top = flake.getY();
            rect.right = rect.left + flake.getWidth();
            rect.bottom = rect.top + flake.getHeight();
            canvas.drawBitmap(mSnowBitmap,null,rect,paint);
        }
    }


    /**
     * 下雨
     * @param canvas
     */
    private void drawRain(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.transparent_75));
        paint.setStrokeWidth(2);
        Rect rect = new Rect();
        for (SnowFlake flake : mSnowFlakes) {
            if(flake == null){
                break;
            }
            rect.left = flake.getX();
            rect.top = flake.getY();
            if(mIsRightDir){
                rect.right = rect.left + 3;
            }else{
                rect.right = rect.left - 3;
            }
            rect.bottom = rect.top + flake.getHeight();
            canvas.drawLine(rect.left,rect.top,rect.right,rect.bottom,paint);
        }
    }

    public void reInitSnowFlakes(int mode) {
        this.mode = mode;
        switch (mode) {
            case 1 ://春天
                mFlakeCount = 80;
                mSpeedY     = 50;
                break;
            case 2 ://夏天
                mFlakeCount = 130;
                mSpeedY     = 70;
                break;
            case 3 ://秋天
                mFlakeCount = 50;
                mSpeedY     = 10;
                break;
            case 4 ://冬天
                mFlakeCount = 50;
                mSpeedY     = 10;
                break;
        }
        initSnowFlakes();
    }
}
