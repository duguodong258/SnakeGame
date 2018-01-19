package max.com.snakegame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import max.com.snakegame.R;
import max.com.snakegame.base.BaseActivity;
import max.com.snakegame.util.AnimUtil;
import max.com.snakegame.widget.SnowSurfaceView;


/**
 * @author jelly
 * @TIME 2018/1/3
 * @DES 欢迎界面
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {

    private Button btnSelectScene;
    private Button btnSpring;
    private Button btnSummer;
    private Button btnAutumn;
    private Button btnWinter;

    private int mWidthPixels;
    private int mHeightPixels;
    private SnowSurfaceView mSnowSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        //        Glide.with(this)
//                .load(R.drawable.icon_1)
//                .apply(RequestOptions.bitmapTransform(new CropCircleTransformation()))
//                .into(iv1);
        mSnowSurfaceView = (SnowSurfaceView) findViewById(R.id.snowView);
        mWidthPixels = getResources().getDisplayMetrics().widthPixels;
        mHeightPixels = getResources().getDisplayMetrics().heightPixels;

        btnSelectScene = (Button) findViewById(R.id.btn_select_scene);
        btnSpring = (Button) findViewById(R.id.btn_spring);
        btnSummer = (Button) findViewById(R.id.btn_summer);
        btnAutumn = (Button) findViewById(R.id.btn_autumn);
        btnWinter = (Button) findViewById(R.id.btn_winter);

        btnSpring.setOnLongClickListener(this);
        btnSummer.setOnLongClickListener(this);
        btnAutumn.setOnLongClickListener(this);
        btnWinter.setOnLongClickListener(this);

        btnSpring.setOnTouchListener(this);
        btnSummer.setOnTouchListener(this);
        btnAutumn.setOnTouchListener(this);
        btnWinter.setOnTouchListener(this);


//        AnimUtil.rotationAnim(btnSelectScene);
        AnimUtil.floatAnim(this,btnSummer,false);
        AnimUtil.floatAnim(this,btnAutumn,false);
        AnimUtil.floatAnim(mContext,btnSpring,true);
        AnimUtil.floatAnim(mContext,btnWinter,true);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (view.getId()) {
            case R.id.btn_spring :
                intent.putExtra("mode",MODE_SPRING);
                break;
            case R.id.btn_summer :
                intent.putExtra("mode",MODE_SUMMER);
                break;
            case R.id.btn_autumn :
                intent.putExtra("mode",MODE_AUTUMN);
                break;
            case R.id.btn_winter :
                intent.putExtra("mode",MODE_WINTER);
                break;
        }
        startActivity(intent);
        finish();
    }

    int downX;
    int downY;
    int lastX;//记录是否是单点
    int lastY;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE :
                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();
                int dX = moveX - downX;
                int dY = moveY - downY;

                int left = v.getLeft() + dX;
                int right = v.getRight() + dX;
                int top = v.getTop() + dY;
                int bottom = v.getBottom() + dY;

                if(left < 0){
                    left = 0;
                    right = left + v.getWidth();
                }
                if(right > mWidthPixels){
                    right = mWidthPixels;
                    left = mWidthPixels - v.getWidth();
                }

                if(top < 0){
                    top = 0;
                    bottom = top + v.getHeight();
                }

                if(bottom > mHeightPixels){
                    bottom = mHeightPixels;
                    top = mHeightPixels - v.getHeight();
                }

                v.layout(left,top,right,bottom);

                downX = moveX;
                downY = moveY;
                break;
            case MotionEvent.ACTION_UP :
                int disx = (int) (event.getRawX() - lastX);
                int disy = (int) (event.getRawY() - lastY);
                if(Math.abs(disx) < 1 || Math.abs(disy) < 1){//单点
                    Intent intent = new Intent(this, MainActivity.class);
                    switch (v.getId()) {
                        case R.id.btn_spring :
                            intent.putExtra("mode",MODE_SPRING);
                            mSnowSurfaceView.reInitSnowFlakes(1);
                            break;
                        case R.id.btn_summer :
                            intent.putExtra("mode",MODE_SUMMER);
                            mSnowSurfaceView.reInitSnowFlakes(2);
                            break;
                        case R.id.btn_autumn :
                            intent.putExtra("mode",MODE_AUTUMN);
                            mSnowSurfaceView.reInitSnowFlakes(3);
                            break;
                        case R.id.btn_winter :
                            intent.putExtra("mode",MODE_WINTER);
                            mSnowSurfaceView.reInitSnowFlakes(4);
                            break;
                    }

                    startActivity(intent);
                    finish();
                }
                break;

        }
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.btn_spring :

                break;
            case R.id.btn_summer :

                break;
            case R.id.btn_autumn :

                break;
            case R.id.btn_winter :

                break;
        }
        return false;
    }
}
